package com.nwilson.finance.moneymgmt.service

import com.nwilson.finance.moneymgmt.FinanceConverter
import com.nwilson.finance.moneymgmt.dao.SpendCategoryRepository
import com.nwilson.finance.moneymgmt.entity.SpendCategory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class SpendCategoryService {

    @Autowired
    SpendCategoryRepository spendCategoryRepository

    List<Map> findAll() {
        spendCategoryRepository.findAll()
        List<Map> allSpendCategories = spendCategoryRepository.findAll().collect {
            FinanceConverter.toSpendCategoryMap(it)
        }.sort { it.name }
        Map defaultSpendCategory = allSpendCategories.find { it.isDefault }
        [defaultSpendCategory] + (allSpendCategories - defaultSpendCategory)
    }

    SpendCategory save(SpendCategory entry) {
        spendCategoryRepository.save(entry)
    }

    SpendCategory getCachedOrPersistentSpendCategory(Integer spendCategoryId, Map<Integer, SpendCategory> spendCategoryByIdMap) {
        SpendCategory spendCategory = spendCategoryByIdMap[(spendCategoryId)]
        if (!spendCategory) {
            spendCategory = spendCategoryRepository.findById(spendCategoryId).get()
            spendCategoryByIdMap[(spendCategoryId)] = spendCategory
        }
        spendCategory
    }
}
