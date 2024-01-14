"use strict";

const pageRowCount = 3;
const pageColumnCount = $("#index-list").css("gridTemplateColumns").split(" ").length;
const pageSize = pageRowCount * pageColumnCount;
console.log(`Page Size = ${pageSize}`);
