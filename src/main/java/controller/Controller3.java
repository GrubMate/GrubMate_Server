package controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import dataClass.User;


import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test2")
public class Controller3 {

    @RequestMapping(value="/user/{id}",method=RequestMethod.GET)
    public String get(@PathVariable("id") Integer id){
        System.out.println("get"+id);
        return "get";
    }

    @RequestMapping(value="/user/{id}",method=RequestMethod.POST)
    public User post(@PathVariable("id") Integer id, @RequestBody User usr){
        System.out.println("post"+id);
        System.out.print("adding" + usr.facebookID);
        return usr;

    }

    @RequestMapping(value="/user/{id}",method=RequestMethod.PUT)
    public String put(@PathVariable("id") Integer id){
        System.out.println("put"+id);
        return "put";
    }

    @RequestMapping(value="/user/{id}",method=RequestMethod.DELETE)
    public String delete(@PathVariable("id") Integer id){
        System.out.println("delete"+id);
        return "delete";
    }


}
