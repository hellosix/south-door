/**
 * Created by lzz on 2018/2/4.
 */

'use strict';
$(document).ready(function () {
    var $wrap = $('.wrapper'), pages = $('.page').length, scrolling = false, currentPage = 1, $navPanel = $('.nav-panel'), $scrollBtn = $('.scroll-btn'), $navBtn = $('.nav-btn');
    function manageClasses() {
        $wrap.removeClass(function (index, css) {
            return (css.match(/(^|\s)active-page\S+/g) || []).join(' ');
        });
        $wrap.addClass('active-page' + currentPage);
        $navBtn.removeClass('active');
        $('.nav-btn.nav-page' + currentPage).addClass('active');
        $navPanel.addClass('invisible');
        scrolling = true;
        setTimeout(function () {
            $navPanel.removeClass('invisible');
            scrolling = false;
        }, 1000);
    }
    function navigateUp() {
        if (currentPage > 1) {
            currentPage--;
            if (Modernizr.csstransforms) {
                manageClasses();
            } else {
                $wrap.animate({ 'top': '-' + (currentPage - 1) * 100 + '%' }, 1000);
            }
        }
    }
    function navigateDown() {
        if (currentPage < pages) {
            currentPage++;
            if (Modernizr.csstransforms) {
                manageClasses();
            } else {
                $wrap.animate({ 'top': '-' + (currentPage - 1) * 100 + '%' }, 1000);
            }
        }
    }
    $(document).on('mousewheel DOMMouseScroll', function (e) {
        if (!scrolling) {
            if (e.originalEvent.wheelDelta > 0 || e.originalEvent.detail < 0) {
                navigateUp();
            } else {
                navigateDown();
            }
        }
    });
    $(document).on('click', '.scroll-btn', function () {
        if ($(this).hasClass('up')) {
            navigateUp();
        } else {
            navigateDown();
        }
    });
    $(document).on('click', '.nav-btn:not(.active)', function () {
        if (!scrolling) {
            var target = $(this).attr('data-target');
            loadData(target);

            if (true) {
                $wrap.removeClass(function (index, css) {
                    return (css.match(/(^|\s)active-page\S+/g) || []).join(' ');
                });
                $wrap.addClass('active-page' + target);
                $navBtn.removeClass('active');
                $(this).addClass('active');
                $navPanel.addClass('invisible');
                currentPage = target;
                scrolling = true;
                setTimeout(function () {
                    $navPanel.removeClass('invisible');
                    scrolling = false;
                }, 1000);
            } else {
                $wrap.animate({ 'top': '-' + (target - 1) * 100 + '%' }, 1000);
            }
        }
    });
});


function loadData(target) {
    console.log(target);
    if( target == "3" ){ // proxy 要剪掉一
        showProxyList();
    }else if (target == "2"){
        showUrlList();
    }else{
        showUrlCenter();
    }
}
