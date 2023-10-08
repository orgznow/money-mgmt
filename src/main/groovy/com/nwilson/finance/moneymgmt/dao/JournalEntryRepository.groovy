package com.nwilson.finance.moneymgmt.dao

import com.nwilson.finance.moneymgmt.entity.JournalEntry
import org.springframework.data.repository.CrudRepository

interface JournalEntryRepository extends CrudRepository<JournalEntry, Long> {
    List<JournalEntry> findAllByEntryDate(Date entryDate)
}