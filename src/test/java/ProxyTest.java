import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.mitre.dsmiley.httpproxy.ProxyServlet;

import javax.servlet.http.HttpServlet;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Jay.H.Zou
 * @date 6/26/2019
 */
public class ProxyTest {

    public static void main(String[] args) throws Exception {
        ExecutorService proxyThreadPool = new ThreadPoolExecutor(
                20,
                20,
                60,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(1),
                new ThreadPoolExecutor.AbortPolicy());
        proxyThreadPool.submit(createProxy(8011, "http://172.16.41.118:8282/"));
        proxyThreadPool.submit(createProxy(8012, "http://172.16.35.219:8086/"));
    }

    private static Thread createProxy(int proxyPort, String baseUrl) throws Exception {

        return new Thread(new Runnable() {

            @Override
            public void run() {
                final Server server = new Server(proxyPort);
                ServletContextHandler servletContextHandler = new ServletContextHandler(server, "/");

                HttpServlet proxyServlet = new ProxyServlet();
                ServletHolder servletHolder = new ServletHolder();
                servletHolder.setServlet(proxyServlet);
                servletHolder.setInitParameter("targetUri", baseUrl);
                servletContextHandler.addServlet(servletHolder, "/*");
                try {
                    server.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.err.println("=======================");
            }
        });
    }
}
