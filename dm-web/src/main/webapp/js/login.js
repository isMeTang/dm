/**
 * Created by tang on 2016/3/3.
 */

//MVVM
$(function(){
    avalon.ready(function () {
        var vm = avalon.define({
            $id: "login",
            userName: "",
            passWord: "",
            //回车登录
            enter: function (event) {
                if (event.keyCode == 13) {
                    vm.login();
                }
            },
            //登录逻辑
            login: function(){
                if(vm.userName == ""){
                    layer.alert("请输入用户名");
                    return;
                }
                if(vm.passWord == ""){
                    layer.alert("请输入密码");
                    return;
                }
                $.myAjax({
                    url: "/admin/login",
                    data: {'userName':vm.userName,'passWord':vm.passWord},
                    onSuccess: function (data) {
                        if(data.state == 1){
                            localStorage.setItem("userId", JSON.stringify(data.obj));
                            window.location.href = '/index.html';
                        } else{
                            layer.alert(data.obj);
                        }
                    },
                    onError: function (data) {
                        layer.alert("登录失败");
                    }
                });
            },
            //初始化
            init: function () {
                avalon.log("init")
            }
        })
        avalon.scan();
        vm.init();
    })
});
