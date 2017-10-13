package controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class Controller2 {
    @RequestMapping(method = RequestMethod.GET)
    public String param(@RequestParam(value="name", defaultValue="World") String name,
                        @RequestParam(value="name2", defaultValue="World")String name2){
        System.out.println(name+" "+name2);
        return name+" "+name2;
    }



}
