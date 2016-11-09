/**
 * Created by tang on 2016/3/15.
 */
$(function () {
    avalon.ready(function () {
        var flowVm = avalon.define({
            $id: "flow",
            yearData: [], //年份数据
            monthData: ['01', '02', '03', '04', '05', '06', '07', '08', '09', '10', '11', '12',], //月份数据
            year: 2015, // 当前选择年值
            month: '01',  // 当前选择月份
            yearMonth: 201501,
            page1: 0,
            page2: 0,
            page3: 0,
            page4: 0,
            pageNo: 1,
            total: 0,
            flowData: [],//流量汇总数据
            //选择月份
            changeMonth: function () {
                flowVm.yearMonth = '' + flowVm.year + flowVm.month;
                flowVm.pageNo = 1;
                flowVm.getContent();
            },
            //选择年份
            changeYear: function () {
                flowVm.yearMonth = '' + flowVm.year + flowVm.month;
                flowVm.pageNo = 1;
                flowVm.getContent();
            },
            //数据处理
            uintTransition: function(data){
                return parseInt(data/1024/1024) > 1000 ? (data/1024/1024/1024).toFixed(2) + 'GB' : (data/1024/1024).toFixed(2) + "MB";
            },
            //活跃峰值
            getPeak: function () {
                $.myAjax({
                    url: '/dm/flow/peak',
                    type: 'get',
                    data: {
                        'month': flowVm.yearMonth
                    },
                    onSuccess: function (data) {
                        if (data.state == 1) {
                            flowVm.page1 = flowVm.uintTransition(data.obj.page1);
                            flowVm.page2 = flowVm.uintTransition(data.obj.page2);
                            flowVm.page3 = flowVm.uintTransition(data.obj.page3);
                            flowVm.page4 = flowVm.uintTransition(data.obj.page4);
                            flowVm.drawDash();
                        }
                    }
                });
            },
            //仪表盘
            drawDash: function () {
                var option = {
                    legend: {
                        orient : 'vertical',
                        x : 'left',
                        data:['页面A','页面B','页面C','页面D']
                    },
                    toolbox: {
                        show : false,
                        feature : {
                            mark : {show: true},
                            dataView : {show: true, readOnly: false},
                            magicType : {
                                show: true,
                                type: ['pie', 'funnel'],
                                option: {
                                    funnel: {
                                        x: '25%',
                                        width: '50%',
                                        funnelAlign: 'center',
                                        max: 1548
                                    }
                                }
                            },
                            restore : {show: true},
                            saveAsImage : {show: true}
                        }
                    },
                    calculable : true,
                    series : [
                        {
                            type:'pie',
                            radius : ['50%', '70%'],
                            itemStyle : {
                                normal : {
                                    label : {
                                        show : false
                                    },
                                    labelLine : {
                                        show : false
                                    }
                                },
                                emphasis : {
                                    label : {
                                        show : true,
                                        position : 'center',
                                        textStyle : {
                                            fontSize : '30',
                                            fontWeight : 'bold'
                                        }
                                    }
                                }
                            },
                            data:[
                                {value:parseInt(flowVm.page1), name:'页面A'},
                                {value:parseInt(flowVm.page2), name:'页面B'},
                                {value:parseInt(flowVm.page3), name:'页面C'},
                                {value:parseInt(flowVm.page4), name:'页面D'}
                            ]
                        }
                    ]
                };
                var dashChart = echarts.init(document.getElementById('dash'));
                dashChart.setOption(option);
            },
            //日活跃趋势
            drawDayflow: function () {
                $.myAjax({
                    url: '/dm/flow/dayflow',
                    type: 'post',
                    data: {
                        'month': flowVm.yearMonth
                    },
                    onSuccess: function (data) {
                        if (data.state == 1) {
                            var xData = [];
                            $.each(data.obj.page1, function (n, item) {
                                xData.push(n + 1);
                                item.value = (item.value/1024/1024).toFixed(2);
                            });
                            $.each(data.obj.page2, function (n, item) {
                                item.value = (item.value/1024/1024).toFixed(2);
                            });
                            $.each(data.obj.page3, function (n, item) {
                                item.value = (item.value/1024/1024).toFixed(2);
                            });
                            $.each(data.obj.page4, function (n, item) {
                                item.value = (item.value/1024/1024).toFixed(2);
                            });
                            var option = {
                                title : {
                                    text: '本月每天各页面访问流量汇总',
                                    x: 'left'
                                },
                                legend: {
                                    data:['页面A','页面B','页面C','页面D']
                                },
                                xAxis : [
                                    {
                                        name: '日期',
                                        type : 'category',
                                        boundaryGap : false,
                                        data : xData
                                    }
                                ],
                                yAxis : [
                                    {
                                        name: '流量值/MB',
                                        type : 'value'
                                    }
                                ],
                                series : [
                                    {
                                        name:'页面A',
                                        type:'line',
                                        smooth:true,
                                        itemStyle: {normal: {areaStyle: {type: 'default'}}},
                                        data:data.obj.page1
                                    },
                                    {
                                        name:'页面B',
                                        type:'line',
                                        smooth:true,
                                        itemStyle: {normal: {areaStyle: {type: 'default'}}},
                                        data:data.obj.page2
                                    },
                                    {
                                        name:'页面C',
                                        type:'line',
                                        smooth:true,
                                        itemStyle: {normal: {areaStyle: {type: 'default'}}},
                                        data:data.obj.page3
                                    },
                                    {
                                        name:'页面D',
                                        type:'line',
                                        smooth:true,
                                        itemStyle: {normal: {areaStyle: {type: 'default'}}},
                                        data:data.obj.page4
                                    }
                                ]
                            };
                            var pageChart = echarts.init(document.getElementById('dayflow'));
                            pageChart.setOption(option);
                        }
                    }
                });
            },
            //全国地图
            drawProvinceflow: function () {
                $.myAjax({
                    url: '/dm/flow/provinceFlow',
                    type: 'get',
                    data: {
                        'month': flowVm.yearMonth
                    },
                    onSuccess: function (data) {
                        if (data.state == 1) {
                            $.each(data.obj,function(n,item){
                                item.value = (item.value /1024 /1024).toFixed(2);
                            });
                            avalon.log("hah")
                            avalon.log(data.obj)
                            var mapOption = {
                                title: {
                                    text: '各省份月流量统计情况',
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
                            var provinceflowChart = echarts.init(document.getElementById('provinceflow'));
                            provinceflowChart.setOption(mapOption);
                        }
                    }
                });
            },
            //获取列表
            getList: function () {
                $.myAjax({
                    url: '/dm/flow/count',
                    type: 'post',
                    data: {
                        'month': flowVm.yearMonth
                    },
                    onSuccess: function (data) {
                        if (data.state == 1) {
                            var total = parseInt(data.obj);
                            total = total % 10 == 0 ? total / 10 : parseInt(total / 10) + 1;
                            flowVm.total = total;
                        }
                    }
                });
                flowVm.queryPage(1);
            },
            //首页，上下页，尾页
            selectPage: function (value) {
                if (flowVm.total <= 0) {
                    return;
                }
                flowVm.pageNo += parseInt(value);
                if (flowVm.pageNo < 1) {
                    flowVm.pageNo = 1;
                }
                if (flowVm.pageNo > flowVm.total) {
                    flowVm.pageNo = flowVm.total;
                }
                flowVm.queryPage(flowVm.pageNo);
            }
            ,
            //查询数据
            queryPage: function (pageNo) {
                $.myAjax({
                    url: '/dm/flow/list',
                    type: 'post',
                    data: {
                        'pageNo': flowVm.pageNo,
                        'month': flowVm.yearMonth
                    },
                    onSuccess: function (data) {
                        if (data.state == 1) {
                            $.each(data.obj, function (n, item) {
                                var year = parseInt(item.date / 10000);
                                var month = parseInt(parseInt(item.date) % 10000 / 100);
                                var day = parseInt(item.date) % 10000 % 100;
                                item.date = year + '年' + month + '月' + day + '日';
                                item.value = flowVm.uintTransition(item.value);
                            });
                            flowVm.flowData = data.obj;
                        }
                    }
                });
            },
            //获取时间
            getTime: function () {
                $.myAjax({
                    url: '/dm/flow/allYear',
                    onSuccess: function (data) {
                        if (data.state == 1) {
                            flowVm.yearData = data.obj;
                            flowVm.year = data.obj[0];
                            flowVm.yearMonth = flowVm.year + '01';
                            flowVm.getContent();
                        }
                    }
                });
            },
            //获取内容
            getContent: function () {
                flowVm.getPeak();
                flowVm.drawDayflow();
                flowVm.drawProvinceflow();
                flowVm.getList();
            },
            //下载
            down: function(){
                window.open('/dm/flow/down?month='+flowVm.yearMonth);
            },
            //初始化
            init: function () {
                flowVm.getTime();
            }
        })
        avalon.scan($('#flow')[0], flowVm);
        flowVm.init();
    })

});