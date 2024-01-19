"use strict";

const pageRowCount = 3;
const pageColumnCount = $("#index-list").css("gridTemplateColumns").split(" ").length;
const pageSize = pageRowCount * pageColumnCount;
console.log(`Page Size = ${pageSize}`);

const list = $("#index-list");

$("#index-filter-container").on("submit", function (e) {
    e.preventDefault();
    const ranking = $("input[name=sort]:checked").val();
    const keyword = $("input[name=keyword]").val();
    let url = `/api/index?pageSize=${pageSize}&ranking=${ranking}&keyword=${keyword}`;
    $("input[name=type0]:checked").each((i, el) => url += `&type0=${$(el).val()}`);
    $("input[name=type1]:checked").each((i, el) => url += `&type1=${$(el).val()}`);
    $("input[name=type2]:checked").each((i, el) => url += `&type2=${$(el).val()}`);
    $.getJSON(url, function (data) {
        list.empty();
        for (var film of data.records) {
            list.append(
                $("<li>").append(
                    $("<a>").attr(
                        "href", `/filmDetail?id=${film.filmId}`
                    ).append(
                        $("<div>").append(
                            $("<img>").attr("src", `/img/film/${film.picture}`)
                        )
                    ).append(
                        $("<span>").text(film.name)
                    )
                )
            );
        }
    });
});
