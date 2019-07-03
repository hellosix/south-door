package org.hellosix.south.door.util;


import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import gui.ava.html.image.generator.HtmlImageGenerator;
import org.fit.cssbox.demo.ImageRenderer;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;

/**
 * @author Jay.H.Zou
 * @date 2019/7/2
 */
public class ImageUtil {

    private ImageUtil() {
    }

    public static void getImage() throws Exception {
        String url = "https://element.eleme.cn/#/zh-CN";
        /*final WebClient webClient = new WebClient(BrowserVersion.CHROME);
        webClient.getOptions().setThrowExceptionOnScriptError(false);//当JS执行出错的时候是否抛出异常, 这里选择不需要
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);//当HTTP的状态非200时是否抛出异常, 这里选择不需要
        webClient.getOptions().setActiveXNative(true);
        webClient.getOptions().setCssEnabled(true);//是否启用CSS, 因为不需要展现页面, 所以不需要启用
        webClient.getOptions().setJavaScriptEnabled(true); //很重要，启用JS
        HtmlPage page = webClient.getPage(url);*/
        /*ImageRenderer render = new ImageRenderer();
        render.setWindowSize(new Dimension(1600, 800), false);
        FileOutputStream out = new FileOutputStream(new File( "E:/images/111.png"));
        render.renderURL(url, out, ImageRenderer.Type.PNG);*/
        HtmlImageGenerator imageGenerator = new HtmlImageGenerator();
        imageGenerator.setSize(new Dimension(1600, 800));
        //加载html模版
        imageGenerator.loadUrl(url);

        //把html写入到图片
        imageGenerator.saveAsImage("E://images/hello-world.png");

    }

    public static void main(String[] args) throws Exception {
        getImage();
    }
}
