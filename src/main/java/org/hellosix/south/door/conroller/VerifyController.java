package org.hellosix.south.door.conroller;

import org.hellosix.south.door.model.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author lzz
 * @date 2018/2/5.
 */
@Controller
@RequestMapping("/verify/*")
public class VerifyController {

    private static final Logger logger = LoggerFactory.getLogger(VerifyController.class);


    @RequestMapping(value = "/proxy-model", method = RequestMethod.GET)
    @ResponseBody
    public Response checkSiteInfo(@RequestParam int proxyPort, @RequestParam int port, @RequestParam String ip) {
        /*boolean res = true;
        res = proxyLogic.checkProxyPort(proxyPort);
        if (res) {
            return new Response(ERROR_CODE, "proxy port is already exist", false);
        }
        res = NetUtil.checkIp(ip);
        if (!res) {
            return new Response(ERROR_CODE, "the ip is not can use able", false);
        }
        res = NetUtil.checkIpAndPort(ip, port);
        if (!res) {
            return new Response(ERROR_CODE, "the port is not can access", false);
        }*/
        return Response.success();
    }

}
