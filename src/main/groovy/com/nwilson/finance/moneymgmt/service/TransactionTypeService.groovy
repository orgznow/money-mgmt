package com.nwilson.finance.moneymgmt.service

import com.nwilson.finance.moneymgmt.dao.TransactionTypeRepository
import com.nwilson.finance.moneymgmt.entity.TransactionType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class TransactionTypeService {

    @Autowired
    TransactionTypeRepository transactionTypeRepository

    List<TransactionType> findAll() {
        transactionTypeRepository.findAll()
    }

    TransactionType save(TransactionType entry) {
        transactionTypeRepository.save(entry)
    }
}
