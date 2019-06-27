package org.hellosix.south.door.proxy.tcp;

import org.hellosix.south.door.dao.SiteInfoDao;
import org.hellosix.south.door.model.SiteInfo;
import org.hellosix.south.door.proxy.ProxyManageAbstract;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.List;
import java.util.concurrent.*;

/**
 * 管理所有代理网站
 *
 * @author lzz, Jay.H.Zou
 * @date 2019/6/25
 */
// @Component
public class TcpProxyManage extends ProxyManageAbstract implements ApplicationListener<ContextRefreshedEvent> {

    private ExecutorService transDataThreadPool;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
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

        transDataThreadPool = new ThreadPoolExecutor(
                20,
                maxProxySize,
                60,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(100),
                new ThreadPoolExecutor.AbortPolicy());
    }

    @Override
    public void startProxyTasks() {
        List<SiteInfo> siteInfoList = siteInfoDao.selectSiteInfoList();
        for (SiteInfo siteInfo : siteInfoList) {
            try {
                addProxyTask(siteInfo);
            } catch (Exception e) {
                logger.error("start " + siteInfo.getSiteName() + " proxy thread failed, proxy port: " + siteInfo.getProxyPort());
            }
        }
    }

    @Override
    public void addProxyTask(SiteInfo siteInfo) {
        if (siteInfo.getIsProxy()) {
            String siteId = siteInfo.getSiteId();
            TcpProxyTask tcpProxyTask = (TcpProxyTask) proxySiteMap.get(siteId);
            if (tcpProxyTask == null) {
                tcpProxyTask = new TcpProxyTask(transDataThreadPool, siteInfo, true);
                proxyThreadPool.submit(tcpProxyTask);
                proxySiteMap.put(siteId, tcpProxyTask);
            }
        }
    }

    @Override
    public void stopProxyTask(String siteId) {
        TcpProxyTask tcpProxyTask = (TcpProxyTask) proxySiteMap.get(siteId);
        logger.info("stopping server socket...");
        SiteInfo siteInfo = null;
        try {
            siteInfo = siteInfoDao.selectSiteInfoById(siteId);
            tcpProxyTask.stop();
            logger.info("stop proxy thread for site " + siteInfo.getSiteName());
        } catch (Exception e) {
            logger.error("server socket closed failed. site info: " + siteInfo);
        }
    }


}
