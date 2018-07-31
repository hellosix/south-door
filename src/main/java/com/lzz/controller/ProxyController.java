package com.lzz.controller;

import com.lzz.logic.ProxyLogic;
import com.lzz.model.ProxyModel;
import com.lzz.model.Response;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Created by lzz on 2018/2/4.
 */
@Controller
public class ProxyController {
    @Resource
    ProxyLogic logic;

    @RequestMapping("/proxylist")
    public String proxyList(Model model) {
        return "proxylist";
    }

    @RequestMapping(value="/add-proxy", method = RequestMethod.POST)
    @ResponseBody
    public Response addProxy(@RequestBody ProxyModel proxyModel){
        return logic.addProxy(proxyModel);
    }

    @RequestMapping(value="/remove-proxy", method = RequestMethod.GET)
    @ResponseBody
    public Response removeProxy(@RequestParam int proxyPort){
        return logic.removeProxy(proxyPort);
    }

    @RequestMapping(value="/proxy-list", method = RequestMethod.GET)
    @ResponseBody
    public Response proxyList(){
        return logic.proxyList();
    }
}
