package com.nwilson.finance.moneymgmt.service

import com.nwilson.finance.moneymgmt.FinanceConverter
import com.nwilson.finance.moneymgmt.dao.EstablishmentRepository
import com.nwilson.finance.moneymgmt.entity.Establishment
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class EstablishmentService {

    @Autowired
    EstablishmentRepository establishmentRepository

    List<Map> findAll() {
        establishmentRepository.findAll().collect {
            FinanceConverter.toEstablishmentMap(it)
        }.sort {a, b -> a.name <=> b.name ?: a.zipCode <=> b.zipCode }
    }

    Establishment save(Establishment entry) {
        establishmentRepository.save(entry)
    }
}
