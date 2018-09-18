package cn.hellosix.common.controller;

import cn.hellosix.common.logic.UrlLogic;
import cn.hellosix.common.model.Response;
import cn.hellosix.common.model.UrlModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Set;

/**
 * Created by lzz on 2018/2/4.
 */
@Controller
public class UrlController {
    @Resource
    UrlLogic logic;

    @RequestMapping("/index")
    public String index(Model model) {
        return "index";
    }

    @RequestMapping("/urllist")
    public String urlList(Model model) {
        return "urllist";
    }

    @RequestMapping(value = "/add-url", method = RequestMethod.POST)
    @ResponseBody
    public Response addUrl(@RequestBody UrlModel urlModel) {
        return logic.addUrl(urlModel);
    }

    @RequestMapping(value = "/save-url", method = RequestMethod.POST)
    @ResponseBody
    public Response saveUrl(@RequestBody UrlModel urlModel) {
        return null;
    }

    @RequestMapping(value = "/remove-url", method = RequestMethod.GET)
    @ResponseBody
    public Response removeUrl(@RequestParam String showName) {
        return logic.removeUrl(showName);
    }

    @RequestMapping(value = "/url-list", method = RequestMethod.GET)
    @ResponseBody
    public Response urlList() {
        return logic.urlList();
    }

    @RequestMapping(value = "/url-map", method = RequestMethod.GET)
    @ResponseBody
    public Response urlMap() {
        return logic.urlMap();
    }

    @RequestMapping(value = "/url-group", method = RequestMethod.GET)
    @ResponseBody
    public Set<String> groupSet() {
        return logic.urlGroup();
    }
}
