package org.hellosix.south.door.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.hellosix.south.door.dao.SiteGroupDao;
import org.hellosix.south.door.dao.SiteInfoDao;
import org.hellosix.south.door.model.SiteGroup;
import org.hellosix.south.door.model.SiteInfo;
import org.hellosix.south.door.proxy.IProxyManage;
import org.hellosix.south.door.proxy.http.HttpProxyManage;
import org.hellosix.south.door.service.ISiteGroupService;
import org.hellosix.south.door.util.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

/**
 * @author Jay.H.Zou
 * @date 6/28/2019
 */
@Service
public class SiteGroupService implements ISiteGroupService {

    private static final Logger logger = LoggerFactory.getLogger(SiteGroupService.class);

    @Autowired
    private SiteGroupDao siteGroupDao;

    @Autowired
    private SiteInfoDao siteInfoDao;

    @Autowired
    private IProxyManage proxyManage;

    @Override
    public SiteGroup addGroup(SiteGroup siteGroup) {
        if (!verifyParam(siteGroup, false)) {
            return null;
        }
        siteGroup.setGroupId(CommonUtil.getUUID());
        siteGroup.setUpdateTime(CommonUtil.getCurrentTimestamp());
        try {
            siteGroupDao.insertSiteGroup(siteGroup);
            return siteGroup;
        } catch (Exception e) {
            logger.error("add site group to db failed, site group: " + siteGroup, e);
        }
        return null;
    }

    @Override
    public SiteGroup updateGroup(SiteGroup siteGroup) {
        if (!verifyParam(siteGroup, true)) {
            return null;
        }
        siteGroup.setUpdateTime(CommonUtil.getCurrentTimestamp());
        try {
            siteGroupDao.updateSiteGroup(siteGroup);
            return siteGroup;
        } catch (Exception e) {
            logger.error("update site group failed, site group: " + siteGroup, e);
        }
        return null;
    }

    @Override
    public boolean deleteGroupById(String groupId) {
        if (StringUtils.isNotBlank(groupId)) {
            try {
                List<SiteInfo> siteInfoList = siteInfoDao.selectSiteInfoListByGroupId(groupId);
                if (siteInfoList == null || siteInfoList.isEmpty()) {
                    siteGroupDao.deleteSiteGroupById(groupId);
                    return true;
                }
            } catch (Exception e) {
                logger.error("delete group failed, group id = " + groupId, e);
            }
        }
        return false;
    }

    @Override
    public boolean isExistSameGroupName(SiteGroup siteGroup) {
        try {
            SiteGroup existSiteGroup = siteGroupDao.selectGroupByName(siteGroup.getGroupName());
            return existSiteGroup != null;
        } catch (Exception e) {
            logger.error("get site group by name failed, " + siteGroup, e);
        }
        return true;
    }

    @Override
    public SiteGroup getSiteGroupById(String groupId) {
        if (StringUtils.isNotBlank(groupId)) {
            try {
                return siteGroupDao.selectGroupById(groupId);
            } catch (Exception e) {
                logger.error("get site group failed, group id = " + groupId, e);
            }
        }
        return null;
    }

    @Override
    public List<SiteGroup> getSiteGroupList() {
        try {
            return siteGroupDao.selectGroupList();
        } catch (Exception e) {
            logger.error("get site group list failed", e);
        }
        return null;
    }

    private boolean verifyParam(SiteGroup siteGroup, boolean isUpdate) {
        if (siteGroup == null || StringUtils.isBlank(siteGroup.getGroupName())) {
            return false;
        }
        if (isUpdate) {
            return StringUtils.isNotBlank(siteGroup.getGroupId());
        }
        return true;
    }
}
