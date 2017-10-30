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
        if (usr.friendList == null) {
            return -1;
        }
        ArrayList<String> list = usr.friendList;
        if (list.size() != 0) {
            usr.allFriends = new ArrayList<Integer>();
            for (int i=0;i<list.size();i++) {
                System.out.println(list.get(i));
                System.out.println(UserTableInteract.getUserIDFromFBID(list.get(i)));
                if (UserTableInteract.getUserIDFromFBID(list.get(i))!=null)
                    usr.allFriends.add(UserTableInteract.getUserIDFromFBID(list.get(i)));
            }
        }
        else {
            System.out.println("no friends") ;
        }
        if (usr.numRatings == null) {
            usr.numRatings = 0;
            usr.rating = 0.0;
        }
        Integer userID = UserTableInteract.addUser(usr);
        return userID;
    }

    @RequestMapping(value="/{uid}/{toWhom}/{rating}", method=RequestMethod.POST)
    public String rate(@PathVariable("uid") Integer uid, @PathVariable("toWhom") Integer toWhom, @PathVariable("rating") Integer rating){
        User user = UserTableInteract.getUser(toWhom);
        if (user == null) {return "failure";}
        if (rating <0 || rating > 5) {return "failure";}
        double newRating;
        if (user.numRatings == null) {
            user.numRatings = 1;
            user.rating = (double)rating;
            newRating = rating;
        }
        else {
            newRating = (user.numRatings * user.rating + rating) / (user.numRatings+1);
            user.numRatings ++;
            user.rating = newRating;
        }
        System.out.println("calculated rating : " + newRating);
        UserTableInteract.updateUser(user);
        return "success";
    }

    @RequestMapping(value="/{userID}",method=RequestMethod.PUT)
    public void put(@RequestBody User usr){
        System.out.println("change user " + usr.userID);
        UserTableInteract.updateUser(usr);
    }
}
