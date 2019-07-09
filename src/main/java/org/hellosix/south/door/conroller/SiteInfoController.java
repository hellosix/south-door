package org.hellosix.south.door.conroller;

import org.hellosix.south.door.model.Response;
import org.hellosix.south.door.model.SiteInfo;
import org.hellosix.south.door.service.ISiteInfoService;
import org.hellosix.south.door.util.ImageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Map;

/**
 * @author Jay.H.Zou
 * @date 2019/6/29
 */
@Controller
@RequestMapping("/site/*")
public class SiteInfoController {

    private static final Logger logger = LoggerFactory.getLogger(SiteInfoController.class);

    @Value("${south-door.data.site-image-path}")
    private String siteImagePath;

    @Autowired
    private ISiteInfoService siteInfoService;

    @RequestMapping(value = "/saveSiteImage", method = RequestMethod.POST)
    @ResponseBody
    public Response saveSiteImage(@RequestParam("siteImage") MultipartFile multipartFile,@RequestParam Map<String, Object> siteInfo) {
        if (siteInfo == null || siteInfo.get("siteName") == null) {
            return Response.fail("site name is empty");
        }
        String siteName = siteInfo.get("siteName").toString().trim();
        String siteId = siteInfo.get("siteId").toString();
        SiteInfo site = new SiteInfo();
        site.setSiteId(siteId);
        site.setSiteName(siteName);
        try {
            boolean existSameSiteName = siteInfoService.isExistSameSiteName(site);
            if (!existSameSiteName) {
                ImageUtil.saveImage(multipartFile, siteImagePath, siteName);
            } else {
                Response.fail(siteName + " exist!");
            }

        } catch (Exception e) {
            logger.error("save site image failed, return a random image.", e);
        }
        return Response.success();
    }

    @RequestMapping(value = "/getAllSite", method = RequestMethod.GET)
    @ResponseBody
    public Response getAllSite() {
        List<SiteInfo> allSiteInfoList = siteInfoService.getAllSiteInfoList();
        return Response.success(allSiteInfoList);
    }

    @RequestMapping(value = "/getSiteByGroupId", method = RequestMethod.GET)
    @ResponseBody
    public Response getSiteByGroupId(@PathParam("groupId") String groupId) {
        List<SiteInfo> siteInfoList = siteInfoService.getSiteInfoListByGroupId(groupId);
        return Response.success(siteInfoList);
    }

    @RequestMapping(value = "/getSiteInfoById", method = RequestMethod.GET)
    @ResponseBody
    public Response getSiteInfoById(@PathParam("siteId") String siteId) {
        SiteInfo siteInfo = siteInfoService.getSiteInfoById(siteId);
        return Response.success(siteInfo);
    }

    @RequestMapping(value = "/saveSiteInfo", method = RequestMethod.POST)
    @ResponseBody
    public Response saveSiteInfo(@RequestBody SiteInfo siteInfo) {
        if (siteInfoService.isExistSameSiteName(siteInfo)) {
            return Response.fail(siteInfo.getSiteName() + " exist!");
        }
        boolean status = siteInfoService.saveSiteInfo(siteInfo);
        return status ? Response.success() : Response.fail();
    }

    @RequestMapping(value = "/deleteSiteById", method = RequestMethod.POST)
    @ResponseBody
    public Response deleteSiteById(@RequestBody String siteId) {
        boolean status = siteInfoService.deleteSiteInfo(siteId);
        return status ? Response.success() : Response.fail();
    }
}
