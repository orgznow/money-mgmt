package com.nwilson.finance.moneymgmt.dao

import com.nwilson.finance.moneymgmt.entity.TransactionType
import org.springframework.data.repository.CrudRepository

interface TransactionTypeRepository extends CrudRepository<TransactionType, Long> {
}