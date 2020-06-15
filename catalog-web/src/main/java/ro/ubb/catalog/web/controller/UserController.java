package ro.ubb.catalog.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ro.ubb.catalog.core.model.User;
import ro.ubb.catalog.core.service.UserService;
import ro.ubb.catalog.web.dto.UserDto;

@RestController
public class UserController {

    public static final Logger log = LoggerFactory.getLogger(ClientController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public UserDto login(@RequestBody UserDto user){
        User u = userService.getUserByUserName(user.getUsername());
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        if(bCryptPasswordEncoder.matches(user.getPassword(), u.getPassword()))
            return new UserDto(user.getUsername(), user.getPassword(), u.getUserRole().toString());
        else return new UserDto("","", "");
    }
}
