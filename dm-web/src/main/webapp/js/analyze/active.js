/**
 * Created by tang on 2016/3/15.
 */
$(function () {
    avalon.ready(function () {
        var activeVm = avalon.define({
            $id: "active",
            yearData: [], //年份数据
            monthData: ['01', '02', '03', '04', '05', '06', '07', '08', '09', '10', '11', '12',], //月份数据
            year: 2015, // 当前选择年值
            month: '01',  // 当前选择月份
            yearMonth: 201501,
            province: {},
            dayActive: 0,
            monthSum: 0,
            yearSum: 0,
            gauge: 0,
            dashChart: echarts.init(document.getElementById('dash')),
            dayActiveChart: echarts.init(document.getElementById('dayActive')),
            monthActiveChart: echarts.init(document.getElementById('monthActive')),
            provinceActiveChart: echarts.init(document.getElementById('provinceActive')),
            pageNo: 1,
            total: 0,
            activeData: [],//活跃数据
            //选择月份
            changeMonth: function () {
                activeVm.yearMonth = '' + activeVm.year + activeVm.month;
                activeVm.pageNo = 1;
                activeVm.getContent();
            },
            //选择年份
            changeYear: function () {
                activeVm.yearMonth = '' + activeVm.year + activeVm.month;
                activeVm.pageNo = 1;
                activeVm.getContent();
            },
            //活跃峰值
            getPeak: function () {
                $.myAjax({
                    url: '/dm/active/peak',
                    type: 'get',
                    data: {
                        'month': activeVm.yearMonth
                    },
                    onSuccess: function (data) {
                        if (data.state == 1) {
                            activeVm.province = data.obj.maxActiveByP;
                            activeVm.dayActive = data.obj.maxActive;
                            activeVm.monthSum = data.obj.sumByMonth;
                            activeVm.yearSum = data.obj.sumByYear;
                            var registerSum = parseInt(data.obj.sumRegister);
                            if(registerSum > 0){
                                activeVm.gauge = parseInt((activeVm.monthSum / registerSum) * 100);
                            }
                            activeVm.drawDash();
                        }
                    }
                });
            },
            //仪表盘
            drawDash: function () {
                var dashOption = {
                    title: {
                        text: '用户活跃百分比',
                        x: 'center'
                    },
                    series: [
                        {
                            name: '业务指标',
                            type: 'gauge',
                            detail: {formatter: '{value}%'},
                            data: [{value: activeVm.gauge, name: '完成率'}]
                        }
                    ]
                };
                activeVm.dashChart.setOption(dashOption);
            },
            //日活跃趋势
            drawDayActive: function () {
                $.myAjax({
                    url: '/dm/active/dayActive',
                    type: 'post',
                    data: {
                        'month': activeVm.yearMonth
                    },
                    onSuccess: function (data) {
                        if (data.state == 1) {
                            var xData = [];
                            $.each(data.obj, function (n, item) {
                                xData.push(n + 1);
                            })
                            var dayActiveOption = {
                                title: {
                                    text: "本月每日用户数活跃值",
                                    x: "center"
                                },
                                xAxis: [
                                    {
                                        type: "category",
                                        name: "日期",
                                        data: xData
                                    }
                                ],
                                yAxis: [
                                    {
                                        type: "log",
                                        name: "活跃用户数"
                                    }
                                ],
                                series: [
                                    {
                                        type: "line",
                                        data: data.obj,
                                        markPoint: {
                                            data: [
                                                {type: 'max'},
                                                {type: 'min'}
                                            ]
                                        }
                                    }
                                ]
                            };
                            activeVm.dayActiveChart.setOption(dayActiveOption);
                        }
                    }
                });
            },
            //月活跃趋势
            drawMonthActive: function () {
                $.myAjax({
                    url: '/dm/active/monthActive',
                    type: 'get',
                    data: {
                        'month': activeVm.yearMonth
                    },
                    onSuccess: function (data) {
                        if (data.state == 1) {
                            var monActiveOption = {
                                title: {
                                    text: "本年每月用户数活跃值",
                                    x: "center"
                                },
                                xAxis: [
                                    {
                                        type: 'category',
                                        name: '月份',
                                        data: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月',]
                                    }
                                ],
                                yAxis: [
                                    {
                                        type: 'value',
                                        name: '活跃用户数'
                                    }
                                ],
                                series: [
                                    {
                                        type: 'bar',
                                        data: data.obj,
                                        markLine: {data: [[{type: 'min'}, {type: 'max'}]]},
                                        markPoint: {data: [{type: 'max'}, {type: 'min'}]}
                                    }
                                ]
                            };
                            activeVm.monthActiveChart.setOption(monActiveOption);
                        }
                    }
                });
            },
            //全国地图
            drawProvinceActive: function () {
                $.myAjax({
                    url: '/dm/active/provinceActive',
                    type: 'get',
                    data: {
                        'month': activeVm.yearMonth
                    },
                    onSuccess: function (data) {
                        if (data.state == 1) {
                            activeVm.provinceActiveChart.setOption([]);
                            var mapOption = {
                                title: {
                                    text: '各省份月活跃情况',
                                    x: 'center'
                                },
                                dataRange: {
                                    min: 0,
                                    max: 200000,
                                    x: 'left',
                                    y: 'bottom',
                                    text: ['高', '低'],           // 文本，默认为数值文本
                                    calculable: true
                                },
                                roamController: {
                                    show: true,
                                    x: 'right',
                                    mapTypeControl: {
                                        'china': true
                                    }
                                },
                                series: [
                                    {
                                        type: 'map',
                                        mapType: 'china',
                                        roam: false,
                                        itemStyle: {
                                            normal: {label: {show: true}},
                                            emphasis: {label: {show: true}}
                                        },
                                        data: data.obj
                                    }
                                ]
                            };
                            activeVm.provinceActiveChart.setOption(mapOption);
                        }
                    }
                });
            },
            //获取列表
            getList: function () {
                $.myAjax({
                    url: '/dm/active/count',
                    type: 'post',
                    data: {
                        'month': activeVm.yearMonth
                    },
                    onSuccess: function (data) {
                        if (data.state == 1) {
                            var total = parseInt(data.obj);
                            total = total % 10 == 0 ? total / 10 : parseInt(total / 10) + 1;
                            activeVm.total = total;
                        }
                    }
                });
                activeVm.queryPage(1);
            },
            //首页，上下页，尾页
            selectPage: function (value) {
                if (activeVm.total <= 0) {
                    return;
                }
                activeVm.pageNo += parseInt(value);
                if (activeVm.pageNo < 1) {
                    activeVm.pageNo = 1;
                }
                if (activeVm.pageNo > activeVm.total) {
                    activeVm.pageNo = activeVm.total;
                }
                activeVm.queryPage(activeVm.pageNo);
            }
            ,
            //查询数据
            queryPage: function (pageNo) {
                $.myAjax({
                    url: '/dm/active/list',
                    type: 'post',
                    data: {
                        'pageNo': activeVm.pageNo,
                        'month': activeVm.yearMonth
                    },
                    onSuccess: function (data) {
                        if (data.state == 1) {
                            $.each(data.obj, function (n, item) {
                                var year = parseInt(item.date / 10000);
                                var month = parseInt(parseInt(item.date) % 10000 / 100);
                                var day = parseInt(item.date) % 10000 % 100;
                                item.date = year + '年' + month + '月' + day + '日';
                            });
                            activeVm.activeData = data.obj;
                        }
                    }
                });
            },
            //获取时间
            getTime: function () {
                $.myAjax({
                    url: '/dm/active/allYear',
                    onSuccess: function (data) {
                        if (data.state == 1) {
                            activeVm.yearData = data.obj;
                            activeVm.year = data.obj[0];
                            activeVm.yearMonth = activeVm.year + '01';
                            activeVm.getContent();
                        }
                    }
                });
            },
            //获取内容
            getContent: function () {
                activeVm.getPeak();
                activeVm.drawDayActive();
                activeVm.drawMonthActive();
                activeVm.drawProvinceActive();
                activeVm.getList();
            },
            //下载
            down: function(){
                window.open('/dm/active/down?month='+activeVm.yearMonth);
            },
            //初始化
            init: function () {
                activeVm.getTime();
            }
        })
        avalon.scan($('#active')[0], activeVm);
        activeVm.init();
    })

});