package org.hellosix.south.door.proxy.http;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.hellosix.south.door.model.SiteInfo;
import org.hellosix.south.door.util.UrlUtil;
import org.mitre.dsmiley.httpproxy.ProxyServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServlet;
import java.net.MalformedURLException;

/**
 * @author Jay.H.Zou
 * @date 2019/6/27
 */
public class HttpProxyTask implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(HttpProxyTask.class);

    private SiteInfo siteInfo;

    private Server server;

    public HttpProxyTask(SiteInfo siteInfo) {
        this.siteInfo = siteInfo;
    }

    @Override
    public void run() {
        Integer proxyPort = siteInfo.getProxyPort();
        server = new Server(proxyPort);
        String address = siteInfo.getAddress();
        String baseUrl = UrlUtil.getBaseUrl(address);
        ServletContextHandler servletContextHandler = new ServletContextHandler(server, "/");
        HttpServlet proxyServlet = new ProxyServlet();
        ServletHolder servletHolder = new ServletHolder(proxyServlet);
        servletHolder.setInitParameter("targetUri", baseUrl);
        servletContextHandler.addServlet(servletHolder, "/*");
        try {
            server.start();
        } catch (Exception e) {
            logger.error("server start failed, site info: " + siteInfo, e);
            if (server != null) {
                try {
                    server.stop();
                } catch (Exception ex) {
                    logger.error("", ex);
                }
            }
        }
    }

    public void stop() {
        if (server != null) {
            try {
                server.stop();
            } catch (Exception e) {
                logger.error("stop server failed.", e);
            }
        }
    }

}
