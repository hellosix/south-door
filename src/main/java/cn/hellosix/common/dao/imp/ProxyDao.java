package cn.hellosix.common.dao.imp;

import cn.hellosix.common.dao.IProxyDao;
import cn.hellosix.common.model.ProxyModel;
import cn.hellosix.common.util.XmlUtil;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by lzz on 2018/2/4.
 */
@Component
public class ProxyDao implements IProxyDao {
    private static final String URL_DB = "proxy-list.xml";
    public static Map<Integer, ProxyModel> proxyMap = new ConcurrentHashMap<>();

    static {
        XmlUtil xmlUtil = new XmlUtil(URL_DB);
        ProxyModel proxyModel1 = new ProxyModel(8071, 8081, "127.0.0.1");
        ProxyModel proxyModel2 = new ProxyModel(8072, 8081, "127.0.0.1");
        xmlUtil.add(String.valueOf(proxyModel1.getProxyPort()), proxyModel1.serialized());
        xmlUtil.add(String.valueOf(proxyModel2.getProxyPort()), proxyModel2.serialized());
    }

    public static Map<Integer, ProxyModel> getProxyMeta() {
        XmlUtil xmlUtil = new XmlUtil(URL_DB);
        Map<String, String> allProxy = xmlUtil.getAllMap();
        Map<Integer, ProxyModel> resMap = new HashMap<>();
        for (Map.Entry<String, String> element : allProxy.entrySet()) {
            Integer key = Integer.valueOf(element.getKey());
            String value = element.getValue();
            resMap.put(key, ProxyModel.unSerialized(value));
        }
        return resMap;
    }

    @Override
    public boolean add(ProxyModel proxyModel) {
        XmlUtil xmlUtil = new XmlUtil(URL_DB);
        boolean res = true;
        try {
            res = xmlUtil.add(String.valueOf(proxyModel.getProxyPort()), proxyModel.serialized());
        } catch (Exception e) {
            res = false;
        }
        return res;
    }

    @Override
    public boolean remove(int proxyPort) {
        XmlUtil xmlUtil = new XmlUtil(URL_DB);
        boolean res = true;
        try {
            res = xmlUtil.remove(String.valueOf(proxyPort));
        } catch (Exception e) {
            res = false;
        }
        return res;
    }

    @Override
    public List<ProxyModel> proxyList() {
        List<ProxyModel> resList = new ArrayList<>();
        Map<Integer, ProxyModel> allMap = getProxyMeta();
        for (Map.Entry<Integer, ProxyModel> element : allMap.entrySet()) {
            ProxyModel proxyModel = element.getValue();
            resList.add(proxyModel);
        }
        return resList;
    }

    @Override
    public ProxyModel getProxyPort(Integer port) {
        XmlUtil xmlUtil = new XmlUtil(URL_DB);
        String proxyStr = xmlUtil.get(String.valueOf(port));
        return ProxyModel.unSerialized(proxyStr);
    }
}
