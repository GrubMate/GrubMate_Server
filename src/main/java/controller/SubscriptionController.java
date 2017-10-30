package controller;

import dataClass.Post;
import dataClass.SearchRequest;
import dataClass.Subscription;
import model.PostTableInteract;
import model.SubscriptionTableInteract;
import model.UserTableInteract;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.ArrayList;

@RestController
@RequestMapping("/subscription")
public class SubscriptionController {
    @RequestMapping(value="/{id}",method= RequestMethod.GET)
    public SubscriptionFeed get(@PathVariable("id") Integer uid){
        System.out.println("sub get"+uid);
        SubscriptionFeed feed = new SubscriptionFeed();
        feed.id = uid;
        feed.itemList = SubscriptionTableInteract.getUserSubscriptions(uid);
        return feed;
    }


    @RequestMapping(value="/{id}/{sid}",method= RequestMethod.GET)
    public PostFeed getSubPost(@PathVariable("id") Integer uid, @PathVariable("sid") Integer sid){
        Subscription sub = SubscriptionTableInteract.getSubscription(sid);
        SearchRequest sr = new SearchRequest();
        sr.category = sub.category;
        sr.keyword = sub.query;
        sr.allergy = sub.allergyInfo;
        sr.userID = sub.subscriberID;
        PostFeed feed = new PostFeed();
        feed.id = uid;
        feed.itemList = PostTableInteract.searchPost(sr);
        return feed;
    }

    @RequestMapping(value="/{id}",method=RequestMethod.POST)
    public int post(@PathVariable("id") Integer uid, @RequestBody Subscription sub){
        System.out.print("new sub" + uid);
        int subID = SubscriptionTableInteract.addSubscription(sub);

        Integer subscriberID = sub.subscriberID;
        SearchRequest sr = new SearchRequest();
        sr.category = sub.category;
        sr.keyword = sub.query;
        sr.allergy = sub.allergyInfo;
        sr.userID = sub.subscriberID;
        sr.userID = uid;
        ArrayList<Post> results = PostTableInteract.searchPost(sr);

        for (Post p : results) {
            System.out.println("post match!" + p.title);
            Notification notification = new Notification();
            notification.type = Notification.MATCH;
            notification.title = p.title;
            notification.posterID = p.posterID;
            notification.posterName = UserTableInteract.getUser(p.posterID).userName;
            notification.postID = p.postID;
            NotificationManager.nm.addNotification(sub.subscriberID,notification);
        }

        return subID;
    }


    @RequestMapping(value="/{id}/{sid}",method=RequestMethod.DELETE)
    public String delete(@PathVariable("id") Integer id, @PathVariable("sid") Integer sid){
        System.out.println("delete sub"+id);
        if (SubscriptionTableInteract.getSubscription(sid) == null) {
            return "failure";
        }
        SubscriptionTableInteract.deleteSubscription(sid);
        return "success";
    }
}
