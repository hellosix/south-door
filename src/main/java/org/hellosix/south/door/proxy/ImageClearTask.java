package org.hellosix.south.door.proxy;

import org.apache.commons.lang3.StringUtils;
import org.hellosix.south.door.dao.SiteInfoDao;
import org.hellosix.south.door.model.SiteInfo;
import org.hellosix.south.door.util.ImageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Iterator;
import java.util.List;

/**
 * @author Jay.H.Zou
 * @date 2019/7/7
 */
@Component
public class ImageClearTask {

    private static final Logger logger = LoggerFactory.getLogger(ImageClearTask.class);

    @Value("${south-door.data.site-image-path}")
    private String siteImagePath;

    @Autowired
    protected SiteInfoDao siteInfoDao;

    @Scheduled(initialDelay = 6000000, fixedRate = 6000000)
    public void clearImages() {
        try {
            List<SiteInfo> siteInfoList = siteInfoDao.selectSiteInfoList();
            List<File> allFilesName = ImageUtil.getAllFiles(siteImagePath);
            if (siteInfoList != null && siteInfoList.size() > 0) {
                for (SiteInfo siteInfo : siteInfoList) {
                    String imagePath = siteInfo.getImagePath();
                    if (StringUtils.isNotBlank(imagePath)) {
                        Iterator<File> iterator = allFilesName.iterator();
                        while (iterator.hasNext()) {
                            File file = iterator.next();
                            String fileName = file.getName();
                            if (imagePath.contains(fileName)) {
                                iterator.remove();
                                break;
                            }
                        }
                    }
                }
                for (File file : allFilesName) {
                    try {
                        boolean delete = file.delete();
                    } catch (Exception e) {
                        logger.error("clear " + file + " failed.");
                    }
                }
            }
        } catch (Exception e) {
            logger.error("clear useless image task failed.", e);
        }

    }

}
