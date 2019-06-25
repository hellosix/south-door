package org.hellosix.south.door.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.hellosix.south.door.model.SiteGroup;

import java.util.List;

/**
 * @author Jay.H.Zou
 * @date 2019/6/24
 */
public interface SiteGroupDao {

    /**
     * 判断表是否存在
     *
     * @return
     */
    @Select("SHOW TABLES LIKE 'site_group';")
    List isExistSiteGroupTable();

    @Select("SELECT * FROM site_group WHERE group_id = #{groupId}")
    SiteGroup selectGroupById(String groupId);

    @Select("SELECT * FROM site_group")
    List<SiteGroup> selectGroupList();

    @Insert("INSERT INFO site_group " +
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
