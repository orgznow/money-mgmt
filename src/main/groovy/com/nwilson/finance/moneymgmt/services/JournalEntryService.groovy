package com.nwilson.finance.moneymgmt.services

import com.nwilson.finance.moneymgmt.dao.JournalEntryRepository
import com.nwilson.finance.moneymgmt.entity.JournalEntry
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class JournalEntryService {

    @Autowired
    JournalEntryRepository journalEntryRepository

    public Iterable<JournalEntry> findAll(Date entryDate) {
        (entryDate) ? journalEntryRepository.findAllByEntryDate(entryDate) : journalEntryRepository.findAll()
    }

    public JournalEntry addEntry(JournalEntry entry) {
        journalEntryRepository.save(entry)
    }
}
