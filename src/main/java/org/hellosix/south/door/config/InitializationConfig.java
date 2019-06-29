package org.hellosix.south.door.config;

import org.hellosix.south.door.dao.TableDao;
import org.hellosix.south.door.service.impl.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.List;

/**
 * @author Jay.H.Zou
 * @date 2019/6/27
 */
@Configuration
public class InitializationConfig implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger logger = LoggerFactory.getLogger(InitializationConfig.class);

    @Autowired
    private TableDao tableDao;

    @Autowired
    private UserService userService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        logger.info("initialize base data...");
        initAdminUser();
        initTables();
        logger.info("initialize successfully!");
    }

    private void initAdminUser() {
        userService.createAdminUser();
    }

    private void initTables() {
        tableDao.createUserTable();

        tableDao.createSiteGroupTable();

        tableDao.createSiteInfoTable();
    }
}
