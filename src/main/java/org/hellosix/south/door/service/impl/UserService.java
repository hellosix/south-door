package org.hellosix.south.door.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.hellosix.south.door.dao.UserDao;
import org.hellosix.south.door.model.User;
import org.hellosix.south.door.service.IUserService;
import org.hellosix.south.door.util.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author Jay.H.Zou
 * @date 6/29/2019
 */
@Service
public class UserService implements IUserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserDao userDao;

    @Value("${south-door.admin.user-name}")
    private String adminUserName;

    @Value("${south-door.admin.password}")
    private String addminPassword;

    @Override
    public User getUserByNameAndPassword(User user) {
        if (user == null || StringUtils.isBlank(user.getUserName()) || StringUtils.isBlank(user.getPassword())) {
            return null;
        }
        try {
            User existUser = userDao.selectUserByUserNameAndPassword(user);
            return existUser;
        } catch (Exception e) {
            logger.error("get user failed, " + user, e);
            return null;
        }
    }

    @Override
    public User getUserByName(User user) {
        if (user == null || StringUtils.isBlank(user.getUserName())) {
            return null;
        }
        try {
            User existUser = userDao.selectUserByUserName(user);
            return existUser;
        } catch (Exception e) {
            logger.error("get user failed, " + user, e);
            return null;
        }
    }

    @Override
    public boolean createAdminUser() {
        User user = new User();
        user.setUserName(adminUserName);
        user.setPassword(addminPassword);
        if (getUserByName(user) != null) {
            logger.info(user.getUserName() + " exist.");
            return true;
        }
        user.setUserId(CommonUtil.getUUID());
        user.setHeadPic("/images/user/default.png");
        user.setUpdateTime(CommonUtil.getCurrentTimestamp());
        try {
            userDao.insertUser(user);
            return true;
        } catch (Exception e) {
            logger.error("init admin user failed.", e);
        }
        return false;
    }
}
