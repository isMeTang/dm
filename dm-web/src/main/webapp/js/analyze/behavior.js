/**
 * Created by tang on 2016/3/15.
 */
$(function () {
    avalon.ready(function () {
        var behaviorVm = avalon.define({
            $id: "behavior",
            yearData: [], //年份数据
            monthData: ['01', '02', '03', '04', '05', '06', '07', '08', '09', '10', '11', '12',], //月份数据
            year: 2015, // 当前选择年值
            month: '01',  // 当前选择月份
            yearMonth: 201501,
            visitSumForMonth: 0,
            timeSumForMonth: 0,
            timeSumByOther: 0,
            timeSumByUser: 0,
            intoList: [],
            /*     ,

             provincebehaviorChart: echarts.init(document.getElementById('provincebehavior')),*/
            pageNo: 1,
            total: 0,
            behaviorData: [],//活跃数据
            //选择月份
            changeMonth: function () {
                behaviorVm.yearMonth = '' + behaviorVm.year + behaviorVm.month;
                behaviorVm.pageNo = 1;
                behaviorVm.getContent();
            },
            //选择年份
            changeYear: function () {
                behaviorVm.yearMonth = '' + behaviorVm.year + behaviorVm.month;
                behaviorVm.pageNo = 1;
                behaviorVm.getContent();
            },
            //活跃峰值
            getPeak: function () {
                $.myAjax({
                    url: '/dm/behavior/peak',
                    type: 'get',
                    data: {
                        'month': behaviorVm.yearMonth
                    },
                    onSuccess: function (data) {
                        if (data.state == 1) {
                            behaviorVm.visitSumForMonth = data.obj.visitSumForMonth;
                            behaviorVm.timeSumForMonth = data.obj.timeSumForMonth.toFixed(2);
                            behaviorVm.timeSumByOther = data.obj.timeSumByOther.toFixed(2);
                            behaviorVm.timeSumByUser = data.obj.timeSumByUser.toFixed(2);
                            behaviorVm.intoList = data.obj.intoList;
                            behaviorVm.drawDash();
                        }
                    }
                });
            },
            //仪表盘
            drawDash: function () {
                var option = {
                    title: {
                        text: '访问方式比较',
                        x: 'center'
                    },
                    series: [
                        {
                            type: 'pie',
                            radius: [30, 90],
                            roseType: 'area',
                            data: behaviorVm.intoList == '' ? [] : [
                                {value: behaviorVm.intoList[0], name: '地址栏跳转' + behaviorVm.intoList[0]},
                                {value: behaviorVm.intoList[1], name: '搜索引擎进入' + behaviorVm.intoList[1]},
                                {value: behaviorVm.intoList[2], name: '广告进入' + behaviorVm.intoList[2]},
                                {value: behaviorVm.intoList[3], name: '客户端进入' + behaviorVm.intoList[3]},
                                {value: behaviorVm.intoList[4], name: '其他方式' + behaviorVm.intoList[4]}
                            ]
                        }
                    ]
                };
                var dashChart = echarts.init(document.getElementById('dash'));
                dashChart.setOption(option);
            },
            //日活跃趋势
            drawDaybehavior: function () {
                $.myAjax({
                    url: '/dm/behavior/dayVisit',
                    type: 'post',
                    data: {
                        'month': behaviorVm.yearMonth
                    },
                    onSuccess: function (data) {
                        if (data.state == 1) {
                            var xData = [];
                            $.each(data.obj, function (n, item) {
                                xData.push(n + 1);
                            });
                            var daybehaviorOption = {
                                title: {
                                    text: "本月每日被访问时长",
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
                                        name: "访问时长/小时"
                                    }
                                ],
                                series: [
                                    {
                                        type: "bar",
                                        data: data.obj
                                    }
                                ]
                            };
                            var daybehaviorChart = echarts.init(document.getElementById('daybehavior'));
                            daybehaviorChart.setOption(daybehaviorOption);
                        }
                    }
                });
            },
            //月活跃趋势
            drawTimeVisit: function () {
                $.myAjax({
                    url: '/dm/behavior/timeVisit',
                    data: {
                        'month': behaviorVm.yearMonth
                    },
                    onSuccess: function (data) {
                        if (data.state == 1) {
                            var option = {
                                title: {
                                    text: '本月各时间段用户访问时长',
                                    x: 'center'
                                },
                                xAxis: [
                                    {
                                        name: '时间段',
                                        type: 'category',
                                        splitLine: {show: false},
                                        data: ['总时长', '0-8点', '8-12点', '12-19点', '19-24点']
                                    }
                                ],
                                yAxis: [
                                    {
                                        type: 'value',
                                        name: '小时'
                                    }
                                ],
                                series: [
                                    {
                                        name: '辅助',
                                        type: 'bar',
                                        stack: '总量',
                                        itemStyle: {
                                            normal: {
                                                barBorderColor: 'rgba(0,0,0,0)',
                                                color: 'rgba(0,0,0,0)'
                                            },
                                            emphasis: {
                                                barBorderColor: 'rgba(0,0,0,0)',
                                                color: 'rgba(0,0,0,0)'
                                            }
                                        },
                                        data: [0, data.obj[0]-data.obj[1], data.obj[0]-data.obj[1]-data.obj[2], data.obj[0]-data.obj[1]-data.obj[2]-data.obj[3], 0]
                                    },
                                    {
                                        type: 'bar',
                                        stack: '总量',
                                        itemStyle: {normal: {label: {show: true, position: 'inside'}}},
                                        data: data.obj
                                    }
                                ]
                            };
                            var timeVisitChart = echarts.init(document.getElementById('timeVisit'));
                            timeVisitChart.setOption(option);
                        }
                    }
                });
            },
            //系统折线
            drawOsLine: function () {
                $.myAjax({
                    url: '/dm/behavior/osLine',
                    data: {
                        'month': behaviorVm.yearMonth
                    },
                    onSuccess: function (data) {
                        if (data.state == 1) {
                            var xData = [];
                            $.each(data.obj.other, function (n, item) {
                                xData.push(n + 1);
                            });
                            var option = {
                                title : {
                                    text: '用户访问系统类型',
                                    x: 'left'
                                },
                                legend: {
                                    data:['ios','android','windows','其他']
                                },
                                xAxis : [
                                    {
                                        type : 'category',
                                        boundaryGap : false,
                                        data : xData
                                    }
                                ],
                                yAxis : [
                                    {
                                        type : 'value'
                                    }
                                ],
                                series : [
                                    {
                                        name:'ios',
                                        type:'line',
                                        smooth:true,
                                        itemStyle: {normal: {areaStyle: {type: 'default'}}},
                                        data:data.obj.ios
                                    },
                                    {
                                        name:'android',
                                        type:'line',
                                        smooth:true,
                                        itemStyle: {normal: {areaStyle: {type: 'default'}}},
                                        data:data.obj.android
                                    },
                                    {
                                        name:'windows',
                                        type:'line',
                                        smooth:true,
                                        itemStyle: {normal: {areaStyle: {type: 'default'}}},
                                        data:data.obj.windows
                                    },
                                    {
                                        name:'其他',
                                        type:'line',
                                        smooth:true,
                                        itemStyle: {normal: {areaStyle: {type: 'default'}}},
                                        data:data.obj.other
                                    }
                                ]
                            };
                            var osLineChart = echarts.init(document.getElementById('osLine'));
                            osLineChart.setOption(option);
                        }
                    }
                });
            },
            //获取列表
            getList: function () {
                $.myAjax({
                    url: '/dm/behavior/count',
                    type: 'post',
                    data: {
                        'month': behaviorVm.yearMonth
                    },
                    onSuccess: function (data) {
                        if (data.state == 1) {
                            var total = parseInt(data.obj);
                            total = total % 10 == 0 ? total / 10 : parseInt(total / 10) + 1;
                            behaviorVm.total = total;
                        }
                    }
                });
                behaviorVm.queryPage(1);
            },
            //首页，上下页，尾页
            selectPage: function (value) {
                if (behaviorVm.total <= 0) {
                    return;
                }
                behaviorVm.pageNo += parseInt(value);
                if (behaviorVm.pageNo < 1) {
                    behaviorVm.pageNo = 1;
                }
                if (behaviorVm.pageNo > behaviorVm.total) {
                    behaviorVm.pageNo = behaviorVm.total;
                }
                behaviorVm.queryPage(behaviorVm.pageNo);
            }
            ,
            //查询数据
            queryPage: function (pageNo) {
                $.myAjax({
                    url: '/dm/behavior/list',
                    type: 'post',
                    data: {
                        'pageNo': behaviorVm.pageNo,
                        'month': behaviorVm.yearMonth
                    },
                    onSuccess: function (data) {
                        if (data.state == 1) {
                            $.each(data.obj, function (n, item) {
                                var year = parseInt(item.date / 10000);
                                var month = parseInt(parseInt(item.date) % 10000 / 100);
                                var day = parseInt(item.date) % 10000 % 100;
                                item.date = year + '年' + month + '月' + day + '日';
                                item.isUser = item.isUser == 1 ? '注册用户' : '访客';
                                switch (item.intoType) {
                                    case 1:
                                        item.intoType = '地址栏跳转';
                                        break;
                                    case 2:
                                        item.intoType = '搜索引擎进入';
                                        break;
                                    case 3:
                                        item.intoType = '广告进入';
                                        break;
                                    case 4:
                                        item.intoType = '客户端进入';
                                        break;
                                    default:
                                        item.intoType = '其他方式';
                                        break;
                                }
                            });
                            behaviorVm.behaviorData = data.obj;
                        }
                    }
                });
            },
            //获取时间
            getTime: function () {
                $.myAjax({
                    url: '/dm/behavior/allYear',
                    onSuccess: function (data) {
                        if (data.state == 1) {
                            behaviorVm.yearData = data.obj;
                            behaviorVm.year = data.obj[0];
                            behaviorVm.yearMonth = behaviorVm.year + '01';
                            behaviorVm.getContent();
                        }
                    }
                });
            },
            //获取内容
            getContent: function () {
                behaviorVm.getPeak();
                behaviorVm.drawDaybehavior();
                behaviorVm.drawTimeVisit();
                behaviorVm.drawOsLine();
                behaviorVm.getList();
            },
            //下载
            down: function(){
                window.open('/dm/behavior/down?month='+behaviorVm.yearMonth);
            },
            //初始化
            init: function () {
                behaviorVm.getTime();
            }
        })
        avalon.scan($('#behavior')[0], behaviorVm);
        behaviorVm.init();
    })

});