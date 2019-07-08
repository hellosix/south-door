package org.hellosix.south.door.util;


import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Jay.H.Zou
 * @date 2019/7/2
 */
public class ImageUtil {

    public static String IMAGE_SUFFIX_PNG = ".png";

    public static String IMAGE_SUFFIX_JPG = ".jpg";

    /**
     * 保存文件，直接以multipartFile形式
     *
     * @param multipartFile
     * @param path          文件保存绝对路径
     * @return 返回文件名
     * @throws IOException
     */
    public static void saveImage(MultipartFile multipartFile, String path, String siteName) throws Exception {
        if (!path.endsWith("/")) {
            path += "/";
        }
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        FileInputStream fileInputStream = (FileInputStream) multipartFile.getInputStream();
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(path + File.separator + siteName.replaceAll(" ", "-") + IMAGE_SUFFIX_PNG));
        byte[] bs = new byte[1024];
        int len;
        while ((len = fileInputStream.read(bs)) != -1) {
            bos.write(bs, 0, len);
        }
        bos.flush();
        bos.close();
    }

    /*public static void deleteImage(String filePath, String siteName) {
        if (!filePath.endsWith("/")) {
            filePath += "/";
        }
        File file = new File(filePath + siteName.replaceAll(" ", "-") + IMAGE_SUFFIX_PNG);
        if (file.exists() && file.isFile()) {
            file.delete();
        }
    }*/

    public static void updateImageName(String filePath, String oldSiteName, String newSiteName) {
        if (!filePath.endsWith("/")) {
            filePath += "/";
        }
        File file = new File(filePath + oldSiteName.replaceAll(" ", "-") + IMAGE_SUFFIX_PNG);
        // 判断原文件是否存在（防止文件名冲突）
        if (!file.exists()) {
            return;
        }
        newSiteName = newSiteName.trim();
        if (StringUtils.isBlank(newSiteName)) {
            return;
        }
        String newFilePath = filePath + newSiteName.replaceAll(" ", "-") + IMAGE_SUFFIX_PNG;
        File newFile = new File(newFilePath);
        file.renameTo(newFile); // 修改文件名
    }

    public static List<File> getAllFiles(String filePath) {
        if (!filePath.endsWith("/")) {
            filePath += "/";
        }
        File file = new File(filePath);
        if (file.exists() && file.isDirectory()) {
            File[] files = file.listFiles();
            List<File> fileList = new ArrayList<>(Arrays.asList(files));
            return fileList;
        }
        return new ArrayList<>();
    }

    public static void main(String[] args) {
        getAllFiles("E://project/hellosix/south-door/src/main/resources/public/site/site/");
    }

}
