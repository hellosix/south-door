package org.hellosix.south.door.conroller;

import org.hellosix.south.door.model.Response;
import org.hellosix.south.door.model.User;
import org.hellosix.south.door.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author Jay.H.Zou
 * @date 2019/6/29
 */
@Controller
@RequestMapping("/user/*")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private static final String USER = "user";

    @Autowired
    private IUserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Response login(@RequestBody User user) {
        User userByNameAndPassword = userService.getUserByNameAndPassword(user);
        if (userByNameAndPassword != null) {
            userByNameAndPassword.setPassword(null);
            return Response.success(userByNameAndPassword);
        } else {
            return Response.fail();
        }
    }
}
