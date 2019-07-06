package org.hellosix.south.door.service;

import org.hellosix.south.door.model.SiteInfo;

import java.util.List;

/**
 * @author Jay.H.Zou
 * @date 2019/6/25
 */
public interface ISiteInfoService {

    boolean saveSiteInfo(SiteInfo siteInfo);

    boolean deleteSiteInfo(String siteId);

    boolean isExistSameSiteName(SiteInfo siteInfo);

    SiteInfo getSiteInfoById(String siteId);

    List<SiteInfo> getSiteInfoListByGroupId(String groupId);

    List<SiteInfo> getAllSiteInfoList();


}
