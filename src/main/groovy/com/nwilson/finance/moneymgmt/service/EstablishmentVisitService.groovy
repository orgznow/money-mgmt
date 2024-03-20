package com.nwilson.finance.moneymgmt.service

import com.nwilson.finance.moneymgmt.FinanceConverter
import com.nwilson.finance.moneymgmt.dao.EstablishmentVisitRepository
import com.nwilson.finance.moneymgmt.entity.EstablishmentVisit
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import java.text.SimpleDateFormat
import java.time.ZoneId
import java.time.temporal.ChronoField

@Service
@Slf4j
class EstablishmentVisitService {

    @Autowired
    EstablishmentVisitRepository establishmentVisitRepository

    Map getMonthlySpendInfo(String displayMonthYear) {
        toMonthlySpendInfo(findAll(displayMonthYear))
    }

    List<Map> findAll(String displayMonthYear) {
        log.trace("Entered findAll with displayMonthYear ${displayMonthYear}")
        Date monthYearLB = (displayMonthYear) ? new SimpleDateFormat('yyyy-MM').parse(displayMonthYear): null
        Calendar cal = Calendar.getInstance()
        cal.with {
            setTime(monthYearLB.clone() as Date)
            add(MONTH, 1)
            add(SECOND, -1)
        }
        Date forMonthYearUB = cal.getTime()
        log.trace("Determined forMonthYear lowerBound as ${monthYearLB} and upperBound as ${forMonthYearUB}")
        establishmentVisitRepository.findAllByVisitDateBetween(monthYearLB, forMonthYearUB).collect {
            FinanceConverter.toEstablishmentVisitMap(it)
        }.sort { a, b ->
            -a.visitDate.time <=> -b.visitDate.time ?: -a.id <=> -b.id
        }
    }

    private static Map toMonthlySpendInfo(List<Map> storeVisits) {
        Map<Integer, List<Map>> storeVisitsByWeekOfMonth = storeVisits.groupBy {
            it.visitDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().get(ChronoField.ALIGNED_WEEK_OF_MONTH)
        }.sort()
        Map<String, Map<String, BigDecimal>> weeklySpendByCategories = toWeeklySpendByCategories(storeVisitsByWeekOfMonth)
        Map<String, Map<String, BigDecimal>> weeklySpendByTxTypes = toWeeklySpendByTxTypes(storeVisitsByWeekOfMonth)
        List<Map<String, String>> weeklySpendTotals = weeklySpendByCategories.collect { k, v ->
            [(k): "\$${v['Total'].toString()}" as String]
        }
        List<Map<String, String>> weeklySpendTotalsAlt = weeklySpendByTxTypes.collect { k, v ->
            [(k): "\$${v['Total'].toString()}" as String]
        }
        if (weeklySpendTotals != weeklySpendTotalsAlt) {
            log.error("weeklySpendTotals ${weeklySpendTotals} does not match weeklySpendTotalsAlt ${weeklySpendTotalsAlt}")
        }
        BigDecimal totalMonthlySpendToDate = storeVisits.visitTotalAmount.sum() as BigDecimal
        BigDecimal totalMonthlySpendToDateAlt = storeVisits.journalEntries.finalAmount.flatten().sum()
        if (totalMonthlySpendToDate != totalMonthlySpendToDateAlt) {
            log.error("totalMonthlySpendToDate ${totalMonthlySpendToDate} does not match totalMonthlySpendToDateAlt ${totalMonthlySpendToDateAlt}")
        }
        Map storeVisitsInfo = [
            totalMonthlySpendToDate: totalMonthlySpendToDate, weeklySpendTotals: weeklySpendTotals, weeklySpendByCategories: weeklySpendByCategories,
            weeklySpendByTxTypes: weeklySpendByTxTypes, allStoreVisits: storeVisits
        ]
        log.debug("Returning storeVisitsInfo as ${storeVisitsInfo}")
        storeVisitsInfo
    }

