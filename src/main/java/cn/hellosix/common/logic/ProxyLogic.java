package cn.hellosix.common.logic;

import cn.hellosix.common.dao.IProxyDao;
import cn.hellosix.common.model.ProxyModel;
import cn.hellosix.common.model.Response;
import cn.hellosix.common.proxyservice.ProxyManager;
import cn.hellosix.common.util.NetUtil;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by lzz on 2018/2/4.
 */
@Component
public class ProxyLogic {
    @Resource
    IProxyDao proxyDao;


    public Response addProxy(ProxyModel proxyModel) {
        return proxyDao.add(proxyModel) == true ? Response.Success() : Response.Fail();
    }

    public Response removeProxy(Integer proxyPort) {
        return proxyDao.remove(proxyPort) == true ? Response.Success() : Response.Fail();
    }

    public Response proxyList() {
        List<ProxyModel> proxyList = proxyDao.proxyList();
        return new Response(0, "success", proxyList);
    }

    public boolean checkProxyPort(Integer port) {
        ProxyModel proxyModel = proxyDao.getProxyPort(port);
        boolean res = false;
        if (null != proxyModel) {
            res = true;
        } else {
            res = NetUtil.checkPort(port);
        }
        return res;
    }


    public Response getProxyUrl(String url) {
        Response res = null;
        String showUrl = url;
        if (!StringUtils.isEmpty(url)) {
            try {
                String[] tmpArr = url.split("//");
                String host = tmpArr[0];
                if (tmpArr.length >= 2) {
                    host = tmpArr[1];
                }
                host = host.split("/")[0];
                String ip = host.split(":")[0];
                int port = Integer.parseInt(host.split(":")[1]);
                System.out.println("host :" + host + " ip:" + ip + " port:" + port + " tmpProxyMap:" + ProxyManager.tmpProxyMap.size());
                Integer proxyPort = ProxyManager.tmpProxyMap.get(host);
                if (null == proxyPort) {
                    ProxyModel proxyModel = new ProxyModel(0, port, ip);
                    if (ProxyManager.startTmpProxy(proxyModel, true, true)) {
                        proxyPort = proxyModel.getProxyPort();
                    }
                }
                showUrl = UrlLogic.getShowUrl(proxyPort, url);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        res = new Response(0, "", showUrl);
        return res;
    }
}
