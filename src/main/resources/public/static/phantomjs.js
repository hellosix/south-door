var page = require('webpage').create();
//第一个参数，可以传递进来需要截图的路径
page.open('http://example.com', function(status) {
  console.log("Status: " + status);
  if(status === "success") {
    //截图 图片名称。可以选择图片质量
    page.render('example.png');
  }
  phantom.exit();
});