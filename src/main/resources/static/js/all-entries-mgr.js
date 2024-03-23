function onCategoryLoseFocus(element) {
    window.setTimeout(function() {
        document.getElementById("addItemBtn").focus({ focusVisible: true });
    }, 0);
}

//function onAddItemBtnOutFocus(element) {
//    /*console.log("Came to addItemBtnOutFocus event ... attempting to set focus to addStoreVisitBtn");
//    window.setTimeout(function() {
//        document.getElementById("addStoreVisitBtn").focus({ focusVisible: true });
//    }, 0);*/
//}