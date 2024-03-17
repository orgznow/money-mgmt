window.onload = function() {
  document.getElementById("visitTotalAmount").focus();
}

function onItemBaseAmountInFocus(element) {
    let rowId = element.id.split(".")[0];
    let rowRateAmount = parseFloat(document.getElementById(rowId + ".rateAmount").value);
    let rowQuantityAmount = parseFloat(document.getElementById(rowId + ".quantity").value);
    element.value = rowRateAmount * rowQuantityAmount;
}

function onItemTaxAmountInFocus(element) {
    let rowId = element.id.split(".")[0];
    let rowBaseAmount = parseFloat(document.getElementById(rowId + ".baseAmount").value);
    let rowDiscountAmount = parseFloat(document.getElementById(rowId + ".discountAmount").value);
    let rowWithDiscountAmount = isNaN(rowDiscountAmount) ? rowBaseAmount : rowBaseAmount - rowDiscountAmount;
    let rowIsTaxableChecked = document.getElementById(rowId + ".isTaxable1").checked;
    let taxAmount = (rowIsTaxableChecked) ? rowWithDiscountAmount * 0.075 : 0.0;
    if (taxAmount != 0.0) {
        element.value = taxAmount.toFixed(2);
    }
}

function onItemFinalAmountInFocus(element) {
    let rowId = element.id.split(".")[0];
    let rowBaseAmount = parseFloat(document.getElementById(rowId + ".baseAmount").value);
    let rowDiscountAmount = parseFloat(document.getElementById(rowId + ".discountAmount").value);
    let rowWithDiscountAmount = isNaN(rowDiscountAmount) ? rowBaseAmount : rowBaseAmount - rowDiscountAmount;
    let rowIsTaxableChecked = document.getElementById(rowId + ".isTaxable1").checked;
    let taxAmount = (rowIsTaxableChecked) ? parseFloat(document.getElementById(rowId + ".taxAmount").value) : 0.0;
    let rowTipAmount = parseFloat(document.getElementById(rowId + ".tipAmount").value);
    let finalAmount = rowWithDiscountAmount + (isNaN(taxAmount) ? 0.0 : taxAmount) + (isNaN(rowTipAmount) ? 0.0 : rowTipAmount);
    element.value = finalAmount;
}

function onCategoryLoseFocus(element) {
    window.setTimeout(function() {
        document.getElementById("addItemBtn").focus({ focusVisible: true });
    }, 0);
}