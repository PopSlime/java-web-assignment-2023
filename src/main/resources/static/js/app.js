'use strict';

$(document).ready(() => {
    try {
        document.querySelector(":has(test)");
    }
    catch (DOMException) {
        window.alert($("#msg-browser-incompatible").text());
    }

    $("#account-subscribe").on("click", function (e) {
        e.preventDefault();
        if (window.confirm($("#msg-subscribe-confirm").text())) {
            $.post("/api/subscribe", () => location.reload());
        }
    });
});
