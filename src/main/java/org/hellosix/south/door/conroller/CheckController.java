package org.hellosix.south.door.conroller;

import org.hellosix.south.door.model.Response;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author lzz
 * @date 2018/2/5.
 */
@Controller
@RequestMapping("/check")
public class CheckController {
    private static final Integer ERROR_CODE = 1;


    @RequestMapping(value = "/proxy-model", method = RequestMethod.GET)
    @ResponseBody
    public Response checkProxyModel(@RequestParam int proxyPort, @RequestParam int port, @RequestParam String ip) {
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
