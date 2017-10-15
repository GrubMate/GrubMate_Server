package controller;

import dataClass.Subscription;
import dataClass.User;
import model.UserTableInteract;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @RequestMapping(method= RequestMethod.GET)
    public User get(@RequestParam("userID") Integer uid){
        System.out.println("user get" + uid);
        return new User();
    }

    @RequestMapping(method=RequestMethod.POST)
    public Integer post(@RequestBody User usr){
        Integer userID = UserTableInteract.addUser(usr);
        return userID;
    }
}
