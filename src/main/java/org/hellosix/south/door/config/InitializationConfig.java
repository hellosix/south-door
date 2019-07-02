package org.hellosix.south.door.config;

import org.hellosix.south.door.dao.TableInitializationDao;
import org.hellosix.south.door.service.impl.UserService;
import org.hellosix.south.door.util.NetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

import java.net.UnknownHostException;


/**
 * @author Jay.H.Zou
 * @date 2019/6/27
 */
@Configuration
public class InitializationConfig implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger logger = LoggerFactory.getLogger(InitializationConfig.class);

    @Autowired
    private TableInitializationDao tableInitializationDao;

    @Autowired
    private UserService userService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        logger.info("initialize base data...");
        initTables();
        initAdminUser();
        logger.info("initialize successfully!");
        try {
            NetUtil.LOCAL_IP = NetUtil.getLocalIp();
            logger.info("local ip is: " + NetUtil.LOCAL_IP);
        } catch (UnknownHostException e) {
            logger.error("get local ip failed.", e);
            throw new RuntimeException("get local ip failed");
        }
    }

    private void initAdminUser() {
        userService.createAdminUser();
    }

    private void initTables() {
        tableInitializationDao.createUserTable();

        tableInitializationDao.createSiteGroupTable();

        tableInitializationDao.createSiteInfoTable();
    }
}
