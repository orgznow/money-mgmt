package com.nwilson.finance.moneymgmt.controller

import com.nwilson.finance.moneymgmt.controller.cmd.EstablishmentVisitCmd
import com.nwilson.finance.moneymgmt.controller.cmd.JournalEntryCmd
import com.nwilson.finance.moneymgmt.input.ViewConfigInput
import com.nwilson.finance.moneymgmt.service.EstablishmentService
import com.nwilson.finance.moneymgmt.service.EstablishmentVisitService
import com.nwilson.finance.moneymgmt.service.JournalEntryService
import com.nwilson.finance.moneymgmt.service.SpendCategoryService
import com.nwilson.finance.moneymgmt.service.TransactionTypeService
import com.nwilson.finance.moneymgmt.service.UnitTypeService
import groovy.util.logging.Slf4j
import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.ModelMap
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody

import java.text.SimpleDateFormat

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

    @Deprecated
    @GetMapping(value="/all-rest", produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody List<Map> findAll(@RequestParam(value="date", required=false) @DateTimeFormat(pattern="MM/dd/yyyy") Date entryDate) {
        journalEntryService.findAll(entryDate)
    }

    @ModelAttribute("allTxTypes")
    List<Map> getAllTransactionTypes() {
        transactionTypeService.findAll()
    }

    @ModelAttribute("allUnitTypes")
    List<Map> getAllUnitTypes() {
        unitTypeService.findAll()
    }

    @ModelAttribute("allCategories")
    List<Map> getAllSpendCategories() {
        spendCategoryService.findAll()
    }

    @ModelAttribute("allStores")
    List<Map> getAllEstablishments() {
        establishmentService.findAll()
    }

    @ModelAttribute("displayMonthYear")
    static ViewConfigInput getDisplayMonthYear(final ModelMap model) {
        ViewConfigInput viewConfigInput = model.getAttribute("displayMonthYear") as ViewConfigInput
        log.trace("In getDisplayMonthYear() with viewConfigInput ${viewConfigInput}")
        if (!viewConfigInput) {
            viewConfigInput = new ViewConfigInput()
        }
        viewConfigInput.displayMonthYear = viewConfigInput.displayMonthYear ?: new SimpleDateFormat('yyyy-MM').format(new Date()) //'2023-11'
        log.trace("Leaving getDisplayMonthYear() with ${viewConfigInput}")
        viewConfigInput
    }

    @RequestMapping(value="/all-entries-mgr", params=["displayMonthYear"], method=RequestMethod.POST)
    String setDisplayMonthYear(final ViewConfigInput viewConfigInput, final BindingResult bindingResult, final ModelMap model) {
        log.trace("Entered setDisplayMonthYear() with displayMonthYear ${viewConfigInput}")
        model.addAttribute("displayMonthYear", viewConfigInput)
        List<Map> visits = establishmentVisitService.findAll(viewConfigInput.displayMonthYear)
        model.addAttribute("allStoreVisits", visits)
        EstablishmentVisitCmd visit = new EstablishmentVisitCmd(
            visitDate: new SimpleDateFormat('yyyy-MM').parse(viewConfigInput.displayMonthYear),
            journalEntries: [new JournalEntryCmd(quantity: 1.0)]
        )
        log.debug("In setDisplayMonthYear ... visit is ${visit}")
        model.addAttribute("establishmentVisit", visit)
        "all-entries-mgr"
    }

    @ModelAttribute("allStoreVisitsInfo")
    Map getMonthlySpendInfo(final ModelMap model) {
        ViewConfigInput viewConfigInput = model.getAttribute("displayMonthYear") as ViewConfigInput
        if (!viewConfigInput) {
            viewConfigInput = new ViewConfigInput()
        }
        viewConfigInput.displayMonthYear = viewConfigInput.displayMonthYear ?: new SimpleDateFormat('yyyy-MM').format(new Date())
        log.debug("In getMonthlySpendInfo() for ${viewConfigInput.displayMonthYear}")
        establishmentVisitService.getMonthlySpendInfo(viewConfigInput.displayMonthYear)
    }

    @RequestMapping(value=["/", "/all-entries-mgr"], method=RequestMethod.GET)
    static String showEstablishmentVisits(final EstablishmentVisitCmd establishmentVisit, final Model model, final BindingResult bindingResult) {
        log.trace("Entered showEstablishmentVisits(establishmentVisit=${establishmentVisit}), bindingResult=${bindingResult}")
        establishmentVisit.visitDate = new Date()
        if (establishmentVisit.journalEntries == null) {
            establishmentVisit.journalEntries = [new JournalEntryCmd(quantity: 1.0)]
        }
        log.trace("In showEstablishmentVisits(establishmentVisit after initialization=${establishmentVisit})")
        model.addAttribute("establishmentVisit", establishmentVisit)
        "all-entries-mgr"
    }

    @RequestMapping(value="/all-entries-mgr", params=["addItem"], method=RequestMethod.POST)
    String addJournalEntry(final EstablishmentVisitCmd establishmentVisit, final Model model, final BindingResult bindingResult) {
        log.trace("Entered addJournalEntry(establishmentVisit=${establishmentVisit}, bindingResult=${bindingResult})")
        establishmentVisit.journalEntries.add(new JournalEntryCmd(quantity: 1.0))
        log.trace("Added journalEntry row to establishmentVisit)")
        model.addAttribute("establishmentVisit", establishmentVisit)
        "all-entries-mgr"
    }

    @RequestMapping(value="/all-entries-mgr", params=["removeItem"], method=RequestMethod.POST)
    String removeJournalEntry(/*@RequestBody*/ final EstablishmentVisitCmd establishmentVisit, final BindingResult bindingResult, final Model model, final HttpServletRequest req) {
        log.trace("Entered removeJournalEntry(establishmentVisit=${establishmentVisit}, bindingResult=${bindingResult})")
        Integer rowNum = Integer.valueOf(req.getParameter('removeItem'))
        establishmentVisit.journalEntries.remove(rowNum)
        log.trace("Removed journalEntry row # ${rowNum} from establishmentVisit)")
        model.addAttribute("establishmentVisit", establishmentVisit)
        "all-entries-mgr"
    }

    @RequestMapping(value="/all-entries-mgr", params=["save"], method=RequestMethod.POST)
    String saveEstablishmentVisit(/*@RequestBody*/ @Valid final EstablishmentVisitCmd establishmentVisit, final BindingResult bindingResult, final ModelMap model) {
        log.trace("Entered saveEstablishmentVisit(establishmentVisit=${establishmentVisit}, bindingResult=${bindingResult}, model=${model})")
        if (bindingResult.hasErrors()) {
            log.error("bindingResult.hasErrors=true")
            bindingResult.allErrors.each {
                log.error("Current error is : ${it}")
            }
            "all-entries-mgr"
        } else {
            this.establishmentVisitService.save(establishmentVisit)
            log.trace("Saved establishmentVisit")
            model.clear()
            "redirect:/all-entries-mgr"
        }
    }
}
