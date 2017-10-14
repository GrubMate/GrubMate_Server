package controller;


import model.MongoInitializer;
import org.springframework.web.bind.annotation.*;
import dataClass.Post;
import model.postTableInteract;

@RestController
@RequestMapping("/post")
public class PostController {


    @RequestMapping(value="/{id}",method= RequestMethod.GET)
    public Feed get(@PathVariable("id") Integer id){
        System.out.println("get"+id);
        Feed feed = new Feed();
        feed.itemList = new Post[5];

        for (int i=0;i<5;i++) {
            feed.itemList[i] = new Post();
            feed.itemList[i].posterID = i*100;
            feed.itemList[i].title = "post" +  i*100;
        }
        return feed;
    }

    @RequestMapping(value="/{id}",method=RequestMethod.POST)
    public Post post(@PathVariable("id") Integer id, @RequestBody Post post){
        System.out.println("post"+id);
        postTableInteract.addPost(post);
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
