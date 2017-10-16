package controller;


import com.google.gson.Gson;
import model.PostTableInteract;
import org.springframework.web.bind.annotation.*;
import dataClass.Post;


@RestController
@RequestMapping("/post")
public class PostController {


    @RequestMapping(value="/{id}",method= RequestMethod.GET)
    public PostFeed get(@PathVariable("id") Integer uid){
        System.out.println("get all visible user id: "+uid);
        PostFeed feed = new PostFeed();
        feed.id = uid;
        feed.itemList = PostTableInteract.getAllVisiblePosts(uid);
        System.out.println("returning");
        System.out.println(new Gson().toJson(feed.itemList));
        return feed;
    }
    public Post get(@PathVariable("id") Integer id, @RequestParam("postID")Integer pid){
        //PostTableInteract.
        return new Post();
    }

    @RequestMapping(value="/{id}",method=RequestMethod.POST)
    public Post post(@PathVariable("id") Integer id, @RequestBody Post post){
        System.out.println("post"+id);
        PostTableInteract.addPost(post);
        return post;
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
