"use strict";

function createTimeline(url, container, currentDateTime, startDateTime, scaleFactor) {
    $.getJSON(url, function (data) {
        const events = [];
        let title = null;

        for (var episode of data) {
            if (episode.datetime == null) continue;
            const date = new Date(episode.datetime);
            const picUrl = `/img/bangumi/${episode.picture}`;
            let endIndex = episode.endIndex;
            if (endIndex == null) endIndex = episode.beginIndex;
            for (let index = episode.beginIndex; index <= endIndex; index++, date.setSeconds(date.getSeconds() + episode.period)) {
                if (startDateTime != null && date < startDateTime) continue;

                let indexName = episode.indexName;
                if (indexName == null) indexName = (index - episode.indexOffset).toString();
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
        }

        if (title == null) title = events[events.length - 1];

        const timeline = { events, title };

        window.timeline = new TL.Timeline(container, timeline, {
            language: "/js/timeline@zh-cn.json", // TODO i18n
            scale_factor: scaleFactor,
        });
    });
}

function toTimelineDate(date) {
    return {
        year: date.getFullYear(),
        month: date.getMonth() + 1,
        day: date.getDate(),
        hour: date.getHours(),
        minute: date.getMinutes(),
    };
}
