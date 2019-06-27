package org.hellosix.south.door.dao;

import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Jay.H.Zou
 * @date 2019/6/27
 */
public interface TableDao {

    /**
     * 判断 site_group 表是否存在
     *
     * @return
     */
    @Select("SHOW TABLES LIKE 'site_group';")
    List isExistSiteGroupTable();

    /**
     * 判断 site_info 表是否存在
     *
     * @return
     */
    @Select("SHOW TABLES LIKE 'site_info';")
    List isExistSiteInfoTable();

    /**
     * 判断 user 表是否存在
     *
     * @return
     */
    @Select("SHOW TABLES LIKE 'user';")
    List isExistUserTable();

    @Select("CREATE TABLE IF NOT EXISTS site_group (" +
            "group_id varchar(50) NOT NULL," +
            "group_name varchar(255) NOT NULL," +
            "description varchar(255) DEFAULT NULL," +
            "update_time datetime NOT NULL," +
            "PRIMARY KEY (group_id)" +
            ")ENGINE=InnoDB;")
    void createSiteGroupTable();

    @Select("CREATE TABLE IF NOT EXISTS site_info (" +
            "site_id varchar(50) NOT NULL," +
            "site_name varchar(255) NOT NULL," +
            "address varchar(255) NOT NULL," +
            "is_proxy bit DEFAULT 0," +
            "proxy_port MEDIUMINT UNSIGNED DEFAULT NULL," +
            "image_path varchar(255) NOT NULL," +
            "proxy_address varchar(255) NOT NULL," +
            "description varchar(255) DEFAULT NULL," +
            "update_time datetime NOT NULL," +
            "PRIMARY KEY (site_id)" +
            ")ENGINE=InnoDB;")
    void createSiteInfoTable();

    @Select("CREATE TABLE IF NOT EXISTS user (" +
            "user_id varchar(50) NOT NULL," +
            "user_name varchar(255) NOT NULL," +
            "password varchar(255) NOT NULL," +
            "head_pic varchar(255) NOT NULL," +
            "update_time datetime NOT NULL," +
            "PRIMARY KEY (user_id)" +
            ")ENGINE=InnoDB;")
    void createUserTable();
}
