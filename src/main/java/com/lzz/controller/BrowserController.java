package com.lzz.controller;

import com.lzz.logic.ProxyLogic;
import com.lzz.model.Response;
import net.sf.json.JSONObject;
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

    @RequestMapping(value="/tmp-proxy", method = RequestMethod.POST)
    @ResponseBody
    public Response tmpProxy(@RequestBody String urlObj){
        JSONObject jsonObject = JSONObject.fromObject( urlObj );
        return proxyLogic.getProxyUrl( jsonObject.getString("url") );
    }
}
