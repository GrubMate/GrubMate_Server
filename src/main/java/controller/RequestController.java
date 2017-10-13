package controller;

import com.sun.org.apache.regexp.internal.RE;
import dataClass.Post;
import dataClass.Request;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public class RequestController {
    @RequestMapping(value="/{id}",method= RequestMethod.GET)
    public String get(@PathVariable("id") Integer id){
        System.out.println("get"+id);
        return "get";
    }

    @RequestMapping(value="/{id}",method=RequestMethod.POST)
    public Request post(@PathVariable("id") Integer id, @RequestBody Request req){
        System.out.println("post"+id);
        return req;
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
