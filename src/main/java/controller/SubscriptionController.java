package controller;

import dataClass.Post;
import dataClass.Subscription;
import model.PostTableInteract;
import model.SubscriptionTableInteract;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/subscription")
public class SubscriptionController {
    @RequestMapping(value="/{id}",method= RequestMethod.GET)
    public String get(@PathVariable("id") Integer id){
        System.out.println("sub get"+id);
        return "get";
    }

    @RequestMapping(value="/{id}",method=RequestMethod.POST)
    public Subscription post(@PathVariable("id") Integer uid, @RequestBody Subscription sub){
        System.out.print("new sub" + uid);
        SubscriptionTableInteract.addSubscription(sub);
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
