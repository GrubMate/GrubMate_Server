package controller;

import dataClass.Post;
import dataClass.Request;
import model.PostTableInteract;
import model.RequestTableInteract;
import model.UserTableInteract;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/request")
public class RequestController {
    @RequestMapping(value="/{id}",method= RequestMethod.GET)
    public RequestFeed get(@PathVariable("id") Integer uid){
        System.out.println("request get " + uid);
        RequestFeed feed = new RequestFeed();
        feed.id = uid;
        feed.itemList = RequestTableInteract.getUserRequests(uid);
        return feed;
    }

    @RequestMapping(value="/{id}/{pid}",method= RequestMethod.GET)
    public RequestFeed getPostReq(@PathVariable("id") Integer uid,@PathVariable("pid") Integer pid){
        System.out.println("request get " + uid);
        RequestFeed feed = new RequestFeed();
        feed.id = uid;
        feed.itemList = RequestTableInteract.getPostRequests(pid);
        return feed;
    }

    @RequestMapping(value="/{id}",method=RequestMethod.POST)
    public void post(@PathVariable("id") Integer id, @RequestBody Request req){
        RequestTableInteract.addRequest(req);
        Integer toWhom = PostTableInteract.getPost(req.targetPostID).posterID;
        Notification notification = new Notification();
        notification.what = Notification.MY_POST_IS_REQUESTED;
        notification.message = UserTableInteract.getUser(req.requesterID).userName + " requested you post";
        NotificationManager.nm.addNotification(toWhom,notification);
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
