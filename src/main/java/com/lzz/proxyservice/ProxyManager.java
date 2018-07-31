package com.lzz.proxyservice;

import com.lzz.dao.imp.ProxyDao;
import com.lzz.model.ProxyModel;
import com.lzz.util.NetUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by lzz on 2017/9/11.
 */
@Component
public class ProxyManager {
    private transient static Log log = LogFactory.getLog(ProxyManager.class);
    public static ExecutorService threadPool = Executors.newCachedThreadPool();
    public static Map<String, Integer> tmpProxyMap = new ConcurrentHashMap<>();
    private static Map<Integer, ProxyThread> proxyThreadMap = new ConcurrentHashMap<>();

    @Scheduled(fixedRate = 5000)
    public void scheduleRun() throws InterruptedException {
        stopProxyThread();
        startProxyThread();
    }

    private void startProxyThread() {
        Map<Integer, ProxyModel> metaMap = ProxyDao.getProxyMeta();
        for(Map.Entry<Integer, ProxyModel> meta : metaMap.entrySet()){
            Integer proxyPort = meta.getKey();
            ProxyModel proxyModel = meta.getValue();
            ProxyThread proxyThread = proxyThreadMap.get( proxyPort );
            if( null == proxyThread ){
                proxyThread = new ProxyThread(proxyModel, true);
                threadPool.submit( proxyThread );
                proxyThreadMap.put(proxyPort, proxyThread);
            }
        }
    }

    private void stopProxyThread() {
        Map<Integer, ProxyModel> metaMap = ProxyDao.getProxyMeta();
        for(Map.Entry<Integer, ProxyThread> meta : proxyThreadMap.entrySet()){
            Integer proxyPort = meta.getKey();
            ProxyThread proxyThread = meta.getValue();
            ProxyModel proxyModel = metaMap.get( proxyPort );
            if( null == proxyModel ){
                try {
                    System.out.println("stop thread :" + proxyPort);
                    proxyThread.stop();
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    proxyThreadMap.remove( proxyPort );
                }
            }
        }
    }

    public static boolean startTmpProxy(ProxyModel proxyModel, boolean threadStart, boolean istmp){
        int proxyPort = proxyModel.getProxyPort();
        if( proxyPort == 0){
            for(Map.Entry<Integer, ProxyThread> entry : proxyThreadMap.entrySet() ){
                Integer tmpPort = entry.getKey();
                if( tmpPort > proxyPort ){
                    proxyPort = tmpPort;
                }
            }
            proxyPort++; // 比最大的加一
            while ( NetUtil.checkPort( proxyPort ) ){
                proxyPort++;
            }
            proxyModel.setProxyPort( proxyPort );
        }
        ProxyThread proxyThread = new ProxyThread(proxyModel, threadStart, istmp);
        threadPool.submit( proxyThread );
        String host = proxyModel.getIp() + ":" + proxyModel.getPort();
        tmpProxyMap.put( host, proxyPort );
        return true;
    }
}