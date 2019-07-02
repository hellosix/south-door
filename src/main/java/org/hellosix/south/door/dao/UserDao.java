package org.hellosix.south.door.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.hellosix.south.door.model.User;

/**
 * @author Jay.H.Zou
 * @date 2019/6/24
 */
@Mapper
public interface UserDao {

    @Select("SELECT * FROM user WHERE user_name = #{userName} AND password = #{password}")
    User selectUserByUserNameAndPassword(User user);

    @Select("SELECT * FROM user WHERE user_name = #{userName}")
    User selectUserByUserName(User user);

    @Insert("INSERT INTO user (user_id, user_name, password, head_pic, update_time) " +
            "VALUES (#{userId}, #{userName}, #{password}, #{headPic}, #{updateTime})")
    int insertUser(User user);

}