    private static Map<String, Map<String, BigDecimal>> toWeeklySpendByCategories(Map<Integer, List<Map>> storeVisitsByWeekOfMonth) {
        Map<String, Map<String, BigDecimal>> weeklySpendByCategories = [:]
        storeVisitsByWeekOfMonth.each {  weekNbr, weeklyStoreVisits ->
            List<Map> allWeeklyJournalEntries = weeklyStoreVisits*.journalEntries.flatten() as List<Map>
            log.trace("In week ${weekNbr} - all journal entry finalAmounts are: ${allWeeklyJournalEntries.finalAmount} with a total of ${allWeeklyJournalEntries.finalAmount.sum()}")
            Map<String, List<Map>> weeklyJournalEntriesByCategory = allWeeklyJournalEntries.groupBy {  je -> je.spendCategory.description }
            Map<String, BigDecimal> spendByCategory = [:]
            weeklyJournalEntriesByCategory.each { category, journalEntries ->
                BigDecimal totalWeeklySpendByCategory = journalEntries*.finalAmount.sum() as BigDecimal
                log.trace("Total week ${weekNbr} spend by category ${category} is ${totalWeeklySpendByCategory}")
                spendByCategory << [(category): totalWeeklySpendByCategory]
            }
            Map<String, BigDecimal> sortedSpendByCategory = [:] + spendByCategory.sort()
            sortedSpendByCategory << [Total: allWeeklyJournalEntries.finalAmount.sum() as BigDecimal]
            //TODO: See why this doesn't work
            //weeklySpendByCategories << ["Week${weekNbr}" as String: sortedSpendByCategory]
            weeklySpendByCategories.put("Week${weekNbr}" as String, sortedSpendByCategory)
        }
        log.debug("weeklySpendByCategories=${weeklySpendByCategories.sort()}")
        weeklySpendByCategories.sort()
    }

    private static Map<String, Map<String, BigDecimal>> toWeeklySpendByTxTypes(Map<Integer, List<Map>> storeVisitsByWeekOfMonth) {
        Map<String, Map<String, BigDecimal>> weeklySpendByTxTypes = [:]
        storeVisitsByWeekOfMonth.each {  weekNbr, weeklyStoreVisits ->
            log.trace("In week ${weekNbr} - all visitTotalAmount are: ${weeklyStoreVisits.visitTotalAmount} with a total of ${weeklyStoreVisits.visitTotalAmount.sum()}")
            Map<String, List<Map>> weeklyVisitsByTxType = weeklyStoreVisits.groupBy { visit ->
                visit.transactionType.name
            }
            Map<String, BigDecimal> spendByTxType = [:]
            weeklyVisitsByTxType.each { txType, visits ->
                BigDecimal totalWeeklySpendByTxType = visits*.visitTotalAmount.flatten().sum() as BigDecimal
                log.trace("Total week ${weekNbr} spend by txType ${txType} is ${totalWeeklySpendByTxType}")
                spendByTxType << [(txType): totalWeeklySpendByTxType]
            }
            Map<String, BigDecimal> sortedSpendByTxType = [:] + spendByTxType.sort()
            sortedSpendByTxType << [Total: weeklyStoreVisits.visitTotalAmount.sum()]
            //TODO: See why this doesn't work
            //weeklySpendByTxTypes << ["Week${weekNbr}" as String: sortedSpendByTxType]
            weeklySpendByTxTypes.put("Week${weekNbr}" as String, sortedSpendByTxType)
        }
        weeklySpendByTxTypes = weeklySpendByTxTypes.sort()
        log.debug("weeklySpendByTxTypes=${weeklySpendByTxTypes}")
        weeklySpendByTxTypes
    }

    EstablishmentVisit save(EstablishmentVisit theStoreVisit) {
        theStoreVisit.taxPercentage = theStoreVisit.taxPercentage ?: 0.0d
        theStoreVisit.journalEntries.each {
            it.entryDate = theStoreVisit.visitDate
            it.establishmentVisit = theStoreVisit
            it.taxAmount = (it.isTaxable) ? (it.taxAmount ?: 0.0d) : 0.0d
            it.tipAmount = it.tipAmount ?: 0.0d
        }
        establishmentVisitRepository.save(theStoreVisit)
    }
}
