package com.nwilson.finance.moneymgmt.service

import com.nwilson.finance.moneymgmt.FinanceConverter
import com.nwilson.finance.moneymgmt.dao.TransactionTypeRepository
import com.nwilson.finance.moneymgmt.entity.TransactionType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class TransactionTypeService {

    @Autowired
    TransactionTypeRepository transactionTypeRepository

    List<Map> findAll() {
        List<Map> allTxTypes = transactionTypeRepository.findAll().collect {
            FinanceConverter.toTransactionType(it)
        }.sort { it.name }
        Map defaultTxType = allTxTypes.find { it.isDefault }
        [defaultTxType] + (allTxTypes - defaultTxType)
    }

    TransactionType save(TransactionType entry) {
        transactionTypeRepository.save(entry)
    }
}
