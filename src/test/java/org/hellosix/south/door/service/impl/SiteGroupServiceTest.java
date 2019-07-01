package org.hellosix.south.door.service.impl;

import org.hellosix.south.door.SouthDoorApplication;
import org.hellosix.south.door.model.SiteGroup;
import org.hellosix.south.door.service.ISiteGroupService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@SpringBootTest(classes = SouthDoorApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class SiteGroupServiceTest {

    @Autowired
    private ISiteGroupService siteGroupService;

    @Test
    public void addGroup() {
        SiteGroup siteGroup = new SiteGroup();
        siteGroup.setGroupName("Jay");
        siteGroup.setDescription("Jay site");
        SiteGroup siteGroup1 = siteGroupService.addGroup(siteGroup);
        System.out.println(siteGroup1);
    }

    @Test
    public void updateGroup() {
    }

    @Test
    public void deleteGroupById() {
    }

    @Test
    public void isExistSameGroupName() {
    }

    @Test
    public void getSiteGroupById() {
    }

    @Test
    public void getSiteGroupList() {
    }
}