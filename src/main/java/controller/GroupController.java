package controller;


import dataClass.Group;
import dataClass.User;
import model.GroupInfoTableInteract;
import model.PostTableInteract;
import model.UserTableInteract;
import org.springframework.web.bind.annotation.*;
import dataClass.Post;

import java.util.ArrayList;


@RestController
@RequestMapping("/group")
public class GroupController {


    @RequestMapping(value="/{id}",method= RequestMethod.GET)
    public GroupFeed get(@PathVariable("id") Integer uid){
        System.out.println("get group feed " + uid);
        GroupFeed feed = new GroupFeed();
        feed.id = uid;
        User user = UserTableInteract.getUser(uid);
        if (user!=null) {
            ArrayList<Integer> groups = user.groupID;
            if (groups!=null) {
                for (Integer gid : groups) {
                    Group g = GroupInfoTableInteract.getGroupInfo(gid);
                    feed.itemList.add(g);
                }
            }
        }
        //
        Group g = new Group();
        g.groupID = 123;
        g.groupName = "sb";
        g.memberIDs = new ArrayList<Integer>();
        g.memberIDs.add(1);
        g.memberIDs.add(2);
        g.memberIDs.add(3);
        feed.itemList.add(g);
        //
        return feed;
    }

    @RequestMapping(value="/{id}",method=RequestMethod.POST)
    public Integer post(@PathVariable("id") Integer uid, @RequestBody Group group){
        System.out.println("post feed" + uid);
        Integer gid = GroupInfoTableInteract.addGroupinfo(group);
        return gid;
    }

    @RequestMapping(value="/{id}",method=RequestMethod.PUT)
    public void put(@PathVariable("id") Integer id, @RequestBody Group group){

    }

    @RequestMapping(value="/{id}",method=RequestMethod.DELETE)
    public void delete(@PathVariable("id") Integer id){

    }


}
