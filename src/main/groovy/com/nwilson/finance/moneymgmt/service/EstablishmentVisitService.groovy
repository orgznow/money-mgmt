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
        establishmentVisitRepository.save(theStoreVisit)
    }
}
