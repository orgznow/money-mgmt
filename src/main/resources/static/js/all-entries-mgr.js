window.onload = function() {
  document.getElementById("visitTotalAmount").focus();
}

function onItemBaseAmountInFocus(element) {
    const elementCompoundId = element.id.split(".");
    let rowId = elementCompoundId[0];

    let rowRateEl = document.getElementById(rowId + ".rateAmount");
    let rowRateAmount = parseFloat(rowRateEl.value);

    let rowQuantityEl = document.getElementById(rowId + ".quantity");
    let rowQuantityAmount = parseFloat(rowQuantityEl.value);

    element.value = rowRateAmount * rowQuantityAmount;
}

function onItemTaxAmountInFocus(element) {
    const elementCompoundId = element.id.split(".");
    let rowId = elementCompoundId[0];

    let rowIsTaxableEl = document.getElementById(rowId + ".isTaxable1");
    let rowIsTaxableChecked = rowIsTaxableEl.checked;

    let rowBaseAmountEl = document.getElementById(rowId + ".baseAmount");
    let rowBaseAmount = parseFloat(rowBaseAmountEl.value);

    let rowDiscountEl = document.getElementById(rowId + ".discountAmount");
    let rowDiscountAmount = parseFloat(rowDiscountEl.value);

    let rowWithDiscountAmount = isNaN(rowDiscountAmount) ? rowBaseAmount : rowBaseAmount - rowDiscountAmount;

    let taxAmount = (rowIsTaxableChecked) ? rowWithDiscountAmount * 0.075 : 0.0;

    if (taxAmount != 0.0) {
        element.value = taxAmount;
    }
}

function onItemFinalAmountInFocus(element) {
    const elementCompoundId = element.id.split(".");
    let rowId = elementCompoundId[0];

    let rowBaseAmountEl = document.getElementById(rowId + ".baseAmount");
    let rowBaseAmount = parseFloat(rowBaseAmountEl.value);

    let rowDiscountEl = document.getElementById(rowId + ".discountAmount");
    let rowDiscountAmount = parseFloat(rowDiscountEl.value);

    let rowWithDiscountAmount = isNaN(rowDiscountAmount) ? rowBaseAmount : rowBaseAmount - rowDiscountAmount;

    let rowIsTaxableEl = document.getElementById(rowId + ".isTaxable1");
    let rowIsTaxableChecked = rowIsTaxableEl.checked;

    let taxAmount = (rowIsTaxableChecked) ? rowWithDiscountAmount * 0.075 : 0.0;

    let rowTipEl = document.getElementById(rowId + ".tipAmount");
    let rowTipAmount = parseFloat(rowTipEl.value);

    let finalAmount = rowWithDiscountAmount + taxAmount + (isNaN(rowTipAmount) ? 0.0 : rowTipAmount);

    element.value = finalAmount;
}

function onCategoryLoseFocus(element) {
    window.setTimeout(function() {
        document.getElementById("addItemBtn").focus({ focusVisible: true });
    }, 0);
}