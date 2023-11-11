package com.nwilson.finance.moneymgmt.dao

import com.nwilson.finance.moneymgmt.entity.SpendCategory
import org.springframework.data.repository.CrudRepository

interface SpendCategoryRepository extends CrudRepository<SpendCategory, Long> {
}