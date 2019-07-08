package org.hellosix.south.door.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.hellosix.south.door.dao.SiteInfoDao;
import org.hellosix.south.door.model.SiteInfo;
import org.hellosix.south.door.proxy.IProxyManage;
import org.hellosix.south.door.service.ISiteInfoService;
import org.hellosix.south.door.util.CommonUtil;
import org.hellosix.south.door.util.ImageUtil;
import org.hellosix.south.door.util.UrlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.UnknownHostException;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import static org.hellosix.south.door.util.ImageUtil.IMAGE_SUFFIX_JPG;
import static org.hellosix.south.door.util.ImageUtil.IMAGE_SUFFIX_PNG;

/**
 * @author Jay.H.Zou
 * @date 6/29/2019
 */
@Service
public class SiteInfoService implements ISiteInfoService {

    private static final Logger logger = LoggerFactory.getLogger(SiteInfoService.class);

    public static String DEFAULT_IMAGE_PATH_PREFIX = "/public/default-site/";

    public static String IMAGE_PATH_PREFIX = "/site/";

    @Value("${south-door.data.site-image-path}")
    private String siteImagePath;

    @Autowired
    private SiteInfoDao siteInfoDao;

    @Autowired
    private IProxyManage proxyManage;

    @Override
    public boolean saveSiteInfo(SiteInfo siteInfo) {
        if (!verifyParam(siteInfo)) {
            return false;
        }
        siteInfo.setSiteName(siteInfo.getSiteName().trim());
        siteInfo.setUpdateTime(CommonUtil.getCurrentTimestamp());
        boolean isProxy = siteInfo.getIsProxy();
        String address = siteInfo.getAddress();
        if (isProxy) {
            try {
                Integer proxyPort = siteInfo.getProxyPort();
                if (proxyPort != null) {
                    address = UrlUtil.getProxyAddress(address, proxyPort);
                } else {
                    return false;
                }
            } catch (UnknownHostException e) {
                logger.error("build proxy address failed.", e);
                return false;
            }
        }
        siteInfo.setProxyAddress(address);

        String imagePath = null;


        try {
            if (StringUtils.isBlank(siteInfo.getSiteId())) {
                imagePath = DEFAULT_IMAGE_PATH_PREFIX + new Random().nextInt(16) + IMAGE_SUFFIX_JPG;
                siteInfo.setSiteId(CommonUtil.getUUID());
                siteInfo.setImagePath(imagePath);
                siteInfoDao.insertSiteInfo(siteInfo);
            } else {
                SiteInfo oldSite = siteInfoDao.selectSiteInfoById(siteInfo.getSiteId());
                if (oldSite != null) {
                    try {
                        ImageUtil.updateImageName(siteImagePath, oldSite.getSiteName(), siteInfo.getSiteName());
                        imagePath = IMAGE_PATH_PREFIX + siteInfo.getSiteName().replaceAll(" ", "-") + IMAGE_SUFFIX_PNG;
                        siteInfo.setImagePath(imagePath);
                        siteInfoDao.updateSiteInfo(siteInfo);
                    } catch (Exception e) {
                        logger.error("update image name failed", e);
                    }
                }
            }

            return true;
        } catch (Exception e) {
            logger.error("add site info to db failed, " + siteInfo, e);
            return false;
        }
    }

    @Override
    public boolean deleteSiteInfo(String siteId) {
        if (StringUtils.isNotBlank(siteId)) {
            try {
                siteInfoDao.deleteSiteInfoById(siteId);
                proxyManage.stopProxyTask(siteId);
                return true;
            } catch (Exception e) {
                logger.error("delete site info failed, site id = " + siteId, e);
            }
        }
        return false;
    }

    @Override
    public boolean isExistSameSiteName(SiteInfo siteInfo) {
        try {
            SiteInfo site = siteInfoDao.selectSiteInfoByName(siteInfo.getSiteName());
            if (site != null) {
                return !Objects.equals(site.getSiteId(), siteInfo.getSiteId());
            } else {
                return false;
            }
        } catch (Exception e) {
            logger.error("get site info by name failed.", e);
        }
        return true;
    }

    @Override
    public SiteInfo getSiteInfoById(String siteId) {
        if (StringUtils.isNotBlank(siteId)) {
            try {
                return siteInfoDao.selectSiteInfoById(siteId);
            } catch (Exception e) {
                logger.error("get site info failed, site id = " + siteId, e);
            }
        }
        return null;
    }

    @Override
    public List<SiteInfo> getSiteInfoListByGroupId(String groupId) {
        if (StringUtils.isNotBlank(groupId)) {
            try {
                return siteInfoDao.selectSiteInfoListByGroupId(groupId);
            } catch (Exception e) {
                logger.error("get site info list by group id failed, group id = " + groupId, e);
            }
        }
        return null;
    }

    @Override
    public List<SiteInfo> getAllSiteInfoList() {
        try {
            return siteInfoDao.selectSiteInfoList();
        } catch (Exception e) {
            logger.error("get site info list.", e);
            return null;
        }
    }

    private boolean verifyParam(SiteInfo siteInfo) {
        if (siteInfo == null) {
            return false;
        }
        if (StringUtils.isBlank(siteInfo.getSiteName())
                || StringUtils.isBlank(siteInfo.getSiteName().replaceAll(" ", ""))
                || StringUtils.isBlank(siteInfo.getAddress())
                || StringUtils.isBlank(siteInfo.getGroupId())) {
            return false;
        }
        return true;
    }

}
