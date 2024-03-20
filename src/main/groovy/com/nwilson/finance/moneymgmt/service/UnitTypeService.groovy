package com.nwilson.finance.moneymgmt.service

import com.nwilson.finance.moneymgmt.FinanceConverter
import com.nwilson.finance.moneymgmt.dao.UnitTypeRepository
import com.nwilson.finance.moneymgmt.entity.UnitType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UnitTypeService {

    @Autowired
    UnitTypeRepository unitTypeRepository

    List<Map> findAll() {
        List<Map> allUnitTypes = unitTypeRepository.findAll().collect {
            FinanceConverter.toUnitTypeMap(it)
        }.sort { it.name }
        Map defaultUnitType = allUnitTypes.find { it.isDefault }
        [defaultUnitType] + (allUnitTypes - defaultUnitType)
    }

    UnitType save(UnitType entry) {
        unitTypeRepository.save(entry)
    }
}
