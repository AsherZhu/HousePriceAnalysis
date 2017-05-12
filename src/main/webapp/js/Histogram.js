var myChart = echarts.init(document.getElementById('main'));

option = {
    title : {
        text: '北京二手房总价统计',
        subtext: '数据来源链家网'
    },
    tooltip: {
        trigger: 'axis',
        axisPointer: {            // 坐标轴指示器，坐标轴触发有效
            type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
        }
    },
    legend: {
        data:['最低价','平均价','最高价']
    },
    grid: {
        left: '3%',
        right: '4%',
        bottom: '3%',
        containLabel: true
    },
    xAxis: [
        {
            type: 'category',
            data: ['东城', '丰台', '亦庄开发区', '大兴', '密云', '平谷', '延庆', '房山', '昌平', '朝阳', '海淀', '燕郊', '石景山', '西城', '通州', '门头沟', '顺义']
        }
    ],
    yAxis: [
        {
            name: '总价',
            type: 'value',
            axisLabel: {
                formatter: '{value} 万'
            }
        }
    ],
    series: [
        {
            name: '最低价',
            type: 'bar',
            data: [330, 230, 127, 210, 252, 239, 255, 135, 160, 255, 300, 89, 203, 350, 230, 195, 178]
        },
        {
            name: '平均价',
            type: 'bar',
            data: [966,616,684,543,799,533,383,395,566,825,888,285,542,948,563,452,602]

        },

        {
            name: '最高价',
            type: 'bar',
            stack: '搜索引擎',
            data: [5600, 3500, 2200, 3780, 1500, 1500, 510, 1050, 8000, 8000, 7000, 3660, 7300, 5600, 3100, 3200, 4200]
        },
    ]
};

myChart.setOption(option);
