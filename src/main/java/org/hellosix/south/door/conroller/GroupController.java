package org.hellosix.south.door.conroller;

import org.hellosix.south.door.model.Response;
import org.hellosix.south.door.model.SiteGroup;
import org.hellosix.south.door.service.ISiteGroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author Jay.H.Zou
 * @date 2019/6/29
 */
@Controller
@RequestMapping("/group/*")
public class GroupController {

    private static final Logger logger = LoggerFactory.getLogger(GroupController.class);

    @Autowired
    private ISiteGroupService siteGroupService;

    @RequestMapping(value = "/getGroupList", method = RequestMethod.GET)
    @ResponseBody
    public List<SiteGroup> getGroupList() {
        List<SiteGroup> siteGroupList = siteGroupService.getSiteGroupList();

        logger.info(siteGroupList.toString());
        return siteGroupList;
    }

    @RequestMapping(value = "/addGroup", method = RequestMethod.POST)
    @ResponseBody
    public Response addGroup(@RequestBody SiteGroup siteGroup) {
        // 存在
        if (siteGroupService.isExistSameGroupName(siteGroup)) {
            return Response.fail(siteGroup.getGroupName() + " exist!");
        }
        SiteGroup group = siteGroupService.addGroup(siteGroup);
        return group != null ? Response.success(siteGroup) : Response.fail();
    }

    @RequestMapping(value = "/updateGroup", method = RequestMethod.POST)
    @ResponseBody
    public Response updateGroup(@RequestBody SiteGroup siteGroup) {
        SiteGroup group = siteGroupService.updateGroup(siteGroup);
        return group != null ? Response.success(siteGroup) : Response.fail();
    }

    @RequestMapping(value = "/deleteGroupById", method = RequestMethod.POST)
    @ResponseBody
    public Response deleteGroupById(@RequestBody String groupId) {
        boolean status = siteGroupService.deleteGroupById(groupId);
        return status ? Response.success() : Response.fail();
    }

}
