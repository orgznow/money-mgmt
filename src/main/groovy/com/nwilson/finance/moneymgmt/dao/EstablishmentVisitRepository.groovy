package com.nwilson.finance.moneymgmt.dao

import com.nwilson.finance.moneymgmt.entity.EstablishmentVisit
import org.springframework.data.repository.CrudRepository

interface EstablishmentVisitRepository extends CrudRepository<EstablishmentVisit, Long> {
    List<EstablishmentVisit> findAllByVisitDate(Date visitDate)
}