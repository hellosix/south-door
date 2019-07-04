package org.hellosix.south.door.util;


import org.fit.cssbox.demo.ImageRenderer;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;

/**
 * @author Jay.H.Zou
 * @date 2019/7/2
 */
public class ImageUtil {

    private static final String IMAGE_PREFIX_PATH = "./";

    public static void getImage(String url, String imageName) throws Exception {
        ImageRenderer render = new ImageRenderer();
        render.setWindowSize(new Dimension(1600, 800), false);
        FileOutputStream out = new FileOutputStream(new File(IMAGE_PREFIX_PATH + imageName + ".png"));
        render.renderURL(url, out, ImageRenderer.Type.PNG);
    }

    public static void main(String[] args) throws Exception {
        String url = "http://wufazhuce.com/";

        getImage(url, "one");
    }

}
