/**
 * Created by tang on 2016/3/13.
 */

$.extend({
    myAjax: function (option) {
        // 设置默认参数
        var settings = $.extend({
            url: '',
            data: {},
            type: 'get',
            dataType: 'json',
            onSuccess: function (data) {
            },
            onError: function (data) {
            }
        }, option);
        $.ajax({
            url: settings.url,
            type: settings.type,
            data: settings.data,
            cache: false,
            dataType: settings.dataType,
            success: function (data) {
                settings.onSuccess.call(this, data);
            },
            error: function (data) {
                if(data.responseText == "error"){
                    window.location.href = '/login.html';
                    return;
                }
                settings.onError.call(this, data);
            }
        });
    }
});
