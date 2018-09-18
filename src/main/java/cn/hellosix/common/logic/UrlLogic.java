package cn.hellosix.common.logic;

import cn.hellosix.common.dao.IUrlDao;
import cn.hellosix.common.model.Response;
import cn.hellosix.common.model.ShowUrlModel;
import cn.hellosix.common.model.UrlModel;
import cn.hellosix.common.util.NetUtil;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by lzz on 2018/2/4.
 */
@Component
public class UrlLogic {
    private static ExecutorService threadPool = Executors.newCachedThreadPool();

    @Resource
    IUrlDao urlDao;

    public static String getShowUrl(Integer proxyPort, String url) {
        if (null == proxyPort || StringUtils.isEmpty(url) || !url.contains(":")) {
            return url;
        }
        String showUrl = url;
        if (proxyPort > 0) {
            try {
                String[] tmpArr = url.split("//");
                if (tmpArr.length >= 2) {
                    String httpProxy = tmpArr[0];
                    String fullPath = tmpArr[1];
                    if (fullPath.contains(":")) {
                        String path = fullPath.substring(fullPath.indexOf(":"));
                        String ip = NetUtil.getLocalIp();
                        showUrl = httpProxy + "//" + ip;
                        showUrl += ":" + proxyPort;
                        if (path.indexOf("/") > -1) {
                            path = path.substring(path.indexOf("/"));
                            showUrl += path;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return showUrl;
    }

    private static String getDomain(String url) {
        if (StringUtils.isEmpty(url)) {
            return url;
        }
        String[] tmpArr = url.split("//");
        if (tmpArr.length == 2) {
            String httpProxy = tmpArr[0];
            String urlPath = tmpArr[1];
            urlPath = urlPath.split("/")[0];
            url = httpProxy + "//" + urlPath;
        }
        return url;
    }

    public static void main(String[] args) {
        String url = "http://127.0.0.2:121/fdsa";
        System.out.println(getShowUrl(9088, url));
    }

    public Response addUrl(UrlModel urlModel) {
        Response response = Response.Fail();
        if (urlDao.add(urlModel) == true) {
            String showName = urlModel.getShowName();
            String url = urlModel.getUrl();
            threadPool.submit(new ImageTask(showName, url));
            response = Response.Success();
        }
        return response;
    }

    public Response removeUrl(String showName) {
        return urlDao.delete(showName) == true ? Response.Success() : Response.Fail();
    }

    public Response urlList() {
        List<UrlModel> urlList = urlDao.urlList();
        return new Response(0, "success", urlList);
    }

    public Response urlMap() {
        List<UrlModel> urlList = urlDao.urlList();
        Map<String, List<ShowUrlModel>> res = new HashMap<>();
        for (UrlModel urlModel : urlList) {
            String group = urlModel.getGroup();
            List<ShowUrlModel> tmpList = res.get(group);
            if (tmpList == null) {
                tmpList = new ArrayList<>();
            }
            tmpList.add(new ShowUrlModel(urlModel, getDomain(urlModel.getUrl()), getShowUrl(urlModel.getProxyPort(), urlModel.getUrl())));
            res.put(group, tmpList);
        }
        return new Response(0, "success", res);
    }

    public boolean checkShowName(String showName) {
        UrlModel urlModel = urlDao.getUrlModel(showName);
        boolean res = false;
        if (null != urlModel) {
            res = true;
        }
        return res;
    }

    public Set<String> urlGroup() {
        Set<String> urlGroup = new HashSet<>();
        List<UrlModel> urlList = urlDao.urlList();
        for (int i = 0; i < urlList.size(); i++) {
            UrlModel urlModel = urlList.get(i);
            urlGroup.add(urlModel.getGroup());
        }
        return urlGroup;
    }
}
