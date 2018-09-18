package cn.hellosix.common.util;

/**
 * Created by lzz on 2018/3/2.
 */

import org.fit.cssbox.demo.ImageRenderer;

import java.io.File;
import java.io.FileOutputStream;

/**
 *
 */
public class ImageTest {

    public static void main(String[] args) throws Exception {
        ImageRenderer render = new ImageRenderer();
        //render.setLoadImages(true, true);
        System.out.println("kaishi");
        String url = "https://www.newegg.com/";
        FileOutputStream out = new FileOutputStream(new File("/Users/lzz/work/java/south-door/src/main/resources/public/images/aa1.png"));
        render.renderURL(url, out, ImageRenderer.Type.PNG);
        System.out.println("OK");

    }

}