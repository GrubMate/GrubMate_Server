package controller;

import dataClass.Post;
import dataClass.Request;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/request")
public class RequestController {
    @RequestMapping(value="/{id}",method= RequestMethod.GET)
    public String get(@PathVariable("id") Integer id){
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
