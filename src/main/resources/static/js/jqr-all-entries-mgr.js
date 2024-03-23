$(document).ready(function(){
    setFocusToAppropriateElement();
    setOnItemDescriptionsInFocusHandler();
    setOnItemRateAmountsInFocusHandler();
    setOnItemBaseAmountsInFocusHandler();
    setOnItemTaxAmountsInFocusHandler();
    setOnItemFinalAmountsInFocusHandler();
    setOnItemFinalAmountsLoseFocusHandler();
//    setOnCategoryLoseFocusHandler();
});

function setFocusToAppropriateElement() {
    let visitTotalAmt = $("#visitTotalAmount").val();
    if (visitTotalAmt === "") {
         $("#visitTotalAmount").focus();
    } else {
        $(".journalEntriesRows:last > td:nth-of-type(2) > input").focus();
    }
}

function setOnItemDescriptionsInFocusHandler() {
    $(".journalEntriesDescription").focus(onItemDescriptionInFocus);
}

function onItemDescriptionInFocus() {
    //TODO: replace with count of rows being 1
    let elementRowIndex = Number($(this).attr("name").split("[").pop().split("]")[0]);
    if (elementRowIndex === 0) {
        let description = $("#visitDescription").val();
        $(this).val(description);
    }
}

function setOnItemRateAmountsInFocusHandler() {
    $(".journalEntriesRate").focus(onItemRateAmountInFocus);
}

function onItemRateAmountInFocus() {
    //TODO: replace with count of rows being 1
    let elementRowIndex = Number($(this).attr("name").split("[").pop().split("]")[0]);
    if (elementRowIndex === 0) {
        let visitTotalAmount = parseFloat($("#visitTotalAmount").val());
        $(this).val(visitTotalAmount);
    }
}

function setOnItemBaseAmountsInFocusHandler() {
    $(".journalEntriesBaseAmount").focus(onItemBaseAmountInFocus);
}

function onItemBaseAmountInFocus() {
    let rowRateAmount = parseFloat($(this).closest("tr").find(".journalEntriesRate").val());
    let rowQuantityAmount = parseFloat($(this).closest("tr").find(".journalEntriesQuantity").val());
    $(this).val(rowRateAmount*rowQuantityAmount);
}

function setOnItemTaxAmountsInFocusHandler() {
    $(".journalEntriesTaxAmount").focus(onItemTaxAmountInFocus);
}

function onItemTaxAmountInFocus() {
    let rowBaseAmount = parseFloat($(this).closest("tr").find(".journalEntriesBaseAmount").val());
    let rowDiscountAmount = parseFloat($(this).closest("tr").find(".journalEntriesDiscountAmount").val());;
    let rowWithDiscountAmount = isNaN(rowDiscountAmount) ? rowBaseAmount : rowBaseAmount - rowDiscountAmount;
    let rowIsTaxableChecked = $(this).closest("tr").find(".journalEntriesIsTaxable").is(":checked");
    let taxAmount = (rowIsTaxableChecked) ? rowWithDiscountAmount * 0.075 : 0.0;
    if (taxAmount != 0.0) {
        $(this).val(taxAmount.toFixed(2));
    } else {
        $(this).val("");
    }
}

function setOnItemFinalAmountsInFocusHandler() {
    $(".journalEntriesFinalAmount").focus(onItemFinalAmountInFocus);
}

function onItemFinalAmountInFocus() {
    let rowBaseAmount = parseFloat($(this).closest("tr").find(".journalEntriesBaseAmount").val());
    let rowDiscountAmount = parseFloat($(this).closest("tr").find(".journalEntriesDiscountAmount").val());;
    let rowWithDiscountAmount = isNaN(rowDiscountAmount) ? rowBaseAmount : rowBaseAmount - rowDiscountAmount;
    let rowIsTaxableChecked = $(this).closest("tr").find(".journalEntriesIsTaxable").is(":checked");
    let taxAmount = (rowIsTaxableChecked) ? rowWithDiscountAmount * 0.075 : 0.0;
    let rowTipAmount = parseFloat($(this).closest("tr").find(".journalEntriesTipAmount").val());
    let finalAmount = rowWithDiscountAmount + (isNaN(taxAmount) ? 0.0 : taxAmount) + (isNaN(rowTipAmount) ? 0.0 : rowTipAmount);
    $(this).val(finalAmount);
}

//TODO: Move functionality to calculate taxAmount to a common method

function setOnItemFinalAmountsLoseFocusHandler() {
    $(".journalEntriesFinalAmount").focusout(onItemFinalAmountLoseFocus);
}

function onItemFinalAmountLoseFocus() {
      var grandTotal = 0.0;
      $(".journalEntriesFinalAmount").each(function(){
        let rowFinalAmount = parseFloat($(this).val());
        grandTotal = grandTotal + rowFinalAmount;
      });
      $("#visitGrandTotal").text(grandTotal);
      let visitTotalAmount = parseFloat($("#visitTotalAmount").val());
      if (visitTotalAmount != grandTotal) {
          $("#visitGrandTotal").css("border", "3px solid red");
      } else {
          $("#visitGrandTotal").css("border", "3px solid green");
      }

}

//TODO: Move this functionality from javascript to jquery
//function setOnCategoryLoseFocusHandler() {
//    $(".journalEntriesSpendCategory").focusout(onCategoryLoseFocus);
//}
//
//function onCategoryLoseFocus() {
//    window.setTimeout(function() {
//        $("#addItemBtn").focus({ focusVisible: true });
//    }, 0);
//}
