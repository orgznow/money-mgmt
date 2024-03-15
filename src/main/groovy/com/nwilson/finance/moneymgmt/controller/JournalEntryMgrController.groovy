package com.nwilson.finance.moneymgmt.controller

import com.nwilson.finance.moneymgmt.entity.Establishment
import com.nwilson.finance.moneymgmt.entity.EstablishmentVisit
import com.nwilson.finance.moneymgmt.entity.JournalEntry
import com.nwilson.finance.moneymgmt.entity.SpendCategory
import com.nwilson.finance.moneymgmt.entity.TransactionType
import com.nwilson.finance.moneymgmt.entity.UnitType
import com.nwilson.finance.moneymgmt.input.ViewConfigInput
import com.nwilson.finance.moneymgmt.service.EstablishmentService
import com.nwilson.finance.moneymgmt.service.EstablishmentVisitService
import com.nwilson.finance.moneymgmt.service.JournalEntryService
import com.nwilson.finance.moneymgmt.service.SpendCategoryService
import com.nwilson.finance.moneymgmt.service.TransactionTypeService
import com.nwilson.finance.moneymgmt.service.UnitTypeService
import groovy.util.logging.Slf4j
import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody

import java.text.SimpleDateFormat
import java.time.ZoneId
import java.time.temporal.ChronoField

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
    @ResponseBody Iterable<JournalEntry> findAll(@RequestParam(value="date", required=false) @DateTimeFormat(pattern="MM/dd/yyyy")
        Date entryDate) {
        journalEntryService.findAll(entryDate)
    }

    @ModelAttribute("allTxTypes")
    List<TransactionType> populateTransactionTypes() {
        log.debug("In populateTransactionTypes()")
        transactionTypeService.findAll().sort {it.name }
    }

    @ModelAttribute("allUnitTypes")
    List<UnitType> populateUnitTypes() {
        log.debug("In populateUnitTypes()")
        unitTypeService.findAll().sort { it.name }
    }

    @ModelAttribute("allCategories")
    List<SpendCategory> populateSpendCategories() {
        log.debug("In populateSpendCategories()")
        spendCategoryService.findAll().sort { it.name }
    }

    @ModelAttribute("allStores")
    List<Establishment> populateEstablishments() {
        log.debug("In populateEstablishments()")
        establishmentService.findAll().sort {a, b -> a.name <=> b.name ?: a.zipCode <=> b.zipCode }
    }

    @ModelAttribute("allStoreVisitsInfo")
    Map populateEstablishmentVisits(final ModelMap model) {
        ViewConfigInput viewConfigInput = model.getAttribute("displayMonthYear")
        if (!viewConfigInput) {
            viewConfigInput = new ViewConfigInput()
        }
        viewConfigInput.displayMonthYear = viewConfigInput.displayMonthYear ?: new SimpleDateFormat('yyyy-MM').format(new Date())
        log.debug("In populateEstablishmentVisits() for ${viewConfigInput.displayMonthYear}")
        toResults(establishmentVisitService.findAll(viewConfigInput.displayMonthYear))
    }

    private static Map toResults(List<EstablishmentVisit> storeVisits) {
        List<EstablishmentVisit> sortedStoreVisits = storeVisits.sort { a, b ->
            -a.visitDate.time <=> -b.visitDate.time ?: -a.id <=> -b.id
        }
        Map<Integer, List<EstablishmentVisit>> storeVisitsByWeekOfMonth = sortedStoreVisits.groupBy {
            it.visitDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().get(ChronoField.ALIGNED_WEEK_OF_MONTH)
        }.sort()
        Map<String, Map<String, BigDecimal>> weeklySpendByCategories = toWeeklySpendByCategories(storeVisitsByWeekOfMonth)
        Map<String, Map<String, BigDecimal>> weeklySpendByTxTypes = toWeeklySpendByTxTypes(storeVisitsByWeekOfMonth)
        List<Map<String, String>> weeklySpendTotals = weeklySpendByCategories.collect { k, v -> ["${k}": "\$${v['Total'].toString()}"] }
        List<Map<String, String>> weeklySpendTotalsAlt = weeklySpendByTxTypes.collect { k, v -> ["${k}": "\$${v['Total'].toString()}"] }
        if (weeklySpendTotals != weeklySpendTotalsAlt) {
            log.error("weeklySpendTotals ${weeklySpendTotals} does not match weeklySpendTotalsAlt ${weeklySpendTotalsAlt}")
        }
        BigDecimal totalMonthlySpendToDate = storeVisits.visitTotalAmount.sum()
        BigDecimal totalMonthlySpendToDateAlt = storeVisits.journalEntries.finalAmount.flatten().sum()
        if (totalMonthlySpendToDate != totalMonthlySpendToDateAlt) {
            log.error("totalMonthlySpendToDate ${totalMonthlySpendToDate} does not match totalMonthlySpendToDateAlt ${totalMonthlySpendToDateAlt}")
        }
        Map storeVisitsInfo = [
            totalMonthlySpendToDate: totalMonthlySpendToDate, weeklySpendTotals: weeklySpendTotals, weeklySpendByCategories: weeklySpendByCategories,
            weeklySpendByTxTypes: weeklySpendByTxTypes, allStoreVisits: sortedStoreVisits
        ]
        log.debug("Returning storeVisitsInfo as ${storeVisitsInfo}")
        storeVisitsInfo
    }

    private static Map<String, Map<String, Double>> toWeeklySpendByCategories(Map<Integer, List<EstablishmentVisit>> storeVisitsByWeekOfMonth) {
        Map<String, Map<String, BigDecimal>> weeklySpendByCategories = [:]
        storeVisitsByWeekOfMonth.each {  weekNbr, weeklyStoreVisits ->
            List<JournalEntry> allWeeklyJournalEntries = weeklyStoreVisits*.journalEntries.flatten()
            log.trace("In week ${weekNbr} - all journal entry finalAmounts are: ${allWeeklyJournalEntries.finalAmount} with a total of ${allWeeklyJournalEntries.finalAmount.sum()}")
            Map<String, List<JournalEntry>> weeklyJournalEntriesByCategory = allWeeklyJournalEntries.groupBy {  je -> je.spendCategory.description }.sort()
            Map<String, BigDecimal> spendByCategory = [:]
            weeklyJournalEntriesByCategory.each { category, journalEntries ->
                BigDecimal totalWeeklySpendByCategory = journalEntries*.finalAmount.sum()
                log.trace("Total week ${weekNbr} spend by category ${category} is ${totalWeeklySpendByCategory}")
                spendByCategory << [(category): totalWeeklySpendByCategory]
            }
            spendByCategory << [Total: allWeeklyJournalEntries.finalAmount.sum()]
            spendByCategory = spendByCategory.sort()
            weeklySpendByCategories << ["Week${weekNbr}": spendByCategory]
        }
        weeklySpendByCategories = weeklySpendByCategories.sort()
        log.debug("weeklySpendByCategories=${weeklySpendByCategories}")
        weeklySpendByCategories
    }

    private static Map<String, Map<String, Double>> toWeeklySpendByTxTypes(SortedMap<Integer, List<EstablishmentVisit>> storeVisitsByWeekOfMonth) {
        Map<String, Map<String, BigDecimal>> weeklySpendByTxTypes = [:]
        storeVisitsByWeekOfMonth.each {  weekNbr, weeklyStoreVisits ->
            log.trace("In week ${weekNbr} - all visitTotalAmount are: ${weeklyStoreVisits.visitTotalAmount} with a total of ${weeklyStoreVisits.visitTotalAmount.sum()}")
            Map<String, List<EstablishmentVisit>> weeklyVisitsByTxType = weeklyStoreVisits.groupBy { visit ->
                visit.transactionType.name
            }
            Map<String, BigDecimal> spendByTxTypes = [:]
            weeklyVisitsByTxType.each { txType, visits ->
                BigDecimal totalWeeklySpendByTxType = visits*.visitTotalAmount.flatten().sum()
                log.trace("Total week ${weekNbr} spend by txType ${txType} is ${totalWeeklySpendByTxType}")
                spendByTxTypes << [(txType): totalWeeklySpendByTxType]
            }
            spendByTxTypes << [Total: weeklyStoreVisits.visitTotalAmount.sum()]
            spendByTxTypes = spendByTxTypes.sort()
            weeklySpendByTxTypes << ["Week${weekNbr}": spendByTxTypes]
        }
        weeklySpendByTxTypes = weeklySpendByTxTypes.sort()
        log.debug("weeklySpendByTxTypes=${weeklySpendByTxTypes}")
        weeklySpendByTxTypes
    }

    @ModelAttribute("displayMonthYear")
    ViewConfigInput getDisplayMonthYear(final ModelMap model) {
        ViewConfigInput viewConfigInput = model.getAttribute("displayMonthYear")
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
        List<EstablishmentVisit> visits = establishmentVisitService.findAll(viewConfigInput.displayMonthYear).sort { a, b -> -a.visitDate.time <=> -b.visitDate.time ?: -a.id <=> -b.id }
        model.addAttribute("allStoreVisits", visits)
        EstablishmentVisit visit = new EstablishmentVisit(visitDate: new SimpleDateFormat('yyyy-MM').parse(viewConfigInput.displayMonthYear), journalEntries: [new JournalEntry(quantity: 1.0)])
        model.addAttribute("establishmentVisit", visit)
        "all-entries-mgr"
    }

    @RequestMapping(value=["/", "/all-entries-mgr"], method=RequestMethod.GET)
    String showEstablishmentVisits(final EstablishmentVisit establishmentVisit) {
        log.trace("Entered showEstablishmentVisits(establishmentVisit=${establishmentVisit})")
        establishmentVisit.visitDate = new Date()
        if (establishmentVisit.journalEntries == null) {
            establishmentVisit.journalEntries = [new JournalEntry(quantity: 1.0)]
        }
        "all-entries-mgr"
    }

    @RequestMapping(value="/all-entries-mgr", params=["save"], method=RequestMethod.POST)
    String saveEstablishmentVisit(final EstablishmentVisit establishmentVisit, final BindingResult bindingResult, final ModelMap model) {
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

    @RequestMapping(value="/all-entries-mgr", params=["addItem"], method=RequestMethod.POST)
    String addJournalEntry(final EstablishmentVisit establishmentVisit, final BindingResult bindingResult) {
        log.trace("Entered addJournalEntry(establishmentVisit=${establishmentVisit}, bindingResult=${bindingResult})")
        establishmentVisit.journalEntries.add(new JournalEntry(quantity: 1.0))
        log.trace("Added journalEntry row to establishmentVisit)")
        "all-entries-mgr"
    }

    @RequestMapping(value="/all-entries-mgr", params=["removeItem"], method=RequestMethod.POST)
    String removeJournalEntry(final EstablishmentVisit establishmentVisit, final BindingResult bindingResult, final HttpServletRequest req) {
        log.trace("Entered removeJournalEntry(establishmentVisit=${establishmentVisit}, bindingResult=${bindingResult})")
        Integer rowNum = Integer.valueOf(req.getParameter('removeItem'))
        establishmentVisit.journalEntries.remove(rowNum)
        log.trace("Remvoved journalEntry row # ${rowNum} from establishmentVisit)")
        "all-entries-mgr"
    }
}
