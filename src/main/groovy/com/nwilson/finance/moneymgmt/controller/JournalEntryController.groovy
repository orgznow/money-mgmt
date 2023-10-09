package com.nwilson.finance.moneymgmt.controller


import com.nwilson.finance.moneymgmt.entity.JournalEntry
import com.nwilson.finance.moneymgmt.entity.SpendCategory
import com.nwilson.finance.moneymgmt.services.JournalEntryService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
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

    @GetMapping(path="/all-rest")
    @ResponseBody Iterable<JournalEntry> findAllRest(@RequestParam(value="date", required=false) @DateTimeFormat(pattern="MM/dd/yyyy")
        Date entryDate) {
        journalEntryService.findAll(entryDate)
    }

    @GetMapping(path="/all")
    String findAll(@RequestParam(value="date", required=false) @DateTimeFormat(pattern="MM/dd/yyyy")
        Date entryDate, Model model) {
        model.addAttribute("caption", (entryDate) ? "ALL ENTRIES FOR ${JournalEntry.DATE_FORMATTER.format(entryDate)}" : "ALL ENTRIES")
        List<JournalEntry> entries = journalEntryService.findAll(entryDate)
        model.addAttribute("entries", entries.sort {-it.entryDate.time })
        "all-entries"
    }

    @PostMapping(path="/add")
    @ResponseBody JournalEntry addEntry(@RequestParam Date entryDate, @RequestParam String description, @RequestParam Double baseAmount,
        @RequestParam(required=false) Double discountAmount, @RequestParam boolean isTaxable, @RequestParam Double finalAmount,
        @RequestParam(required=false)  String comments, @RequestParam Integer categoryId, @RequestParam Integer storeId, @RequestParam Integer txTypeId) {
        /*SpendCategory theCategory = new SpendCategory()
        Establishment theStore
        TransactionType theTxType*/
        JournalEntry entry = new JournalEntry(entryDate: entryDate, description: description, baseAmount: baseAmount, discountAmount: discountAmount,
            isTaxable: isTaxable, finalAmount: finalAmount, comments: comments)
        journalEntryService.addEntry(entry)
    }
}
