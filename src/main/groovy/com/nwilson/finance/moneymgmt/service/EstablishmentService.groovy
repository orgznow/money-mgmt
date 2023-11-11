package com.nwilson.finance.moneymgmt.service

import com.nwilson.finance.moneymgmt.dao.EstablishmentRepository
import com.nwilson.finance.moneymgmt.entity.Establishment
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class EstablishmentService {

    @Autowired
    EstablishmentRepository establishmentRepository

    List<Establishment> findAll() {
        establishmentRepository.findAll()
    }

    Establishment save(Establishment entry) {
        establishmentRepository.save(entry)
    }
}
