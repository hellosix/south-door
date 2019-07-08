package org.hellosix.south.door.conroller;

import org.hellosix.south.door.model.Response;
import org.hellosix.south.door.model.SiteGroup;
import org.hellosix.south.door.model.SiteInfo;
import org.hellosix.south.door.service.ISiteGroupService;
import org.hellosix.south.door.service.ISiteInfoService;
import org.hellosix.south.door.util.NetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Jay.H.Zou
 * @date 7/5/2019
 */
@Controller
@RequestMapping("/validate/*")
public class ValidateController {

    private static final Logger logger = LoggerFactory.getLogger(ValidateController.class);

    @Autowired
    private ISiteInfoService siteInfoService;

    @Autowired
    private ISiteGroupService siteGroupService;

    @RequestMapping(value = "/groupName", method = RequestMethod.POST)
    @ResponseBody
    public Response validateGroupame(@RequestBody SiteGroup siteGroup) {
        if (siteGroupService.isExistSameGroupName(siteGroup)) {
            return Response.fail(siteGroup.getGroupName() + " exist!");
        }
        return Response.success();
    }


    @RequestMapping(value = "/siteName", method = RequestMethod.POST)
    @ResponseBody
    public Response validateSiteName(@RequestBody SiteInfo siteInfo) {
        boolean existSameSiteName = siteInfoService.isExistSameSiteName(siteInfo);
        return existSameSiteName ? Response.fail() : Response.success();
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
    }

}
