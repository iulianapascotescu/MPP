package ro.ubb.catalog.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.ubb.catalog.core.model.User;
import ro.ubb.catalog.core.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public User getUserByUserName(String userName) {
        return userRepository.getUserByUserName(userName);
    }

    @Override
    public boolean checkCredentials(String username, String password){
        User user = this.getUserByUserName(username);
        if(user==null)
            return false;
        return user.getPassword().equals(password);
    }
}
