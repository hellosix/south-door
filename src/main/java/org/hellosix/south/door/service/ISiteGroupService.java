package org.hellosix.south.door.service;

import org.hellosix.south.door.model.SiteGroup;

import java.util.List;

/**
 * @author Jay.H.Zou
 * @date 6/28/2019
 */
public interface ISiteGroupService {

    boolean addGroup(SiteGroup siteGroup);

    boolean updateGroup(SiteGroup siteGroup);

    /**
     * 当这个组下面仍然有 site，则直接返回 false
     *
     * @return
     */
    boolean deleteGroupById(String groupId);

    boolean isExistSameGroupName(SiteGroup siteGroup);

    SiteGroup getSiteGroupById(String groupId);

    List<SiteGroup> getSiteGroupList();

}
