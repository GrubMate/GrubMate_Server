package controller;

import dataClass.Subscription;
import dataClass.User;
import model.UserTableInteract;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/user")
public class UserController {
    @RequestMapping(method= RequestMethod.GET)
    public User get(@RequestParam("userID") Integer uid){
        System.out.println(" get" + uid);
        return new User();
    }

    @RequestMapping(method=RequestMethod.POST)
    public Integer post(@RequestBody User usr){
        System.out.println("facebookid" + usr.facebookID);
        String[] list = usr.friendList;
        if (list.length != 0) {
            usr.allFriends = new ArrayList<Integer>();
            for (int i=0;i<list.length;i++) {
                usr.allFriends.add(UserTableInteract.getUserIDFromFBID(list[i]));
            }
        }
        else {
            System.out.println("no friends");
        }
        Integer userID = UserTableInteract.addUser(usr);
        return userID;
    }
}
