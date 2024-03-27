$(document).ready(function() {
    setFocusToAppropriateElement();
    setVisitDescriptionInFocusHandler();
    setItemDescriptionsInFocusHandler();
    setItemRateAmountsInFocusHandler();
    setItemBaseAmountsInFocusHandler();
    setItemTaxAmountsInFocusHandler();
    setItemFinalAmountsInFocusHandler();
    setItemFinalAmountsLoseFocusHandler();
    setCategoryLoseFocusHandler();
    setAddItemButtonTabOutHandler();
});

function determineJournalEntriesCount() {
    return $(".journalEntriesRows").length;
}

function determineActualJournalEntriesCount() {
    var numJournalEntryRows = determineJournalEntriesCount();
    if (numJournalEntryRows == 1) {
        let firstRowFinalAmount = parseFloat($(".journalEntriesRows:first > td:nth-of-type(11) > input").val());
        if (isNaN(firstRowFinalAmount)) {
            numJournalEntryRows--;
        }
    }
    return numJournalEntryRows;
}

function calculateBaseAmount(thisObj) {
    let rowRateAmount = parseFloat(thisObj.closest("tr").find(".journalEntriesRate").val());
    let rowQuantityAmount = parseFloat(thisObj.closest("tr").find(".journalEntriesQuantity").val());
    let baseAmount = (isNaN(rowRateAmount) ? 0.0 : rowRateAmount) * (isNaN(rowQuantityAmount) ? 0.0 : rowQuantityAmount);
    return baseAmount;
}

function calculateAmountWithDiscount(thisObj) {
    let rowBaseAmount = calculateBaseAmount(thisObj);
    let rowDiscountAmount = parseFloat(thisObj.closest("tr").find(".journalEntriesDiscountAmount").val());
    let amountWithDiscount = isNaN(rowDiscountAmount) ? rowBaseAmount : rowBaseAmount - rowDiscountAmount;
    return amountWithDiscount;
}

function calculateTaxAmount(thisObj) {
    let rowWithDiscountAmount = calculateAmountWithDiscount(thisObj);
    let rowIsTaxableChecked = thisObj.closest("tr").find(".journalEntriesIsTaxable").is(":checked");
    let taxAmount = (rowIsTaxableChecked) ? rowWithDiscountAmount * 0.075 : 0.0;
    return taxAmount;
}

function calculateFinalAmount(thisObj) {
    let amountWithDiscount = calculateAmountWithDiscount(thisObj);
    let taxAmount = calculateTaxAmount(thisObj);
    let rowTipAmount = parseFloat(thisObj.closest("tr").find(".journalEntriesTipAmount").val());
    let finalAmount = amountWithDiscount + taxAmount + (isNaN(rowTipAmount) ? 0.0 : rowTipAmount);
    return finalAmount;
}

function setFocusToAppropriateElement() {
    let visitTotalAmt = $("#visitTotalAmount").val();
    if (visitTotalAmt === "") {
         $("#visitTotalAmount").focus();
    } else {
        $(".journalEntriesRows:last > td:nth-of-type(2) > input").focus();
    }
}

function setVisitDescriptionInFocusHandler() {
    $("#visitDescription").focus(onVisitDescriptionInFocus);
}

function onVisitDescriptionInFocus() {
    let visitDate = $("#visitDate").val();
    let visitedStore = $("#visitedStore option:selected").text();
    let currentVisitDescription = $(this).val();
    if (currentVisitDescription === "") {
        let visitDescription = `${visitedStore} ${visitDate} Visit`;
        $(this).val(visitDescription);
    }
}

function setItemDescriptionsInFocusHandler() {
    $(".journalEntriesDescription").focus(onItemDescriptionInFocus);
}

function onItemDescriptionInFocus() {
    let numJournalEntryRows = determineJournalEntriesCount();
    if (numJournalEntryRows == 1) {
        let currentDescription = $(this).val();
        if (currentDescription === "") {
            let visitDescription = $("#visitDescription").val();
            $(this).val(visitDescription);
        }
    }
}

function setItemRateAmountsInFocusHandler() {
    $(".journalEntriesRate").focus(onItemRateAmountInFocus);
}

function onItemRateAmountInFocus() {
    let numJournalEntryRows = determineJournalEntriesCount();
    if (numJournalEntryRows == 1) {
        let currentRate = parseFloat($(this).val());
        if (isNaN(currentRate) || (currentRate == 0.0)) {
            let visitTotalAmount = parseFloat($("#visitTotalAmount").val());
            $(this).val(visitTotalAmount);
        }
    }
}

function setItemBaseAmountsInFocusHandler() {
    $(".journalEntriesBaseAmount").focus(onItemBaseAmountInFocus);
}

function onItemBaseAmountInFocus() {
    let baseAmount = calculateBaseAmount($(this));
    $(this).val(baseAmount.toFixed(2));
}

function setItemTaxAmountsInFocusHandler() {
    $(".journalEntriesTaxAmount").focus(onItemTaxAmountInFocus);
}

function onItemTaxAmountInFocus() {
    let taxAmount = calculateTaxAmount($(this));
    if (taxAmount != 0.0) {
        $(this).val(taxAmount.toFixed(2));
    } else {
        $(this).val("");
    }
}

function setItemFinalAmountsInFocusHandler() {
    $(".journalEntriesFinalAmount").focus(onItemFinalAmountInFocus);
}

function onItemFinalAmountInFocus() {
    let finalAmount = calculateFinalAmount($(this));
    $(this).val(finalAmount.toFixed(2));
}

function setItemFinalAmountsLoseFocusHandler() {
    $(".journalEntriesFinalAmount").focusout(onItemFinalAmountLoseFocus);
}

function onItemFinalAmountLoseFocus() {
    var grandTotal = 0.0;
    $(".journalEntriesFinalAmount").each(function(){
      let rowFinalAmount = parseFloat($(this).val());
      grandTotal = grandTotal + rowFinalAmount;
    });
    grandTotal = grandTotal.toFixed(2);
    $("#visitGrandTotal").text(grandTotal);
    let visitTotalAmount = parseFloat($("#visitTotalAmount").val());
    if (visitTotalAmount != grandTotal) {
        $("#visitGrandTotal").css("border", "3px solid yellow");
    } else {
        $("#visitGrandTotal").css("border", "3px solid green");
    }
}

function setCategoryLoseFocusHandler() {
    $(".journalEntriesSpendCategory").focusout(onCategoryLoseFocus);
}

function onCategoryLoseFocus() {
    $("#addItemBtn").focus();
}

function setAddItemButtonTabOutHandler() {
    $("#addItemBtn").keydown(onAddItemButtonTabOut);
}

function onAddItemButtonTabOut(e) {
    if (e.which == 9) {
        let numJournalEntryRows = determineActualJournalEntriesCount();
        if (numJournalEntryRows >= 1) {
            $("#addStoreVisitBtn").focus();
            e.preventDefault();
        }
    }
}
