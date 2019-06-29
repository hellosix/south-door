package org.hellosix.south.door.dao;

import org.apache.ibatis.annotations.*;
import org.hellosix.south.door.model.SiteGroup;

import java.util.List;

/**
 * @author Jay.H.Zou
 * @date 2019/6/24
 */
@Mapper
public interface SiteGroupDao {

    @Select("SELECT * FROM site_group WHERE group_id = #{groupId}")
    SiteGroup selectGroupById(String groupId);

    @Select("SELECT * FROM site_group WHERE group_name = #{groupName}")
    SiteGroup selectGroupByName(String groupName);

    @Select("SELECT * FROM site_group")
    List<SiteGroup> selectGroupList();

    @Insert("INSERT INTO site_group " +
            "(group_id, group_name, description, updateTime) " +
            "VALUES (#{groupId}, #{groupName}, #{description}, #{updateTime})")
    int insertSiteGroup(SiteGroup siteGroup);

    @Update("UPDATE site_group" +
            "SET group_name = #{groupName}, description = #{description}" +
            "WHERE group_id =  #{groupId}")
    int updateSiteGroup(SiteGroup siteGroup);


    @Delete("DELETE FROM site_group WHERE group_id = #{groupId}")
    int deleteSiteGroupById(String groupId);
}
