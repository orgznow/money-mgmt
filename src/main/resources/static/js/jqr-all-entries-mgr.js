$(document).ready(function(){
    let visitTotalAmt = $("#visitTotalAmount").val();
    if (visitTotalAmt === "") {
         $("#visitTotalAmount").focus();
    } else {
        $(".journalEntriesRow:last > td:nth-of-type(2) > input").focus();
    }
});
