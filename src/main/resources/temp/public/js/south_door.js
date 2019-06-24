/**
 * Created by lzz on 2018/2/4.
 */

var target_path = "/south-door?target=";

$(document).ready(function () {
    var urlTarget = getQueryString("target");
    if( urlTarget && urlTarget.length > 0 && urlTarget != 1){
        var target = "[data-target='" + urlTarget + "']";
        $( target ).click();
    }else{
        showUrlCenter();
    }

});

$(document).on("click", ".url-wrapper", function () {
    var detail = $(this).data("detail");
    var url = detail.url;
    if( detail.proxyPort != 0 ){
        var url = document.location.protocol + "//" + location.hostname + ":" + detail.proxyPort + getUrlPath(url);
    }
    console.log( url );
    window.open( url );
});

$("#add-url-model").click(function () {
    var params = {};
    params.showName = $('[name="showName"]').val();
    params.url = $('[name="url"]').val();
    params.proxyPort = $('[name="proxyPort"]').val();
    console.log( params );
    var $btn = $(this).button('loading');
    var check_url = get_url_by_param("/check/url-model", params);
    var url = get_url_by_param("/add-url", params);

    checkGet(this,check_url, url, function (res) {
        window.location.href = target_path + 2;
    }, function (res) {
        var str = alert_msg(res.msg);
        $("#url-manager-alert").removeClass("hidden");
        $("#url-manager-alert").html(str);
    });
});

$(document).on("click", ".url-row-delete", function () {
    var showName = $(this).data("show-name");
    var url = "/remove-url?showName=" + showName;
    $.get(url, function (res) {
        window.location.href = target_path + 2;
    });
});

$("#add-proxy-model").click(function () {
    var params = {};
    params.proxyPort = $('[name="p-proxy-port"]').val();
    params.ip = $('[name="p-ip"]').val();
    params.port = $('[name="p-port"]').val();
    console.log( params );
    var check_url = get_url_by_param("/check/proxy-model", params);
    var url = get_url_by_param("/add-proxy", params);

    checkGet(this,check_url, url, function (res) {
        window.location.href = target_path + 3;
    }, function (res) {
        var str = alert_msg(res.msg);
        $("#proxy-manager-alert").removeClass("hidden");
        $("#proxy-manager-alert").html(str);
    });
});

$(document).on("click", ".proxy-row-delete", function () {
    var proxyPort = $(this).data("proxy-port");
    var url = "/remove-proxy?proxyPort=" + proxyPort;
    $.get(url, function (res) {
        window.location.href = target_path + 3;
    });
});

function showProxyList(){
    $.get("/proxy-list", function (res) {
        var result = res.result;
        var tableStr = "";
        for(i in result){
            var obj = result[i];
            tableStr += "<tr>" +
                "<td>" + obj.proxyPort + "</td>" +
                "<td>" + obj.port + "</td>" +
                "<td>" + obj.ip + "</td>" +
                '<td><a href="javascript:void(0)" class="btn btn-block btn-xs btn-danger proxy-row-delete" data-proxy-port="' + obj.proxyPort+ '">Remove</a></td>'+
                "</tr>";
        }
        $("#show-proxy-list").html( tableStr );
    });
    console.log("show proxy list");
}


function showUrlCenter() {
    $.get("/url-list", function (res) {
        var result = res.result;
        var url_box = "";
        for(i in result){
            var obj = result[i];
            var detail = JSON.stringify(obj);
            var isProxy = "without-proxy";
            if( obj.proxyPort != 0 ){
                obj.proxyPort = "[" + obj.proxyPort + "]";
                isProxy = "normal-proxy";
            }else{
                obj.proxyPort = "";
            }
            url_box += '<div class="url-wrapper" data-detail=' + "'" + detail + "'" + '>' +
                '<div class="item">' +
                '<div class="url-box ' + isProxy + '" >' +
                '<strong>' + obj.showName + '</strong>' +
                '</div>' +
                '<span class="information">' +
                '<strong>' + obj.showName + obj.proxyPort + '</strong> ' + obj.url +
                '</span>' +
                '</div>' +
                '</div>'
        }
        $("#show-user-center").html( url_box );
    });
    console.log("show url center");
}

function  showUrlList() {
    $.get("/url-list", function (res) {
        var result = res.result;
        var tableStr = "";
        for(i in result){
            var obj = result[i];
            if( obj.proxyPort == 0 ){
                obj.proxyPort = "";
            }
            tableStr += "<tr>" +
                "<td>" + obj.showName + "</td>" +
                "<td><a href='" + obj.url + "' target='_black'>" + obj.url + "</a></td>" +
                "<td>" + obj.proxyPort + "</td>" +
                '<td><a href="javascript:void(0)" class="btn btn-block btn-xs btn-danger url-row-delete" data-show-name="' + obj.showName + '">Remove</a></td>'+
                "</tr>";
        }
        $("#show-url-list").html( tableStr );
    });
    console.log("show url list");
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

