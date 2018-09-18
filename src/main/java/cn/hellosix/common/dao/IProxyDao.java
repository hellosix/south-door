package cn.hellosix.common.dao;

import cn.hellosix.common.model.ProxyModel;

import java.util.List;

/**
 * Created by lzz on 2018/2/4.
 */
public interface IProxyDao {
    boolean add(ProxyModel proxyModel);

    boolean remove(int proxyPort);

    List<ProxyModel> proxyList();

    ProxyModel getProxyPort(Integer port);
}
