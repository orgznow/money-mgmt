function onItemDescriptionInFocus(element) {
    let elementRowIndex = Number(element.getAttribute("name").split("[").pop().split("]")[0]);
    if (elementRowIndex === 0) {
        let description = document.getElementById("description").value;
        element.value = description;
    }
}

function onItemRateInFocus(element) {
    let elementRowIndex = Number(element.getAttribute("name").split("[").pop().split("]")[0]);
    if (elementRowIndex === 0) {
        let visitTotalAmount = parseFloat(document.getElementById("visitTotalAmount").value);
        element.value = visitTotalAmount;
    }
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

function onAddItemBtnOutFocus(element) {
    /*console.log("Came to addItemBtnOutFocus event ... attempting to set focus to addStoreVisitBtn");
    window.setTimeout(function() {
        document.getElementById("addStoreVisitBtn").focus({ focusVisible: true });
    }, 0);*/
}