package com.nwilson.finance.moneymgmt.service

import com.nwilson.finance.moneymgmt.dao.EstablishmentVisitRepository
import com.nwilson.finance.moneymgmt.entity.EstablishmentVisit
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class EstablishmentVisitService {

    @Autowired
    EstablishmentVisitRepository establishmentVisitRepository

    List<EstablishmentVisit> findAll() {
        establishmentVisitRepository.findAll()
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
