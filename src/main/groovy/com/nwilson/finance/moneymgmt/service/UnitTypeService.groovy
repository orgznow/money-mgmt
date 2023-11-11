package com.nwilson.finance.moneymgmt.service

import com.nwilson.finance.moneymgmt.dao.UnitTypeRepository
import com.nwilson.finance.moneymgmt.entity.UnitType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UnitTypeService {

    @Autowired
    UnitTypeRepository unitTypeRepository

    List<UnitType> findAll() {
        unitTypeRepository.findAll()
    }

    UnitType save(UnitType entry) {
        unitTypeRepository.save(entry)
    }
}
