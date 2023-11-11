package com.nwilson.finance.moneymgmt.dao

import com.nwilson.finance.moneymgmt.entity.UnitType
import org.springframework.data.repository.CrudRepository

interface UnitTypeRepository extends CrudRepository<UnitType, Long> {
}