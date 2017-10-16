package controller;

import dataClass.Post;
import dataClass.SearchField;
import dataClass.SearchRequest;
import dataClass.Subscription;
import model.PostTableInteract;
import model.SubscriptionTableInteract;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/search")
public class SearchController {
    @RequestMapping(value="/{id}",method= RequestMethod.GET)
    public String get(@PathVariable("id") Integer id){
        System.out.println("sub get"+id);
        return "get";
    }

    @RequestMapping(value="/{id}",method=RequestMethod.POST)
    public PostFeed post(@PathVariable("id") Integer uid, @RequestBody SearchField sf){
        System.out.println("search " + uid);
        SearchRequest sr = new SearchRequest();
        sr.category = sf.category;
        sr.keyword = sf.title;
        sr.allergy = sf.allergyInfo;
        sr.userID = uid;

        PostFeed feed = new PostFeed();
        feed.id = uid;
        feed.itemList = PostTableInteract.searchPost(sr);
        return feed;
    }

    @RequestMapping(value="/{id}",method=RequestMethod.PUT)
    public String put(@PathVariable("id") Integer id){
        System.out.println("put"+id);
        return "put";
    }

    @RequestMapping(value="/{id}",method=RequestMethod.DELETE)
    public String delete(@PathVariable("id") Integer id){
        System.out.println("delete"+id);
        return "delete";
    }
}
