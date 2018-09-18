package cn.hellosix.common.controller;

import cn.hellosix.common.logic.ProxyLogic;
import cn.hellosix.common.model.Response;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Created by lzz on 2018/3/21.
 */
@Controller
public class BrowserController {
    @Resource
    ProxyLogic proxyLogic;

    @RequestMapping("/browser")
    public String index(Model model) {
        return "browser";
    }

    @RequestMapping("/browser/default")
    public String defaultLoading(Model model) {
        return "browser-loading";
    }

    @RequestMapping(value = "/tmp-proxy", method = RequestMethod.POST)
    @ResponseBody
    public Response tmpProxy(@RequestBody String urlObj) {
        JSONObject jsonObject = JSONObject.parseObject(urlObj);
        return proxyLogic.getProxyUrl(jsonObject.getString("url"));
    }
}
