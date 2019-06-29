package org.hellosix.south.door.conroller;

import org.hellosix.south.door.model.SiteGroup;
import org.hellosix.south.door.service.ISiteGroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
        return siteGroupService.getSiteGroupList();
    }

}
