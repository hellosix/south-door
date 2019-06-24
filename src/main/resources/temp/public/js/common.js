/**
 * Created by lzz on 2018/2/1.
 */

function post(url, data, callback) {
    $.ajax({
        type: "POST",
        url: url,
        async: false,
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(data),
        dataType: "json",
        success: callback
    });
}

function get(url, callback,errocall) {
    $.ajax({
        type: "GET",
        url: url,
        async: false,
        contentType: "application/json; charset=utf-8",
        success: callback,
        error:errocall
    });
}


function delete_confirm(url) {
    $("#delete-modal").modal("show");
    $("#modal-confirm-delete").data("url", url);
}
$(document).on("click", "#modal-confirm-delete", function () {
    var req_url = $(this).data("url");
    $.get(req_url, function (res) {
        location.reload();
    });
});

function  alert_msg(msg) {
    var str = "<strong>Warn!</strong> <span> please check format </span>";
    if( msg ){
        str = "<strong>Warn!</strong> <span>" + msg + "</span>";
    }
    return str;
}


function checkGet(load_obj,check_url, url, callback, errorcall) {
    $.ajax({
        type:'get',
        url: check_url,
        beforeSend:function(XMLHttpRequest){
            $(load_obj).button('loading');
        },
        success:function (check_res) {
            console.log(check_res);
            if( check_res.code == 0 ){
                $.get(url, callback(check_res));
            }else{
                errorcall(check_res);
            }
        },
        complete:function(XMLHttpRequest){
            $(load_obj).button('reset');
        },
        error:errorcall
    });

}
function unique(arr) {
    var result = [], hash = {};
    for (var i = 0, elem; (elem = arr[i]) != null; i++) {
        if (!hash[elem]) {
            result.push(elem);
            hash[elem] = true;
        }
    }
    return result;
}

function getUrlPath(url) {
    var arr = url.split("//");
    var str = arr[1];
    var pos = str.indexOf("/");
    var res = "";
    if(pos != -1){
        res = str.substring(pos);
    }
    console.log(res);
    return res;
}

function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg); //获取url中"?"符后的字符串并正则匹配
    var context = "";
    if (r != null)
        context = r[2];
    reg = null;
    r = null;
    return context == null || context == "" || context == "undefined" ? "" : context;
}


String.prototype.format = function(args) {
    var result = this;
    if (arguments.length > 0) {
        if (arguments.length == 1 && typeof (args) == "object") {
            for (var key in args) {
                if(args[key]!=undefined){
                    var reg = new RegExp("({" + key + "})", "g");
                    result = result.replace(reg, args[key]);
                }
            }
        }
        else {
            for (var i = 0; i < arguments.length; i++) {
                if (arguments[i] != undefined) {
                    var reg= new RegExp("({)" + i + "(})", "g");
                    result = result.replace(reg, arguments[i]);
                }
            }
        }
    }
    return result;
}



function get_url_by_param(path, params) {
    var url = path + "?";
    for(param in params){
        if( params[param] ){
            url += param + "=" + params[param] + "&";
        }
    }
    console.log(url);
    return url;
}
