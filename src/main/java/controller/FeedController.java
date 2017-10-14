package controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/feed")
public class FeedController {
    @RequestMapping(value="/user/{id}",method=RequestMethod.GET)
    public Feed get(@PathVariable("id") Integer id){
        String[] list = {"caonima","caocaocao","caonima","caocaocao","caonima","caocaocao","caonima","caocaocao","caonima","caocaocao","caonima","caocaocao","caonima","caocaocao","caonima","caocaocao" };
        Feed feed = new Feed();
        feed.id = id;
        System.out.println("get"+id);
        return feed;
    }


    @RequestMapping(value="/user/{id}",method=RequestMethod.POST)
    public String post(@PathVariable("id") Integer id){
        return "";
        //return "I got you message name:" + car.name+ " miles:" + car.miles;
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
