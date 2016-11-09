/**
 * Created by tang on 2016/3/3.
 */

//MVVM
$(function () {
    avalon.ready(function () {
        var vm = avalon.define({
            $id: "home",
            roleName: "",
            user: {},
            menuData: [],
            //退出
            quit: function () {
                layer.confirm('确定退出？', {
                    btn: ['确定', '取消'] //按钮
                }, function (index) {
                    $.myAjax({
                        url: "/admin/quit",
                        onSuccess: function (data) {
                            if (data.state == 1) {
                                layer.close(index);
                                window.location.href = '/login.html';
                            }
                        }
                    });
                });
            },
            //用户信息
            userInfo: function () {
                $.myAjax({
                    url: "/dm/user/userInfo",
                    data: {'userId': localStorage.getItem("userId")},
                    onSuccess: function (data) {
                        if (data.state == 1) {
                            switch (data.obj.roleId){
                                case 1 : vm.roleName = '超级管理员';break;
                                case 2 : vm.roleName = '分析师';break;
                                default : vm.roleName = '合作伙伴';break;
                            }
                            vm.user = data.obj;
                            vm.getMenu(data.obj.roleId);
                        }
                    }
                });
            },
            //获取菜单
            getMenu: function (roleId) {
                $.myAjax({
                    url: "/dm/user/menu",
                    type: "post",
                    data: {'roleId': roleId},
                    onSuccess: function (data) {
                        if (data.state == 1) {
                            vm.menuData = data.obj;
                            avalon.log(vm.menuData[0].subMenu[0].url);
                            vm.getFuncHtml(vm.menuData[0].subMenu[0].url)
                            /* $('#mainFunName').text(vm.menuData[0].mainMenu.name);
                             $('#subFunName').text(vm.menuData[0].subMenu[0].name);*/
                        }
                    }
                });
            },
            //点击子菜单
            subFuntion: function (el) {
                avalon.log(el)
                vm.getFuncHtml(el.url);
                /*$('#subFunName').text(el.name);*/
            },
            //获取功能页面
            getFuncHtml: function (url) {
                $.myAjax({
                    url: url + '.html',
                    dataType: "html",
                    onSuccess: function (data) {
                        $('.content').html(data);
                    }
                });
            },
            //初始化
            init: function () {
                vm.userInfo();
            }
        })
        avalon.scan();
        vm.init();
    })
});
