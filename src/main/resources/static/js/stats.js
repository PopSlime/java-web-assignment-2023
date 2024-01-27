'use strict';

function generateChart(ctx, ranking, field, label) {
    $.get(`/api/stats?ranking=${ranking}`, function (data) {
        const labels = [];
        const dataset = [];
        for (var f of data) {
            labels.push(f.name);
            dataset.push(f[field]);
        }
        new Chart(ctx, {
            type: 'bar',
            data: {
                labels: labels,
                datasets: [{
                    label: label,
                    data: dataset
                }]
            }
        });
    });
}

generateChart($('#chart-overview')[0], 'total_heat'  , 'totalHeat'  , $('#msg-stats-hot'   ).text());
generateChart($('#chart-weekly'  )[0], 'weekly_heat' , 'weeklyHeat' , $('#msg-stats-hot'   ).text());
generateChart($('#chart-monthly' )[0], 'monthly_heat', 'monthlyHeat', $('#msg-stats-hot'   ).text());
generateChart($('#chart-review'  )[0], 'rating'      , 'rating'     , $('#msg-stats-rating').text());
