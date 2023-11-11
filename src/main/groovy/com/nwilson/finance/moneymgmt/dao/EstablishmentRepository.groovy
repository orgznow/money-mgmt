package com.nwilson.finance.moneymgmt.dao

import com.nwilson.finance.moneymgmt.entity.Establishment
import org.springframework.data.repository.CrudRepository

interface EstablishmentRepository extends CrudRepository<Establishment, Long> {
}