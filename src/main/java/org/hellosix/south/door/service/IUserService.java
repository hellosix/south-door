package org.hellosix.south.door.service;

import org.hellosix.south.door.model.User;

/**
 * @author Jay.H.Zou
 * @date 6/28/2019
 */
public interface IUserService {

    User getUser(User user);

    boolean createAdminUser();
}
