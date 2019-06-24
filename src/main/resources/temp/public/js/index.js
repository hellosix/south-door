/**
 * Created by lzz on 2018/3/4.
 */


        
var url_box = '<div class="weblist">' +
    '<a href="{showUrl}" target="_black">' +
    '<div class="url-box">' +
    '<div class="url-box-header proxy-{isProxy}">' +
    '<div class="pull-left box-header-img"><img src="{domain}/favicon.ico" onerror="this.src=\'../images/favicon.ico\'" /></div>' +
    '<div class="pull-left box-header-con">{showName}</div>' +
    '</div>' +
    '<div class="url-box-body"><img src="../images/db/{showName}.png" onerror="this.src=\'../images/url-default.png\'"  /></div>' +
    '</div>' +
    '</a>' +
    '</div>';

$(document).ready(function () {
    init_data();
    init_page();
});

function init_data() {
    get("/url-map", function (obj) {
        var urlMap = obj.result;
        var isfirst = true;
        for(var key in urlMap){
            if( isfirst ){
                $("#scroll_nav").append("<b class='current'> " + key + " </b>");
            }else{
                $("#scroll_nav").append("<b>" + key + "</b>");
            }
            isfirst = false;
            var li_str = "<li>";
            for(var i = 0; i < urlMap[key].length; i++){
                var conObj = urlMap[key][i];
                conObj.showName = conObj["urlModel"]["showName"];
                if( conObj["urlModel"]["proxyPort"] > 0 ){
                    conObj.isProxy = true;
                }else{
                    conObj.isProxy = false;
                }
                li_str += url_box.format( conObj );
            }
            li_str += "</li>";
            $("#scroll_box > ul.roll_ul").append( li_str );
        }

    });
}

function init_page(){
    var page_height = document.body.clientHeight;
    $("#scroll_box").height(page_height + 50);
    $("#banner_next").height(page_height);
    $("#banner_prev").height(page_height);
    var w=100+"%";
    var n=$('.roll_ul li').size();
    $('.roll_ul').width(100*n+"%");
    $('.roll_ul li').width(100/n+"%");
    var t=0;
    $('#banner_next').bind('click',function(){
        t++;
        $('#banner_prev').show();
        var f=$('.roll_ul').css('left');
        $('.roll_ul').animate({'left':'-=100%'},300);
        if(t==n-1){
            $('#banner_next').hide()
        }
        $('#scroll_nav b').eq(t).siblings().removeClass('current');
        $('#scroll_nav b').eq(t).addClass('current');
    })
    $('#banner_prev').click(function(){
        t--;
        $('#banner_next').show();
        var l=$('.roll_ul').css('left');
        $('.roll_ul').animate({'left':'+=100%'},300)
        if(t==0){
            $('#banner_prev').hide()
        }
        $('#scroll_nav b').eq(t).siblings().removeClass('current');
        $('#scroll_nav b').eq(t).addClass('current');
    })
    $('#scroll_nav b').each(function(i){
        $(this).click(function(){
            i=$(this).index();
            t=i;
            $('#banner_prev').show();
            if(i==0){
                $('#banner_prev').hide();
                $('#banner_next').show();
            }else if(i==n-1){
                $('#banner_next').hide();
            }else{
                $('#banner_next').show();
                $('#banner_prev').show();
            }
            var cd=-100*i+"%";
            $('.roll_ul').animate({'left':cd},500);
            $(this).siblings().removeClass('current');
            $(this).addClass('current')
        })
    })
}
