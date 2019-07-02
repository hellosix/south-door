package org.hellosix.south.door.proxy.http;

import org.hellosix.south.door.model.SiteInfo;
import org.hellosix.south.door.proxy.ProxyManageAbstract;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Jay.H.Zou
 * @date 2019/6/27
 */
@Component
public class HttpProxyManage extends ProxyManageAbstract implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (maxProxySize < 20) {
            throw new RuntimeException("max proxy should be greater or equal 20");
        }
        proxySiteMap = new ConcurrentHashMap<>(maxProxySize);

        proxyThreadPool = new ThreadPoolExecutor(
                20,
                maxProxySize,
                60,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(1),
                new ThreadPoolExecutor.AbortPolicy());
    }

    @Override
    public void startProxyTasks() {
        List<SiteInfo> siteInfoList = siteInfoDao.selectSiteInfoList();
        if (siteInfoList != null && !siteInfoList.isEmpty()) {
            for (SiteInfo siteInfo : siteInfoList) {
                addProxyTask(siteInfo);
            }

        }
    }

    @Override
    public void addProxyTask(SiteInfo siteInfo) {
        if (siteInfo.getIsProxy()) {
            try {
                String siteId = siteInfo.getSiteId();
                Runnable httpProxyTask = proxySiteMap.get(siteId);
                if (httpProxyTask == null) {
                    httpProxyTask = new HttpProxyTask(siteInfo);
                    proxyThreadPool.submit(httpProxyTask);
                    proxySiteMap.put(siteId, httpProxyTask);
                }
            } catch (Exception e) {
                logger.error("start proxy task failed, site info: " + siteInfo, e);
            }
        }

    }

    @Override
    public void stopProxyTask(String siteId) {
        Runnable runnable = proxySiteMap.get(siteId);
        logger.info("stopping server socket...");
        SiteInfo siteInfo = null;
        try {
            if (runnable != null) {
                HttpProxyTask httpProxyTask = (HttpProxyTask) runnable;
                httpProxyTask.stop();
            }
            siteInfo = siteInfoDao.selectSiteInfoById(siteId);
            logger.info("stopping server socket...");
        } catch (Exception e) {
            logger.error("server socket closed failed. site info: " + siteInfo);
        }
    }

}
