package com.nwilson.finance.moneymgmt.controller


import com.nwilson.finance.moneymgmt.entity.JournalEntry
import com.nwilson.finance.moneymgmt.services.JournalEntryService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody

@Controller
@RequestMapping(path="/entries")
class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService

    @GetMapping(path="/all")
    @ResponseBody Iterable<JournalEntry> findAll(@RequestParam(value="date", required=false) @DateTimeFormat(pattern="MM/dd/yyyy")
        Date entryDate) {
        journalEntryService.findAll(entryDate)
    }

    @PostMapping(path="/add")
    @ResponseBody JournalEntry addEntry(@RequestParam Date entryDate, @RequestParam String description, @RequestParam Double baseAmount,
        @RequestParam(required=false) Double discountAmount, @RequestParam boolean isTaxable, @RequestParam Double finalAmount,
        @RequestParam(required=false)  String comments, @RequestParam Integer categoryId, @RequestParam Integer storeId, @RequestParam Integer txTypeId) {
        /*SpendCategory theCategory
        Establishment theStore
        TransactionType theTxType*/
        JournalEntry entry = new JournalEntry(entryDate: entryDate, description: description, baseAmount: baseAmount, discountAmount: discountAmount,
            isTaxable: isTaxable, finalAmount: finalAmount, comments: comments)
        journalEntryService.addEntry(entry)
    }
}
