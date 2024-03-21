package com.nwilson.finance.moneymgmt.service

import com.nwilson.finance.moneymgmt.FinanceConverter
import com.nwilson.finance.moneymgmt.controller.cmd.JournalEntryCmd
import com.nwilson.finance.moneymgmt.dao.JournalEntryRepository
import com.nwilson.finance.moneymgmt.entity.EstablishmentVisit
import com.nwilson.finance.moneymgmt.entity.JournalEntry
import com.nwilson.finance.moneymgmt.entity.SpendCategory
import com.nwilson.finance.moneymgmt.entity.UnitType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class JournalEntryService {

    @Autowired
    JournalEntryRepository journalEntryRepository

    @Autowired
    SpendCategoryService spendCategoryService

    @Autowired
    UnitTypeService unitTypeService

    List<Map> findAll(Date entryDate) {
        def results = (entryDate) ? journalEntryRepository.findAllByEntryDate(entryDate) : journalEntryRepository.findAll()
        results.collect { FinanceConverter.toJournalEntryMap(it) }
    }

    List<JournalEntry> saveAll(List<JournalEntryCmd> entriesToSave, List<JournalEntry> theExistingEntries, EstablishmentVisit storeVisit) {
        Map<Integer, JournalEntry> theExistingEntriesById = theExistingEntries.collectEntries { [ (it.id): it ] }
        List<JournalEntry> entitiesToSave = []
        Map<Integer, SpendCategory> spendCategoryByIdMap = [:]
        Map<Integer, UnitType> unitTypeByIdMap = [:]
        entriesToSave.each {
            JournalEntry theEntry = (it.id) ? theExistingEntriesById[it.id] : new JournalEntry()
            if (!theEntry) {
                log.error("JournalEntry ${it} is not associated with store visit ${storeVisit.id}, skipping ...!!")
            } else {
                SpendCategory theCategory = spendCategoryService.getCachedOrPersistentSpendCategory(it.spendCategoryId, spendCategoryByIdMap)
                UnitType theUnitType = unitTypeService.getCachedOrPersistentUnitType(it.unitTypeId, unitTypeByIdMap)
                entitiesToSave << theEntry.toJournalEntry(it, storeVisit, theCategory, theUnitType)
            }
        }
        saveAll(entitiesToSave)
    }

    List<JournalEntry> saveAll(List<JournalEntry> entries) {
        journalEntryRepository.saveAll(entries)
    }
}
