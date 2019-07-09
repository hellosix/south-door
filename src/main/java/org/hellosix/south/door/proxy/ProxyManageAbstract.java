package org.hellosix.south.door.proxy;

import org.hellosix.south.door.dao.SiteInfoDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Map;
import java.util.concurrent.ExecutorService;

/**
 * @author Jay.H.Zou
 * @date 2019/6/27
 */
public abstract class ProxyManageAbstract implements IProxyManage {

    protected static final Logger logger = LoggerFactory.getLogger(ProxyManageAbstract.class);

    /**
     * max proxy site number
     */
    @Value("${south-door.max-proxy-size}")
    protected int maxProxySize;

    @Autowired
    protected SiteInfoDao siteInfoDao;

    protected ExecutorService proxyThreadPool;

    /**
     * siteId, thread
     */
    protected Map<String, Runnable> proxySiteMap;

    @Scheduled(initialDelay = 10000, fixedRate = 5000)
    public void checkProxyTasks() {
        try {
            startProxyTasks();
        } catch (Exception e) {
            logger.error("The scheduled task failed to start.", e);
        }

    }


}
