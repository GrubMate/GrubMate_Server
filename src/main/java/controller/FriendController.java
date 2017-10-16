package controller;


import dataClass.User;
import javafx.util.Pair;
import model.PostTableInteract;
import model.UserTableInteract;
import org.springframework.web.bind.annotation.*;
import dataClass.Post;

import java.util.ArrayList;


@RestController
@RequestMapping("/friend")
public class FriendController {


    @RequestMapping(value="/{id}",method= RequestMethod.GET)
    public ArrayList<Pair<Integer,String>> get(@PathVariable("id") Integer uid){
        User user = UserTableInteract.getUser(uid);
        ArrayList<Integer> allFriends = user.allFriends;
        ArrayList<Pair<Integer,String>> feed = new ArrayList<Pair<Integer,String>>();
        if (allFriends!=null) {
            for (int i=0;i<allFriends.size();i++) {
                User friend = UserTableInteract.getUser(allFriends.get(i));
                if (friend!=null) {
                    feed.add(new Pair<Integer, String>(friend.userID,friend.userName));
                }

            }
        }
        return feed;
    }


}
