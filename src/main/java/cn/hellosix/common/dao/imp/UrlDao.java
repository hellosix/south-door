package cn.hellosix.common.dao.imp;

import cn.hellosix.common.dao.IUrlDao;
import cn.hellosix.common.model.UrlModel;
import cn.hellosix.common.util.XmlUtil;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by lzz on 2018/2/4.
 */
@Component
public class UrlDao implements IUrlDao {
    private static final String URL_DB = "url-list.xml";

    static {
        XmlUtil xmlUtil = new XmlUtil(URL_DB);
        UrlModel urlModel1 = new UrlModel("test1", "http://127.0.0.1:8081/index", 8070, "g1", null);
        UrlModel urlModel2 = new UrlModel("test2", "http://127.0.0.1:8081/index", null, "g2", null);
        UrlModel urlModel3 = new UrlModel("test3", "http://127.0.0.1:8081/index", null, "g2", null);
        xmlUtil.add("test1", urlModel1.serializa());
        xmlUtil.add("test2", urlModel2.serializa());
        xmlUtil.add("test3", urlModel3.serializa());
    }

    @Override
    public boolean add(UrlModel urlModel) {
        String showName = urlModel.getShowName();
        XmlUtil xmlUtil = new XmlUtil(URL_DB);
        boolean res = true;
        try {
            xmlUtil.add(showName, urlModel.serializa());
        } catch (Exception e) {
            res = false;
        }
        return res;
    }

    @Override
    public List<UrlModel> urlList() {
        XmlUtil xmlUtil = new XmlUtil(URL_DB);
        List<UrlModel> urlList = new ArrayList<>();
        Map<String, String> urlMap = xmlUtil.getAllMap();
        for (Map.Entry<String, String> element : urlMap.entrySet()) {
            String value = element.getValue();
            urlList.add(UrlModel.unSerializa(value));
        }
        return urlList;
    }

    @Override
    public boolean delete(String showName) {
        XmlUtil xmlUtil = new XmlUtil(URL_DB);
        boolean res = true;
        try {
            xmlUtil.remove(showName);
        } catch (Exception e) {
            res = false;
        }
        return res;
    }

    @Override
    public UrlModel getUrlModel(String showName) {
        XmlUtil xmlUtil = new XmlUtil(URL_DB);
        String res = xmlUtil.get(showName);
        return UrlModel.unSerializa(res);
    }
}
