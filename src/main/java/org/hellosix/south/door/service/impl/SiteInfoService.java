package org.hellosix.south.door.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.hellosix.south.door.dao.SiteInfoDao;
import org.hellosix.south.door.model.SiteInfo;
import org.hellosix.south.door.proxy.IProxyManage;
import org.hellosix.south.door.service.ISiteInfoService;
import org.hellosix.south.door.util.CommonUtil;
import org.hellosix.south.door.util.UrlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.UnknownHostException;
import java.util.List;

/**
 * @author Jay.H.Zou
 * @date 6/29/2019
 */
@Service
public class SiteInfoService implements ISiteInfoService {

    private static final Logger logger = LoggerFactory.getLogger(SiteInfoService.class);

    private static String IMAGE_PATH_PREFIX = "/images/site/";

    private static String IMAGE_NAME_SUFFIX = ".png";

    @Autowired
    private SiteInfoDao siteInfoDao;

    @Autowired
    private IProxyManage proxyManage;

    @Override
    public boolean addSiteInfo(SiteInfo siteInfo) {
        if (!verifyParam(siteInfo, false)) {
            return false;
        }
        return saveSiteInfo(siteInfo, true);
    }

    @Override
    public boolean updateSiteInfo(SiteInfo siteInfo) {
        if (!verifyParam(siteInfo, true)) {
            return false;
        }
        proxyManage.stopProxyTask(siteInfo.getSiteId());
        return saveSiteInfo(siteInfo, false);
    }

    private boolean saveSiteInfo(SiteInfo siteInfo, boolean isAdd) {
        siteInfo.setUpdateTime(CommonUtil.getCurrentTimestamp());
        boolean isProxy = siteInfo.getIsProxy();
        String address = siteInfo.getAddress();
        if (isProxy) {
            try {
                address = UrlUtil.getProxyAddress(address, siteInfo.getProxyPort());
            } catch (UnknownHostException e) {
                logger.error("build proxy address failed.", e);
                return false;
            }
        }
        siteInfo.setProxyAddress(address);
        String imagePath = IMAGE_PATH_PREFIX + siteInfo.getSiteName().replaceAll(" ", "-").toLowerCase() + IMAGE_NAME_SUFFIX;
        siteInfo.setImagePath(imagePath);
        // TODO: 添加图片
        try {
            if (isAdd) {
                siteInfo.setSiteId(CommonUtil.getUUID());
                siteInfoDao.insertSiteInfo(siteInfo);
            } else {
                siteInfoDao.updateSiteInfo(siteInfo);
            }

            return true;
        } catch (Exception e) {
            logger.error("add site info to db failed, " + siteInfo, e);
            return false;
        }

    }

    @Override
    public boolean deleteSiteInfo(String siteId) {
        // TODO: 删除图片
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
            return site != null;
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

    private boolean verifyParam(SiteInfo siteInfo, boolean isUpdate) {
        if (siteInfo == null) {
            return false;
        }
        // TODO: 检验 URL
        if (StringUtils.isBlank(siteInfo.getSiteName())
                || StringUtils.isBlank(siteInfo.getAddress())
                || StringUtils.isBlank(siteInfo.getGroupId())) {
            return false;
        }
        if (isUpdate) {
            return StringUtils.isNotBlank(siteInfo.getSiteId());
        }
        return true;
    }
}
