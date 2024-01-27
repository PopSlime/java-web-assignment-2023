'use strict';

$(document).ready(() => {
    $("#account-subscribe").on("click", function (e) {
        e.preventDefault();
        if (window.confirm($("#msg-subscribe-confirm").text())) {
            $.post("/api/subscribe", () => location.reload());
        }
    });
});
