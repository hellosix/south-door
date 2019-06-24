/**
 * Created by lzz on 2018/3/5.
 */

$(document).ready(function () {
    showProxyList();
});

$("#add-proxy-model").click(function () {
    $("#add-proxy-modal").modal("show");
});


$("#save-proxy-modal-confirm").click(function () {
    var data = {};
    data.group = $("[name='modal-group']").val();
    data.proxyPort = $("[name='modal-proxyPort']").val();
    data.port = $("[name='modal-port']").val();
    data.ip = $("[name='modal-ip']").val();

    var check_url = get_url_by_param("/check/proxy-model", data);
    get(check_url, function (res) {
        if( res.code == 0 ){
            post("/add-proxy", data, function (res) {
                location.reload();
            })
        }else{
            var str = alert_msg(res.msg);
            $("#proxy-manager-alert").removeClass("hidden");
            $("#proxy-manager-alert").html(str);
        }
    }, function (res) {
        var str = alert_msg(res.msg);
        $("#proxy-manager-alert").removeClass("hidden");
        $("#proxy-manager-alert").html(str);
    });

});

$(document).on("click", ".proxy-row-delete", function () {
    var proxyPort = $(this).data("proxy-port");
    var url = "/remove-proxy?proxyPort=" + proxyPort;
    delete_confirm(url);
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
                '<td><a href="javascript:void(0)" class="btn btn-xs btn-danger proxy-row-delete" data-proxy-port="' + obj.proxyPort+ '">Remove</a></td>'+
                "</tr>";
        }
        $("#show-proxy-list").html( tableStr );
    });
    console.log("show proxy list");
}
