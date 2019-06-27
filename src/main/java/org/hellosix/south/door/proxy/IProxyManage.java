package org.hellosix.south.door.proxy;

import org.hellosix.south.door.model.SiteInfo;

/**
 * @author Jay.H.Zou
 * @date 2019/6/27
 */
public interface IProxyManage {

    void startProxyTasks();

    void addProxyTask(SiteInfo siteInfo);

    void stopProxyTask(String siteId);

}
