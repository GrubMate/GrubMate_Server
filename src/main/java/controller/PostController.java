package controller;


import model.MongoInitializer;
import org.springframework.web.bind.annotation.*;
import dataClass.Post;
import model.postTableInteract;

@RestController
@RequestMapping("/post")
public class PostController {


    @RequestMapping(value="/{id}",method= RequestMethod.GET)
    public String get(@PathVariable("id") Integer id){
        System.out.println("get"+id);
        return "get";
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
