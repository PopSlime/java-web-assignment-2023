"use strict";

const currentDateTime = new Date();
const startDateTime = new Date(currentDateTime);
startDateTime.setDate(currentDateTime.getDate() - 7);
$.getJSON(`/api/index_episode?startDateTime=${startDateTime.toISOString()}`, function (data) {
    const events = [];
    let title = null;

    for (var episode of data) {
        const date = new Date(episode.datetime);
        const picUrl = `/img/bangumi/${episode.picture}`;
        let indexName = episode.indexName;
        if (indexName == null) indexName = episode.index.toString();
        const event = {
            start_date: toTimelineDate(date),
            text: {
                headline: episode.name,
                text: `#${indexName}`,
            },
            media: {
                url: picUrl.replace(".webp", ""),
                thumbnail: picUrl.replace(".webp", ""),
            },
        };
        events.push(event);

        if (title == null && date >= currentDateTime) title = event;
    }

    if (title == null) title = events[events.length - 1];

    const timeline = { events, title };

    window.timeline = new TL.Timeline('timeline-container', timeline, {
        language: "/js/timeline@zh-cn.json", // TODO i18n
    });
});

function toTimelineDate(date) {
    return {
        year: date.getFullYear(),
        month: date.getMonth() + 1,
        day: date.getDate(),
        hour: date.getHours(),
        minute: date.getMinutes(),
    };
}
