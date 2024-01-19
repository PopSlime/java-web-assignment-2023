"use strict";

$("#login-form").on("submit", function (e) {
    if ($("#password").val() != $("#confirm-password").val()) {
        e.preventDefault();
        alert($("#msg-mismatched-password").text());
    }
});
