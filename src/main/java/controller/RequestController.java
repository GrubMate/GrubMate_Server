package controller;

import dataClass.Post;
import dataClass.Request;
import model.PostTableInteract;
import model.RequestTableInteract;
import model.UserTableInteract;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

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

    @RequestMapping(value="/{id}/{rid}/{accept}",method=RequestMethod.GET)
    public String respondRequest(@PathVariable("id") Integer id, @PathVariable("rid") Integer rid, @PathVariable("accept") Integer accept){
        System.out.println(accept==1 ? "ACCEPTED" : "DENIED");
        Request req = RequestTableInteract.getRequest(rid);
        req.status = accept==1 ? "ACCEPTED" : "DENIED";
        RequestTableInteract.updateRequest(req);
        Post post = PostTableInteract.getPost(req.targetPostID);
        post.leftQuantity -= 1;
        if (post.leftQuantity <=0) {
            post.isActive = false;
        }
        PostTableInteract.updatePost(post);
        Integer requesterID = req.requesterID;
        Notification reqn = new Notification();
        reqn.type = accept==1?Notification.ACCEPTED:Notification.DENIED;
        reqn.title = PostTableInteract.getPost(req.targetPostID).title;
        reqn.requesterID = requesterID;
        reqn.posterID = PostTableInteract.getPost(req.targetPostID).posterID;
        NotificationManager.nm.addNotification(requesterID,reqn);
        return "";
    }

    @RequestMapping(value="/{id}",method=RequestMethod.POST)
    public void post(@PathVariable("id") Integer id, @RequestBody Request req){
        System.out.println("request ID " + id);
        int reqID = RequestTableInteract.addRequest(req);
        Integer toWhom = PostTableInteract.getPost(req.targetPostID).posterID;
        Notification notification = new Notification();
        notification.type = Notification.REQUEST;
        notification.requestID = reqID;
        notification.requesterID = req.requesterID;
        notification.requesterName = UserTableInteract.getUser(req.requesterID).userName;
        notification.title = PostTableInteract.getPost(req.targetPostID).title;
        notification.address = PostTableInteract.getPost(req.targetPostID).address;
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
