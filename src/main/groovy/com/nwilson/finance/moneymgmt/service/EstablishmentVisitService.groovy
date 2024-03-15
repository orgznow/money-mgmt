package com.nwilson.finance.moneymgmt.service

import com.nwilson.finance.moneymgmt.dao.EstablishmentVisitRepository
import com.nwilson.finance.moneymgmt.entity.EstablishmentVisit
import com.nwilson.finance.moneymgmt.entity.JournalEntry
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

    Map toResults(String displayMonthYear) {
        List<EstablishmentVisit> storeVisits = findAll(displayMonthYear)
        List<EstablishmentVisit> sortedStoreVisits = storeVisits.sort { a, b ->
            -a.visitDate.time <=> -b.visitDate.time ?: -a.id <=> -b.id
        }
        Map<Integer, List<EstablishmentVisit>> storeVisitsByWeekOfMonth = sortedStoreVisits.groupBy {
            it.visitDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().get(ChronoField.ALIGNED_WEEK_OF_MONTH)
        }.sort()
        Map<String, Map<String, BigDecimal>> weeklySpendByCategories = toWeeklySpendByCategories(storeVisitsByWeekOfMonth)
        Map<String, Map<String, BigDecimal>> weeklySpendByTxTypes = toWeeklySpendByTxTypes(storeVisitsByWeekOfMonth)
        List<Map<String, String>> weeklySpendTotals = weeklySpendByCategories.collect { k, v -> ["${k}": "\$${v['Total'].toString()}"] }
        List<Map<String, String>> weeklySpendTotalsAlt = weeklySpendByTxTypes.collect { k, v -> ["${k}": "\$${v['Total'].toString()}"] }
        if (weeklySpendTotals != weeklySpendTotalsAlt) {
            log.error("weeklySpendTotals ${weeklySpendTotals} does not match weeklySpendTotalsAlt ${weeklySpendTotalsAlt}")
        }
        BigDecimal totalMonthlySpendToDate = storeVisits.visitTotalAmount.sum()
        BigDecimal totalMonthlySpendToDateAlt = storeVisits.journalEntries.finalAmount.flatten().sum()
        if (totalMonthlySpendToDate != totalMonthlySpendToDateAlt) {
            log.error("totalMonthlySpendToDate ${totalMonthlySpendToDate} does not match totalMonthlySpendToDateAlt ${totalMonthlySpendToDateAlt}")
        }
        Map storeVisitsInfo = [
            totalMonthlySpendToDate: totalMonthlySpendToDate, weeklySpendTotals: weeklySpendTotals, weeklySpendByCategories: weeklySpendByCategories,
            weeklySpendByTxTypes: weeklySpendByTxTypes, allStoreVisits: sortedStoreVisits
        ]
        log.debug("Returning storeVisitsInfo as ${storeVisitsInfo}")
        storeVisitsInfo
    }

    private static Map<String, Map<String, Double>> toWeeklySpendByCategories(Map<Integer, List<EstablishmentVisit>> storeVisitsByWeekOfMonth) {
        Map<String, Map<String, BigDecimal>> weeklySpendByCategories = [:]
        storeVisitsByWeekOfMonth.each {  weekNbr, weeklyStoreVisits ->
            List<JournalEntry> allWeeklyJournalEntries = weeklyStoreVisits*.journalEntries.flatten()
            log.trace("In week ${weekNbr} - all journal entry finalAmounts are: ${allWeeklyJournalEntries.finalAmount} with a total of ${allWeeklyJournalEntries.finalAmount.sum()}")
            Map<String, List<JournalEntry>> weeklyJournalEntriesByCategory = allWeeklyJournalEntries.groupBy {  je -> je.spendCategory.description }.sort()
            Map<String, BigDecimal> spendByCategory = [:]
            weeklyJournalEntriesByCategory.each { category, journalEntries ->
                BigDecimal totalWeeklySpendByCategory = journalEntries*.finalAmount.sum()
                log.trace("Total week ${weekNbr} spend by category ${category} is ${totalWeeklySpendByCategory}")
                spendByCategory << [(category): totalWeeklySpendByCategory]
            }
            spendByCategory << [Total: allWeeklyJournalEntries.finalAmount.sum()]
            spendByCategory = spendByCategory.sort()
            weeklySpendByCategories << ["Week${weekNbr}": spendByCategory]
        }
        weeklySpendByCategories = weeklySpendByCategories.sort()
        log.debug("weeklySpendByCategories=${weeklySpendByCategories}")
        weeklySpendByCategories
    }

    private static Map<String, Map<String, Double>> toWeeklySpendByTxTypes(SortedMap<Integer, List<EstablishmentVisit>> storeVisitsByWeekOfMonth) {
        Map<String, Map<String, BigDecimal>> weeklySpendByTxTypes = [:]
        storeVisitsByWeekOfMonth.each {  weekNbr, weeklyStoreVisits ->
            log.trace("In week ${weekNbr} - all visitTotalAmount are: ${weeklyStoreVisits.visitTotalAmount} with a total of ${weeklyStoreVisits.visitTotalAmount.sum()}")
            Map<String, List<EstablishmentVisit>> weeklyVisitsByTxType = weeklyStoreVisits.groupBy { visit ->
                visit.transactionType.name
            }
            Map<String, BigDecimal> spendByTxTypes = [:]
            weeklyVisitsByTxType.each { txType, visits ->
                BigDecimal totalWeeklySpendByTxType = visits*.visitTotalAmount.flatten().sum()
                log.trace("Total week ${weekNbr} spend by txType ${txType} is ${totalWeeklySpendByTxType}")
                spendByTxTypes << [(txType): totalWeeklySpendByTxType]
            }
            spendByTxTypes << [Total: weeklyStoreVisits.visitTotalAmount.sum()]
            spendByTxTypes = spendByTxTypes.sort()
            weeklySpendByTxTypes << ["Week${weekNbr}": spendByTxTypes]
        }
        weeklySpendByTxTypes = weeklySpendByTxTypes.sort()
        log.debug("weeklySpendByTxTypes=${weeklySpendByTxTypes}")
        weeklySpendByTxTypes
    }

    private List<EstablishmentVisit> findAll(String displayMonthYear) {
        log.trace("Entered findAll with displayMonthYear ${displayMonthYear}")
        Date monthYearLB = (displayMonthYear) ? new SimpleDateFormat('yyyy-MM').parse(displayMonthYear): null
        Calendar cal = Calendar.getInstance()
        cal.with {
            setTime(monthYearLB.clone())
            add(Calendar.MONTH, 1)
            add(Calendar.SECOND, -1)
        }
        Date forMonthYearUB = cal.getTime()
        log.trace("Determined forMonthYear lowerBound as ${monthYearLB} and upperBound as ${forMonthYearUB}")
        establishmentVisitRepository.findAllByVisitDateBetween(monthYearLB, forMonthYearUB)
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
