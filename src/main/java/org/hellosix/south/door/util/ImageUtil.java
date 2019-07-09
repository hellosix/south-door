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
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(path + File.separator + getImageNameBySiteName(siteName)));
        byte[] bs = new byte[1024];
        int len;
        while ((len = fileInputStream.read(bs)) != -1) {
            bos.write(bs, 0, len);
        }
        bos.flush();
        bos.close();
    }

    public static boolean existImage(String filePath, String siteName) {
        File file = new File(filePath + getImageNameBySiteName(siteName));
        return file.exists();
    }

    public static void updateImageName(String filePath, String oldSiteName, String newSiteName) {
        if (!filePath.endsWith("/")) {
            filePath += "/";
        }
        File file = new File(filePath + getImageNameBySiteName(oldSiteName));
        // 判断原文件是否存在（防止文件名冲突）
        if (!file.exists()) {
            return;
        }
        newSiteName = newSiteName.trim();
        if (StringUtils.isBlank(newSiteName)) {
            return;
        }
        String newFilePath = filePath + getImageNameBySiteName(newSiteName);
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

    public static String getImageNameBySiteName(String siteName) {
        return siteName.replaceAll(" ", "-") + IMAGE_SUFFIX_PNG;
    }

    public static void main(String[] args) {
        getAllFiles("E://project/hellosix/south-door/src/main/resources/public/site/site/");
    }


}
