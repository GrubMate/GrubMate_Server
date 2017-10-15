package controller;


import model.MongoInitializer;
import model.PostTableInteract;
import org.springframework.web.bind.annotation.*;
import dataClass.Post;


@RestController
@RequestMapping("/post")
public class PostController {


    @RequestMapping(value="/{id}",method= RequestMethod.GET)
    public Feed get(@PathVariable("id") Integer id){
        System.out.println("get"+id);
        Feed feed = new Feed();
        feed.id = id;
        Post [] list  = {new Post(), new Post(), new Post()};
        feed.itemList = list;
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
