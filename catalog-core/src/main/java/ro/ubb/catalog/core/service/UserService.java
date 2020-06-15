package ro.ubb.catalog.core.service;

import ro.ubb.catalog.core.model.User;

public interface UserService {

    User getUserByUserName(String userName);

    boolean checkCredentials(String username, String password);
}
