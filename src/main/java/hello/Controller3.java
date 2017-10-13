package hello;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import model.MongoInitializer;


import java.util.concurrent.atomic.AtomicLong;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test2")
public class Controller3 {
    private MongoInitializer mi;

    @RequestMapping(value="/user/{id}",method=RequestMethod.GET)
    public String get(@PathVariable("id") Integer id){
        System.out.println("get"+id);
        return "get";
    }


    @RequestMapping(value="/user/{id}",method=RequestMethod.POST)
    public User post(@PathVariable("id") Integer id, @RequestBody User usr){
        System.out.println("post"+id);
        mi = new MongoInitializer();
        mi.addUser(usr);
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
