package com.lzz.logic;

import com.lzz.util.PropertiesUtil;
import org.fit.cssbox.demo.ImageRenderer;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by lzz on 2018/3/5.
 */
public class ImageTask implements Runnable {
    private static final String IMAGE_PATH = PropertiesUtil.get("image.db.path");

    private String showName;
    private String url;
    public ImageTask(String showName, String url) {
        this.showName = showName;
        this.url = url;
    }

    @Override
    public void run() {
        try {
            ImageRenderer render = new ImageRenderer();
            String url = this.url;
            FileOutputStream out = new FileOutputStream(new File( IMAGE_PATH + "/" + this.showName + ".png"));
            render.renderURL(url, out, ImageRenderer.Type.PNG);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
