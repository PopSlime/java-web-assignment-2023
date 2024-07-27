'use strict';

$(document).ready(() => {
    try {
        document.querySelector(":has(test)");
    }
    catch (DOMException) {
        window.alert($("#msg-browser-incompatible").text());
    }
});
