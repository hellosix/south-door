package org.hellosix.south.door.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.hellosix.south.door.model.SiteInfo;

import java.util.List;

/**
 * @author Jay.H.Zou
 * @date 2019/6/24
 */
public interface SiteInfoDao {

    /**
     * 判断表是否存在
     *
     * @return
     */
    @Select("SHOW TABLES LIKE 'site_info';")
    List isExistSiteInfoTable();

    @Select("SELECT * FROM site_info WHERE site_id = #{siteId}")
    SiteInfo selectSiteInfoById(String siteId);

    @Select("SELECT * FROM site_info")
    List<SiteInfo> selectSiteInfoList();

    @Select("SELECT * FROM site_info WHERE group_id = #{groupId}")
    List<SiteInfo> selectSiteInfoListByGroupId(String groupId);

    @Insert("INSERT INFO site_info " +
            "(site_id, site_name, address, is_proxy, proxy_port, image_path, proxy_address, description, update_time)" +
            "VALUES (#{siteId}, #{siteName}, #{address}, #{isProxy}, #{proxyPort}, #{imagePath}, #{proxyAddress}, #{description}, #{updateTime})")
    int insertSiteInfo(SiteInfo siteInfo);

    @Update("UPDATE site_info SET site_nam = #{siteName}, address = #{address}, is_proxy = #{isProxy}, proxy_port = #{proxyPort}," +
            "image_path = #{imagePath}, proxy_address = #{proxyAddress}, description = #{description}, update_time = #{updateTime}")
    int updateSiteInfo(SiteInfo siteInfo);

    @Delete("DELETE FROM site_info WHERE site_id = #{siteId}")
    int deleteSiteInfoById(String siteId);
}
