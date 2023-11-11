package com.nwilson.finance.moneymgmt.controller

import com.nwilson.finance.moneymgmt.entity.Establishment
import com.nwilson.finance.moneymgmt.entity.EstablishmentVisit
import com.nwilson.finance.moneymgmt.entity.JournalEntry
import com.nwilson.finance.moneymgmt.entity.SpendCategory
import com.nwilson.finance.moneymgmt.entity.TransactionType
import com.nwilson.finance.moneymgmt.entity.UnitType
import com.nwilson.finance.moneymgmt.service.EstablishmentService
import com.nwilson.finance.moneymgmt.service.EstablishmentVisitService
import com.nwilson.finance.moneymgmt.service.JournalEntryService
import com.nwilson.finance.moneymgmt.service.SpendCategoryService
import com.nwilson.finance.moneymgmt.service.TransactionTypeService
import com.nwilson.finance.moneymgmt.service.UnitTypeService
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.ModelMap
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody

@Controller
@Slf4j
class JournalEntryMgrController {

    @Autowired
    private JournalEntryService journalEntryService

    @Autowired
    private EstablishmentVisitService establishmentVisitService

    @Autowired
    private EstablishmentService establishmentService

    @Autowired
    private SpendCategoryService spendCategoryService

    @Autowired
    private UnitTypeService unitTypeService

    @Autowired
    private TransactionTypeService transactionTypeService

    @GetMapping(value="/all-rest", produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody Iterable<JournalEntry> findAll(@RequestParam(value="date", required=false) @DateTimeFormat(pattern="MM/dd/yyyy")
        Date entryDate) {
        journalEntryService.findAll(entryDate)
    }

    @ModelAttribute("allTxTypes")
    List<TransactionType> populateTransactionTypes() {
        transactionTypeService.findAll().sort {it.name }
    }

    @ModelAttribute("allUnitTypes")
    List<UnitType> populateUnitTypes() {
        unitTypeService.findAll().sort { it.name }
    }

    @ModelAttribute("allCategories")
    List<SpendCategory> populateSpendCategories() {
        spendCategoryService.findAll().sort { it.name }
    }

    @ModelAttribute("allStores")
    List<Establishment> populateEstablishments() {
        establishmentService.findAll().sort {a, b -> a.name <=> b.name ?: a.zipCode <=> b.zipCode }
    }

    @ModelAttribute("allStoreVisits")
    List<EstablishmentVisit> populateEstablishmentVisits() {
        establishmentVisitService.findAll().sort {-it.visitDate.time }
    }

    @GetMapping(value="/all-plain")
    String showAll(@RequestParam(value="date", required=false) @DateTimeFormat(pattern="MM/dd/yyyy")
        Date entryDate, Model model) {
        model.addAttribute("caption", (entryDate) ? "ALL ENTRIES FOR ${JournalEntry.DATE_FORMATTER.format(entryDate)}" : "ALL ENTRIES")
        List<JournalEntry> entries = journalEntryService.findAll(entryDate)
        model.addAttribute("entries", entries.sort {-it.entryDate.time })
        "all-entries"
    }

    //@GetMapping(value="/all")
    @RequestMapping(value=["/", "/all-entries-mgr"])
    String showEstablishmentVisits(final EstablishmentVisit establishmentVisit) {
        log.debug("Entered showEstablishmentVisits(establishmentVisit=${establishmentVisit})")
        establishmentVisit.visitDate = new Date()
        "all-entries-mgr"
    }

    //@RequestMapping(value="/all", params=["save"], method=RequestMethod.POST)
    //@PostMapping(value="/all", params="save")
    //@PostMapping(params="save")
    //@RequestMapping(params="save")
    @RequestMapping(value="/all-entries-mgr", params=["save"])
    String saveEstablishmentVisit(final EstablishmentVisit establishmentVisit, final BindingResult bindingResult, final ModelMap model) {
        log.debug("Entered saveEstablishmentVisit(establishmentVisit=${establishmentVisit}, bindingResult=${bindingResult}, model=${model})")
        if (bindingResult.hasErrors()) {
            log.error("bindingResult.hasErrors=true")
            "all-entries-mgr"
        } else {
            this.establishmentVisitService.save(establishmentVisit)
            //this.journalEntryService.saveAll(establishmentVisit.theJournalEntries)
            log.debug("Saved establishmentVisit")
            model.clear()
            "redirect:/all-entries-mgr"
        }
    }

    //@RequestMapping(value="/all", params=["addItem"], method=RequestMethod.POST)
    //@PostMapping(value="/all", params="addItem")
    //@PostMapping(params="addItem")
    //@RequestMapping(params="addItem")
    @RequestMapping(value="/all-entries-mgr", params=["addItem"])
    String addJournalEntry(final EstablishmentVisit establishmentVisit, final BindingResult bindingResult) {
        log.debug("Entered addJournalEntry(establishmentVisit=${establishmentVisit}, bindingResult=${bindingResult})")
        //JournalEntry entry = new JournalEntry()
        //entry.theStoreVisit = storeVisit
        //establishmentVisit.theJournalEntries.add(entry)
        if (establishmentVisit.journalEntries == null) {
            log.debug("establishmentVisit.journalEntries is null")
            establishmentVisit.journalEntries = []
        }
        establishmentVisit.journalEntries.add(new JournalEntry())
        log.debug("Added journalEntry row to establishmentVisit)")
        "all-entries-mgr"
    }
}
