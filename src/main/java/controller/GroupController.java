package controller;


import dataClass.Group;
import model.PostTableInteract;
import org.springframework.web.bind.annotation.*;
import dataClass.Post;


@RestController
@RequestMapping("/group")
public class GroupController {


    @RequestMapping(value="/{id}",method= RequestMethod.GET)
    public GroupFeed get(@PathVariable("id") Integer id){
        return new GroupFeed();
    }

    @RequestMapping(value="/{id}",method=RequestMethod.POST)
    public void post(@PathVariable("id") Integer id, @RequestBody Group group){

    }

    @RequestMapping(value="/{id}",method=RequestMethod.PUT)
    public void put(@PathVariable("id") Integer id, @RequestBody Group group){

    }

    @RequestMapping(value="/{id}",method=RequestMethod.DELETE)
    public void delete(@PathVariable("id") Integer id){

    }


}
