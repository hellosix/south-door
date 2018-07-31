/**
 * Created by lzz on 2018/3/5.
 */

$(document).ready(function () {
    showUrlList();
});

$("#add-url-button").click(function () {
    $("[name='modal-group']").autocomplete({
        source: "/url-group"
    });
    $("#add-url-modal").modal("show");
});

$("#save-url-modal-confirm").click(function () {
    var data = {};
    data.group = $("[name='modal-group']").val();
    data.showName = $("[name='modal-showName']").val();
    data.url = $("[name='modal-url']").val();
    data.proxyPort = $("[name='modal-proxyPort']").val();

    var check_url = get_url_by_param("/check/url-model", data);
    get(check_url, function (res) {
        if( res.code == 0 ){
            post("/add-url", data, function (res) {
                location.reload();
            })
        }else{
            var str = alert_msg(res.msg);
            $("#url-manager-alert").removeClass("hidden");
            $("#url-manager-alert").html(str);
        }
    }, function (res) {
        var str = alert_msg(res.msg);
        $("#url-manager-alert").removeClass("hidden");
        $("#url-manager-alert").html(str);
    });
});

$(document).on("click", ".url-row-delete", function () {
    var showName = $(this).data("show-name");
    var url = "/remove-url?showName=" + showName;
    delete_confirm(url);
});

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
                "<td>" + obj.group + "</td>" +
                "<td><a href='" + obj.url + "' target='_black'>" + obj.url + "</a></td>" +
                "<td>" + obj.proxyPort + "</td>" +
                '<td><a href="javascript:void(0)" class="btn btn-xs btn-danger url-row-delete" data-show-name="' + obj.showName + '">Remove</a></td>'+
                "</tr>";
        }
        $("#show-url-list").html( tableStr );
    });
    console.log("show url list");
}
