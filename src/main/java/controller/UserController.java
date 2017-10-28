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
        System.out.println(uid);
        return UserTableInteract.getUser(uid);
    }
    @RequestMapping(value="/{userID}",method= RequestMethod.GET)
    public User get2(@PathVariable("userID") Integer uid){
        System.out.println(uid);
        return UserTableInteract.getUser(uid);
    }

    @RequestMapping(method=RequestMethod.POST)
    public Integer post(@RequestBody User usr){
        System.out.println("facebookid" + usr.facebookID);
        String[] list = usr.friendList;
        if (list.length != 0) {
            usr.allFriends = new ArrayList<Integer>();
            for (int i=0;i<list.length;i++) {
                System.out.println(list[i]);
                System.out.println(UserTableInteract.getUserIDFromFBID(list[i]));
                if (UserTableInteract.getUserIDFromFBID(list[i])!=null)
                    usr.allFriends.add(UserTableInteract.getUserIDFromFBID(list[i]));
            }
        }
        else {
            System.out.println("no friends") ;
        }
        Integer userID = UserTableInteract.addUser(usr);
        return userID;
    }

    @RequestMapping(value="/{uid}/{toWhom}/{rating}", method=RequestMethod.POST)
    public String rate(@PathVariable("uid") Integer uid, @PathVariable("toWhom") Integer toWhom, @PathVariable("rating") Integer rating){
        User user = UserTableInteract.getUser(toWhom);
        if (user.numRatings == null) {
            user.numRatings = new Integer(0);
            user.rating = new Double(0);
        }
        user.rating = (user.rating * user.numRatings + rating) / (user.numRatings++);
        return "success";
    }

    @RequestMapping(value="/{userID}",method=RequestMethod.PUT)
    public void put(@RequestBody User usr){
        System.out.println("change user " + usr.userID);
        UserTableInteract.updateUser(usr);
    }
}
