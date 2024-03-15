package com.nwilson.finance.moneymgmt.service

import com.nwilson.finance.moneymgmt.dao.EstablishmentVisitRepository
import com.nwilson.finance.moneymgmt.entity.EstablishmentVisit
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import java.text.SimpleDateFormat

@Service
@Slf4j
class EstablishmentVisitService {

    @Autowired
    EstablishmentVisitRepository establishmentVisitRepository

    List<EstablishmentVisit> findAll(String displayMonthYear) {
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
