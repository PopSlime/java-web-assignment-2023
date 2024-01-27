"use strict";

const pageRowCount = 3;
const pageColumnCount = $("#index-list").css("gridTemplateColumns").split(" ").length;
const pageSize = pageRowCount * pageColumnCount;
console.log(`Page Size = ${pageSize}`);

const list = $("#index-list");
let baseUrl = null;
let currentPage = 1;
let totalPages = 1;

function setPage(page) {
    if (page <= 1) page = 1;
    else if (page > totalPages) page = totalPages;
    $("#pager-current-page").val(currentPage = page);
    if (baseUrl) {
        $.getJSON(`${baseUrl}&page=${page}`, function (data) {
            list.empty();
            for (var film of data.records) {
                let imgContainer = $("<div>").append(
                    $("<img>")
                        .attr("src", `/img/film/${film.picture}`)
                );
                if (film.vip) imgContainer.append($("<i>").addClass("badge-vip").text("VIP"));
                list.append(
                    $("<li>").append(
                        $("<a>")
                        .attr("href", `/filmDetail?id=${film.filmId}`)
                        .append(imgContainer)
                        .append($("<span>").text(film.name))
                    )
                );
            }
            $("#pager-total-pages").text(totalPages = data.pages);
        });
    }
}

function updateFilters() {
    const ranking = $("input[name=sort]:checked").val();
    const keyword = $("input[name=keyword]").val();
    let url = `/api/index?pageSize=${pageSize}&ranking=${ranking}&keyword=${keyword}`;
    $("input[name=type0]:checked").each((i, el) => url += `&type0=${$(el).val()}`);
    $("input[name=type1]:checked").each((i, el) => url += `&type1=${$(el).val()}`);
    $("input[name=type2]:checked").each((i, el) => url += `&type2=${$(el).val()}`);
    baseUrl = url;
    setPage(1);
}
updateFilters();

$("input[name=sort]").on("change", () => updateFilters());
$("input[name=type0]").on("change", () => updateFilters());
$("input[name=type1]").on("change", () => updateFilters());
$("input[name=type2]").on("change", () => updateFilters());

$("#index-filter-container").on("submit", function (e) {
    e.preventDefault();
    updateFilters();
});

$("#pager-current-page").on("change", function (e) {
    setPage(parseInt(this.value));
});

$("#pager-first-page").on("click", function (e) {
    setPage(1);
    return false;
});

$("#pager-prev-page").on("click", function (e) {
    setPage(currentPage - 1);
    return false;
});

$("#pager-next-page").on("click", function (e) {
    setPage(currentPage + 1);
    return false;
});

$("#pager-last-page").on("click", function (e) {
    setPage(totalPages);
    return false;
});
