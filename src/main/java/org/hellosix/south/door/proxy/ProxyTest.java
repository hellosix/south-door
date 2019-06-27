package org.hellosix.south.door.proxy;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.mitre.dsmiley.httpproxy.ProxyServlet;

import javax.servlet.http.HttpServlet;

/**
 * @author Jay.H.Zou
 * @date 6/26/2019
 */
public class ProxyTest {

    public static void main(String[] args) throws Exception {
        createProxy(8011, "http://127.0.0.1:8081/");
        createProxy(8012, "http://172.16.35.219:8083/");
    }

    private static void createProxy(int proxyPort, String baseUrl) throws Exception {
        Server server = new Server(proxyPort);
        ServletContextHandler servletContextHandler = new ServletContextHandler(server, "/");

        HttpServlet proxyServlet = new ProxyServlet();
        ServletHolder servletHolder = new ServletHolder();
        servletHolder.setServlet(proxyServlet);
        servletHolder.setInitParameter("targetUri", baseUrl);
        servletContextHandler.addServlet(servletHolder, "/*");
        server.start();
    }
}
