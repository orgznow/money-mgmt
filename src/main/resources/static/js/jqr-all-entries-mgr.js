$(document).ready(function(){
    setFocusToAppropriateElement();
});

function setFocusToAppropriateElement() {
    let visitTotalAmt = $("#visitTotalAmount").val();
    if (visitTotalAmt === "") {
         $("#visitTotalAmount").focus();
    } else {
        $(".journalEntriesRow:last > td:nth-of-type(2) > input").focus();
    }
}

function onItemFinalAmountOutFocus(element) {
      var grandTotal = 0.0;
      $(".journalEntriesFinalAmount").each(function(){
        let rowFinalAmount = parseFloat($(this).val());
        grandTotal = grandTotal + rowFinalAmount;
      });
      $("#grandTotal").text(grandTotal);
}
