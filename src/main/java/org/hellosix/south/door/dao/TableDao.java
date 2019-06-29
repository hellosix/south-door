package org.hellosix.south.door.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Jay.H.Zou
 * @date 2019/6/27
 */
@Mapper
public interface TableDao {

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
