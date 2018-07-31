package com.lzz.util;

import org.dom4j.DocumentException;
import org.junit.Test;

import java.util.Map;

/**
 * Created by lzz on 2018/2/7.
 */
public class XmlUtilTest {
    @Test
    public void test001() throws Exception {
        XmlUtil xmlUtil = new XmlUtil("user.xml");
        xmlUtil.add("aaa111","cccc");
        XmlUtil xmlUtil2 = new XmlUtil("user2.xml");
        xmlUtil2.add("aaa2222","cccc");
    }

    @Test
    public void test002() throws DocumentException {
        XmlUtil xmlUtil1 = new XmlUtil("user.xml");
        Map map = xmlUtil1.getAllMap();
        System.out.println( map );
        XmlUtil xmlUtil2 = new XmlUtil("user2.xml");
        Map map2 = xmlUtil2.getAllMap();
        System.out.println( map2 );
    }

    @Test
    public void test003() throws DocumentException {
        XmlUtil xmlUtil1 = new XmlUtil("user.xml");
        xmlUtil1.remove("aaa111");
        XmlUtil xmlUtil2 = new XmlUtil("user2.xml");
        xmlUtil2.remove("aaa2222");
    }

    @Test
    public void test004() throws Exception {
        test001();
        test002();
        test003();
        test002();
    }

    @Test
    public void test005() throws InterruptedException {
        while (true){
            System.out.println( PropertiesUtil.propertiesMap );
            Thread.sleep(1000);
        }

        //String path = XmlUtil.class.getResource("/").toString();
        //System.out.println("path = " + path);
    }
}
