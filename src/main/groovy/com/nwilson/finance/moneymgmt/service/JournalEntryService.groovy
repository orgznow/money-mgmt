package com.nwilson.finance.moneymgmt.service

import com.nwilson.finance.moneymgmt.dao.JournalEntryRepository
import com.nwilson.finance.moneymgmt.entity.JournalEntry
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class JournalEntryService {

    @Autowired
    JournalEntryRepository journalEntryRepository

    List<JournalEntry> findAll(Date entryDate) {
        (entryDate) ? journalEntryRepository.findAllByEntryDate(entryDate) : journalEntryRepository.findAll()
    }

    JournalEntry saveAll(List<JournalEntry> entries) {
        journalEntryRepository.saveAll(entries)
    }
}
