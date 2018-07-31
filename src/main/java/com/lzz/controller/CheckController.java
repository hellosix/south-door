package com.lzz.controller;

import com.lzz.logic.ProxyLogic;
import com.lzz.logic.UrlLogic;
import com.lzz.model.Response;
import com.lzz.util.NetUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;

/**
 * Created by lzz on 2018/2/5.
 */
@Controller
@RequestMapping("/check")
public class CheckController {
    private static final Integer ERROR_CODE = 1;

    @Resource
    ProxyLogic proxyLogic;
    @Resource
    UrlLogic urlLogic;

    @RequestMapping(value="/url-model", method = RequestMethod.GET)
    @ResponseBody
    public Response checkUrlModel(@RequestParam String showName, @RequestParam String url,
                                  @RequestParam(name="proxyPort", defaultValue="0") int proxyPort){

        boolean res = true;
        res = urlLogic.checkShowName(showName);
        if( res ){
            return new Response(ERROR_CODE, "showName already exist", false);
        }
        res = NetUtil.checkUrl( url );
        if( !res ){
            return new Response(ERROR_CODE, "url is not use able", false);
        }
        if( proxyPort != 0 ){
            res = NetUtil.checkPort( proxyPort );
            if( !res ){
                return new Response(ERROR_CODE, "proxyPort is not can access", false);
            }
        }
        return Response.Success();
    }

    @RequestMapping(value="/proxy-model", method = RequestMethod.GET)
    @ResponseBody
    public Response checkProxyModel(@RequestParam int proxyPort, @RequestParam int port, @RequestParam String ip){
        boolean res = true;
        res = proxyLogic.checkProxyPort( proxyPort );
        if( res ){
            return new Response(ERROR_CODE, "proxy port is already exist", false);
        }
        res = NetUtil.checkIp(ip);
        if( !res ){
            return new Response(ERROR_CODE, "the ip is not can use able", false);
        }
        res = NetUtil.checkIpAndPort(ip, port);
        if( !res ){
            return new Response(ERROR_CODE, "the port is not can access", false);
        }
        return Response.Success();
    }

}
