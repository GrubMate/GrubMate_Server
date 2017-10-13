package controller;

import dataClass.Post;
import dataClass.Subscription;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public class SubscriptionController {
    @RequestMapping(value="/{id}",method= RequestMethod.GET)
    public String get(@PathVariable("id") Integer id){
        System.out.println("get"+id);
        return "get";
    }

    @RequestMapping(value="/{id}",method=RequestMethod.POST)
    public Subscription post(@PathVariable("id") Integer id, @RequestBody Subscription sub){

        return sub;
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
