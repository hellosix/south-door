package org.hellosix.south.door.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.hellosix.south.door.model.User;

/**
 * @author Jay.H.Zou
 * @date 2019/6/24
 */
public interface UserDao {

    @Select("SELECT * FROM user WHERE user_id = #{userId}")
    User selectUserById(String userId);

    @Insert("INSERT INFO user (user_id, user_name, password, head_pic)" +
            "VALUES (#{userId}, #{userName}, #{password}, #{headPic})")
    int insertUser(User user);

}
