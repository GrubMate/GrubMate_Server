package controller;


import model.PostTableInteract;
import org.springframework.web.bind.annotation.*;
import dataClass.Post;


@RestController
@RequestMapping("/friend")
public class FriendController {


    @RequestMapping(value="/{id}",method= RequestMethod.GET)
    public Integer[] get(@PathVariable("id") Integer uid){
        Integer[] friends = {};
        return friends;
    }


}
