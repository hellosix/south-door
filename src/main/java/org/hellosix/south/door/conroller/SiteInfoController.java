package org.hellosix.south.door.conroller;

import org.hellosix.south.door.model.Response;
import org.hellosix.south.door.model.SiteInfo;
import org.hellosix.south.door.service.ISiteInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author Jay.H.Zou
 * @date 2019/6/29
 */
@Controller
@RequestMapping("/site/*")
public class SiteInfoController {

    private static final Logger logger = LoggerFactory.getLogger(SiteInfoController.class);

    @Autowired
    private ISiteInfoService siteInfoService;

    @RequestMapping(value = "/saveSiteImage", method = RequestMethod.POST)
    @ResponseBody
    public Response saveSiteImage(@RequestParam("siteImage") MultipartFile multipartFile, HttpServletRequest request) throws IOException {
        String path = request.getSession().getServletContext().getRealPath("/saveSiteImage");
        System.err.println(multipartFile.getOriginalFilename());
        System.out.println(path);
        /*File filePath = new File(path);
        System.out.println("文件的保存路径：" + path);
        if (!filePath.exists() && !filePath.isDirectory()) {
            System.out.println("目录不存在，创建目录:" + filePath);
            filePath.mkdir();
        }*/
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

    @RequestMapping(value = "/addSite", method = RequestMethod.POST)
    @ResponseBody
    public Response addSite(@RequestBody SiteInfo siteInfo) {
        if (siteInfoService.isExistSameSiteName(siteInfo)) {
            return Response.fail(siteInfo.getSiteName() + " exist!");
        }
        boolean status = siteInfoService.addSiteInfo(siteInfo);
        return status ? Response.success() : Response.fail();
    }

    @RequestMapping(value = "/deleteSiteById", method = RequestMethod.POST)
    @ResponseBody
    public Response deleteSiteById(@RequestBody String siteId) {
        boolean status = siteInfoService.deleteSiteInfo(siteId);
        return status ? Response.success() : Response.fail();
    }
}
