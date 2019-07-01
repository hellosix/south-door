package org.hellosix.south.door.dao;

import org.apache.ibatis.annotations.*;
import org.hellosix.south.door.model.SiteInfo;

import java.util.List;

/**
 * @author Jay.H.Zou
 * @date 2019/6/24
 */
@Mapper
public interface SiteInfoDao {


    @Select("SELECT * FROM site_info WHERE site_id = #{siteId}")
    SiteInfo selectSiteInfoById(String siteId);

    @Select("SELECT * FROM site_info WHERE site_name = #{siteName}")
    SiteInfo selectSiteInfoByName(String siteName);

    @Select("SELECT site_info.*, site_group.group_name as group_name FROM site_info, site_group WHERE site_info.group_id = site_group.group_id")
    List<SiteInfo> selectSiteInfoList();

    @Select("SELECT * FROM site_info WHERE group_id = #{groupId}")
    List<SiteInfo> selectSiteInfoListByGroupId(String groupId);

    @Insert("INSERT INTO site_info " +
            "(site_id, group_id, site_name, address, is_proxy, proxy_port, image_path, proxy_address, description, update_time) " +
            "VALUES (#{siteId}, #{groupId}, #{siteName}, #{address}, #{isProxy}, #{proxyPort}, #{imagePath}, #{proxyAddress}, #{description}, #{updateTime})")
    int insertSiteInfo(SiteInfo siteInfo);

    @Update("UPDATE site_info SET site_nam = #{siteName}, address = #{address}, is_proxy = #{isProxy}, proxy_port = #{proxyPort}," +
            "image_path = #{imagePath}, proxy_address = #{proxyAddress}, description = #{description}, update_time = #{updateTime}")
    int updateSiteInfo(SiteInfo siteInfo);

    @Delete("DELETE FROM site_info WHERE site_id = #{siteId}")
    int deleteSiteInfoById(String siteId);
}
