package controller;

import dataClass.Post;
import dataClass.SearchRequest;
import dataClass.Subscription;
import model.PostTableInteract;
import model.SubscriptionTableInteract;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/subscription")
public class SubscriptionController {
    @RequestMapping(value="/{id}",method= RequestMethod.GET)
    public ArrayList<Subscription> get(@PathVariable("id") Integer uid){
        System.out.println("sub get"+uid);
        return SubscriptionTableInteract.getUserSubscriptions(uid);
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
    public Subscription post(@PathVariable("id") Integer uid, @RequestBody Subscription sub){
        System.out.print("new sub" + uid);
        SubscriptionTableInteract.addSubscription(sub);
        return sub;
    }


    @RequestMapping(value="/{id}",method=RequestMethod.DELETE)
    public void delete(@PathVariable("id") Integer id, @RequestParam("subscriptionID") Integer sid){
        System.out.println("delete sub"+id);
        SubscriptionTableInteract.deleteSubscription(sid);
    }
}
