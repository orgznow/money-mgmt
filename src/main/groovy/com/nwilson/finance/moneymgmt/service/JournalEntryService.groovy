package com.nwilson.finance.moneymgmt.service

import com.nwilson.finance.moneymgmt.FinanceConverter
import com.nwilson.finance.moneymgmt.dao.JournalEntryRepository
import com.nwilson.finance.moneymgmt.entity.JournalEntry
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class JournalEntryService {

    @Autowired
    JournalEntryRepository journalEntryRepository

    List<Map> findAll(Date entryDate) {
        def results = (entryDate) ? journalEntryRepository.findAllByEntryDate(entryDate) : journalEntryRepository.findAll()
        results.collect { FinanceConverter.toJournalEntryMap(it) }
    }

    List<JournalEntry> saveAll(List<JournalEntry> entries) {
        journalEntryRepository.saveAll(entries)
    }
}
