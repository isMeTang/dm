/**
 * Created by tang on 2016/3/15.
 */
$(function () {
    avalon.ready(function () {
        var registerVm = avalon.define({
            $id: "register",
            yearData: [], //年份数据
            monthData: ['01', '02', '03', '04', '05', '06', '07', '08', '09', '10', '11', '12'], //月份数据
            year: 2015, // 当前选择年值
            month: '01',  // 当前选择月份
            yearMonth: 201501,
            maxForDay: 0,
            sumForMonth: 0,
            sumForYear: 0,
            grilSum: 0,
            manSum: 0,
            scale: 0, //比例
            ageList: [1, 1, 1, 1],//年龄分布
            pageNo: 1,
            total: 0,
            registerData: [],//注册数据
            //选择月份
            changeMonth: function () {
                registerVm.yearMonth = '' + registerVm.year + registerVm.month;
                registerVm.pageNo = 1;
                registerVm.getContent();
            },
            //选择年份
            changeYear: function () {
                registerVm.yearMonth = '' + registerVm.year + registerVm.month;
                registerVm.pageNo = 1;
                registerVm.getContent();
            },
            //注册峰值
            getPeak: function () {
                $.myAjax({
                    url: '/dm/register/peak',
                    type: 'get',
                    data: {
                        'month': registerVm.yearMonth
                    },
                    onSuccess: function (data) {
                        if (data.state == 1) {
                            registerVm.maxForDay = data.obj.maxForDay;
                            registerVm.sumForMonth = data.obj.sumForMonth;
                            registerVm.sumForYear = data.obj.sumForYear;
                            registerVm.manSum = data.obj.manSum;
                            registerVm.grilSum = data.obj.grilSum;
                            registerVm.ageList = data.obj.ageList == "" ? [1, 1, 1, 1] : data.obj.ageList;
                            if (registerVm.manSum == 0 && registerVm.grilSum == 0) {
                                registerVm.manSum = 1;
                                registerVm.grilSum = 1;
                            }
                            registerVm.scale = (registerVm.manSum / (registerVm.manSum + registerVm.grilSum)).toFixed(2) * 100;
                            registerVm.drawPid();
                            registerVm.drawPyramid();
                        }
                    }
                });
            },
            drawPid: function () {
                var pidCharts = echarts.init(document.getElementById('pie'));
                var pidOption = {
                    title: {
                        text: '注册男女比例',
                        x: 'center'
                    },
                    series: [
                        {
                            name: '访问来源',
                            type: 'pie',
                            radius: '55%',
                            center: ['50%', '50%'],
                            data: [
                                {value: registerVm.manSum, name: '男' + registerVm.scale + '%'},
                                {value: registerVm.grilSum, name: '女' + parseInt(100 - registerVm.scale) + '%'}
                            ]
                        }
                    ]
                };
                pidCharts.setOption(pidOption);
            },
            drawPyramid: function () {
                var option = {
                    title: {
                        text: '注册年龄分布',
                        x: 'center'
                    },
                    noDataLoadingOption: {text: '暂无数据'},
                    series: [
                        {
                            name: '漏斗图',
                            type: 'funnel',
                            width: '40%',
                            data: [
                                {value: registerVm.ageList[0] * 10, name: '18岁以下'},
                                {value: registerVm.ageList[1] * 10, name: '19-30岁'},
                                {value: registerVm.ageList[2] * 10, name: '31-50岁'},
                                {value: registerVm.ageList[3] * 10, name: '50岁以上'}
                            ]
                        }
                    ]
                };
                var pyramidCharts = echarts.init(document.getElementById('pyramid'));
                pyramidCharts.setOption(option);
            },
            //日注册趋势
            drawDayRegister: function () {
                $.myAjax({
                    url: '/dm/register/dayRegister',
                    type: 'post',
                    data: {
                        'month': registerVm.yearMonth
                    },
                    onSuccess: function (data) {
                        if (data.state == 1) {
                            var xData = [];
                            $.each(data.obj, function (n, item) {
                                xData.push(n + 1);
                            })
                            var dayRegisterOption = {
                                title: {
                                    text: "本月每日注册用户数",
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
                                        name: "注册用户数"
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
                            var dayRegisterChart = echarts.init(document.getElementById('dayRegister'));
                            dayRegisterChart.setOption(dayRegisterOption);
                        }
                    }
                });
            },
            //月注册趋势
            drawMonthRegister: function () {
                $.myAjax({
                    url: '/dm/register/monthRegister',
                    type: 'get',
                    data: {
                        'month': registerVm.yearMonth
                    },
                    onSuccess: function (data) {
                        if (data.state == 1) {
                            var monRegisterOption = {
                                title: {
                                    text: "本年每月用户注册数",
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
                                        name: '注册用户数'
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
                            var monthRegisterChart = echarts.init(document.getElementById('monthRegister'));
                            monthRegisterChart.setOption(monRegisterOption);
                        }
                    }
                });
            },
            //全国地图
            drawProvinceRegister: function () {
                $.myAjax({
                    url: '/dm/register/provinceRegister',
                    data: {
                        'month': registerVm.yearMonth
                    },
                    onSuccess: function (data) {
                        if (data.state == 1) {
                            var provinceRegisterChart = echarts.init(document.getElementById('provinceRegister'));
                            provinceRegisterChart.setOption([]);
                            var mapOption = {
                                title: {
                                    text: '各省份月注册情况',
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
                            provinceRegisterChart.setOption(mapOption);
                        }
                    }
                });
            },
            //获取列表
            getList: function () {
                $.myAjax({
                    url: '/dm/register/count',
                    type: 'post',
                    data: {
                        'month': registerVm.yearMonth
                    },
                    onSuccess: function (data) {
                        if (data.state == 1) {
                            var total = parseInt(data.obj);
                            total = total % 10 == 0 ? total / 10 : parseInt(total / 10) + 1;
                            registerVm.total = total;
                        }
                    }
                });
                registerVm.queryPage(1);
            },
            //首页，上下页，尾页
            selectPage: function (value) {
                if (registerVm.total <= 0) {
                    return;
                }
                registerVm.pageNo += parseInt(value);
                if (registerVm.pageNo < 1) {
                    registerVm.pageNo = 1;
                }
                if (registerVm.pageNo > registerVm.total) {
                    registerVm.pageNo = registerVm.total;
                }
                registerVm.queryPage(registerVm.pageNo);
            }
            ,
            //查询数据
            queryPage: function () {
                $.myAjax({
                    url: '/dm/register/list',
                    type: 'post',
                    data: {
                        'pageNo': registerVm.pageNo,
                        'month': registerVm.yearMonth
                    },
                    onSuccess: function (data) {
                        if (data.state == 1) {
                            $.each(data.obj, function (n, item) {
                                var year = parseInt(item.date / 10000);
                                var month = parseInt(parseInt(item.date) % 10000 / 100);
                                var day = parseInt(item.date) % 10000 % 100;
                                item.date = year + '年' + month + '月' + day + '日';
                                switch (item.typeId) {
                                    case 100:
                                        item.typeId = '本站注册';
                                        break;
                                    case 101:
                                        item.typeId = 'QQ注册';
                                        break;
                                    case 102:
                                        item.typeId = '邮箱注册';
                                        break;
                                    default:
                                        item.tpeId = '其他';
                                        break;
                                }
                                item.sex = item.sex == 1 ? '男' : '女';
                            });
                            registerVm.registerData = data.obj;
                        }
                    }
                });
            },
            //获取时间
            getTime: function () {
                $.myAjax({
                    url: '/dm/register/allYear',
                    onSuccess: function (data) {
                        if (data.state == 1) {
                            registerVm.yearData = data.obj;
                            registerVm.year = data.obj[0];
                            registerVm.yearMonth = registerVm.year + '01';
                            registerVm.getContent();
                        }
                    }
                });
            },
            //获取内容
            getContent: function () {
                registerVm.getPeak();
                registerVm.drawDayRegister();
                registerVm.drawMonthRegister();
                registerVm.drawProvinceRegister();
                registerVm.getList();
            },
            //下载
            down: function(){
                window.open('/dm/register/down?month='+registerVm.yearMonth);
            },
            //初始化
            init: function () {
                registerVm.getTime();
            }
        })
        avalon.scan($('#register')[0], registerVm);
        registerVm.init();
    })

});