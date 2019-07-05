package org.hellosix.south.door.conroller;

import org.apache.commons.lang3.StringUtils;
import org.hellosix.south.door.model.Response;
import org.hellosix.south.door.util.NetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author Jay.H.Zou
 * @date 7/5/2019
 */
@Controller
@RequestMapping("/validate/*")
public class ValidateController {

    private static final Logger logger = LoggerFactory.getLogger(ValidateController.class);

    @RequestMapping(value = "/siteName", method = RequestMethod.POST)
    @ResponseBody
    public Response validateSiteName(@RequestBody String siteName) {
        return Response.success();
    }

    @RequestMapping(value = "/address", method = RequestMethod.POST)
    @ResponseBody
    public Response validateAddress(@RequestBody String address) {
        boolean status = NetUtil.accessUrl(address);
        return status ? Response.success() : Response.fail();
    }

    @RequestMapping(value = "/proxyPort", method = RequestMethod.POST)
    @ResponseBody
    public Response validateProxyPort(@RequestBody Integer proxyPort) {
        if (proxyPort == null) {
            return Response.fail();
        }
        boolean status = NetUtil.accessPort(proxyPort);
        return status ? Response.fail() : Response.success();
        /*Socket socket = new Socket();
        try {
            InetAddress addr = InetAddress.getByName(server.getHost());
            socket.connect(new InetSocketAddress(addr, server.getPort()), 1000);
            return true;
        } catch (IOException e) {
            return false;
        } finally {
            try {
                if (socket != null)
                    socket.close();
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }*/
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
    }

    @RequestMapping(value = "/address", method = RequestMethod.POST)
    @ResponseBody
    public Response validateAdress(@RequestBody String address) {
        if (StringUtils.isBlank(address)) {
            return Response.fail();
        }
        return Response.success();
        /*Socket socket = new Socket();
        try {
            InetAddress addr = InetAddress.getByName(server.getHost());
            socket.connect(new InetSocketAddress(addr, server.getPort()), 1000);
            return true;
        } catch (IOException e) {
            return false;
        } finally {
            try {
                if (socket != null)
                    socket.close();
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }*/
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
    }
}