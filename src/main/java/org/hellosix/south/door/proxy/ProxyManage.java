package org.hellosix.south.door.proxy;

import org.hellosix.south.door.dao.SiteInfoDao;
import org.hellosix.south.door.model.SiteInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * 管理所有代理网站
 *
 * @author lzz, Jay.H.Zou
 * @date 2019/6/25
 */
@Component
public class ProxyManage implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger logger = LoggerFactory.getLogger(ProxyManage.class);

    /**
     * max proxy site number
     */
    @Value("${south-door.max-proxy-size}")
    private int maxProxySize;

    private ExecutorService proxyThreadPool;

    private ExecutorService transDataThreadPool;

    /**
     * siteId, thread
     */
    private Map<String, ProxyTask> proxySiteMap;

    @Autowired
    private SiteInfoDao siteInfoDao;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        proxySiteMap = new ConcurrentHashMap<>(maxProxySize);

        proxyThreadPool = new ThreadPoolExecutor(
                maxProxySize,
                maxProxySize,
                60,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(100),
                new ThreadPoolExecutor.AbortPolicy());

        transDataThreadPool = new ThreadPoolExecutor(
                maxProxySize,
                maxProxySize,
                60,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(100),
                new ThreadPoolExecutor.AbortPolicy());
    }

    @Scheduled(fixedRate = 5000)
    public void checkProxyThreads() throws InterruptedException {
        startProxyThread();
    }

    private void startProxyThread() {
        List<SiteInfo> siteInfoList = siteInfoDao.selectSiteInfoList();
        for (SiteInfo siteInfo : siteInfoList) {
            String siteId = siteInfo.getSiteId();
            ProxyTask proxyTask = proxySiteMap.get(siteId);
            if (proxyTask == null) {
                proxyTask = new ProxyTask(transDataThreadPool, siteInfo, true);
                proxyThreadPool.submit(proxyTask);
                proxySiteMap.put(siteId, proxyTask);
            }
        }
    }

    public boolean stopProxyThread(SiteInfo siteInfo) {
        String siteId = siteInfo.getSiteId();
        ProxyTask proxyTask = proxySiteMap.get(siteId);
        // logger.info("stop siteInfo.getSiteName ")
        try {

        } catch (Exception e) {

        } finally {

        }
        return false;
    }

    public boolean addTransDataTask(TransDataTask transDataTask) {
        try {
            transDataThreadPool.submit(transDataTask);
            return true;
        } catch (Exception e) {

        }
        return false;

    }

    /*private void stopProxyThread() {
        List<SiteInfo> siteInfoList = siteInfoDao.selectSiteInfoList();
        Map<Integer, ProxyModel> metaMap = ProxyDao.getProxyMeta();
        for (Map.Entry<Integer, ProxyThread> meta : proxyThreadMap.entrySet()) {
            Integer proxyPort = meta.getKey();
            ProxyThread proxyThread = meta.getValue();
            ProxyModel proxyModel = metaMap.get(proxyPort);
            if (null == proxyModel) {
                try {
                    System.out.println("stop thread :" + proxyPort);
                    proxyThread.stop();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    proxyThreadMap.remove(proxyPort);
                }
            }
        }
    }*/

}
