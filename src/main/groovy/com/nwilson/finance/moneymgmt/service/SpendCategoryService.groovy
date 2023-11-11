package com.nwilson.finance.moneymgmt.service

import com.nwilson.finance.moneymgmt.dao.SpendCategoryRepository
import com.nwilson.finance.moneymgmt.entity.SpendCategory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class SpendCategoryService {

    @Autowired
    SpendCategoryRepository spendCategoryRepository

    List<SpendCategory> findAll() {
        spendCategoryRepository.findAll()
    }

    SpendCategory save(SpendCategory entry) {
        spendCategoryRepository.save(entry)
    }
}
