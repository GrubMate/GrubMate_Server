package controller;


import com.google.gson.Gson;
import com.sun.tools.corba.se.idl.constExpr.Not;
import dataClass.Request;
import dataClass.SearchRequest;
import dataClass.Subscription;
import model.PostTableInteract;
import model.RequestTableInteract;
import model.SubscriptionTableInteract;
import model.UserTableInteract;
import org.springframework.web.bind.annotation.*;
import dataClass.Post;
import sun.misc.BASE64Decoder;

import javax.imageio.ImageIO;
import javax.websocket.server.PathParam;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


@RestController
@RequestMapping("/post")
public class PostController {


    @RequestMapping(value="/{id}",method= RequestMethod.GET)
    public PostFeed get(@PathVariable("id") Integer uid){
        System.out.println("get all visible user id: "+uid);
        if (UserTableInteract.getUser(uid) == null) {
            return null;
        }
        PostFeed feed = new PostFeed();
        feed.id = uid;
        feed.itemList = PostTableInteract.getAllVisiblePosts(uid);
        System.out.println("returning");
        System.out.println(new Gson().toJson(feed.itemList));
        return feed;
    }

    @RequestMapping(value="/{id}/{me}",method= RequestMethod.GET)
    public PostFeed get2(@PathVariable("id") Integer uid, @PathVariable("me") Boolean active){
        System.out.println("get all my user id: "+uid);
        if (active) {
            PostFeed feed = new PostFeed();
            feed.id = uid;
            ArrayList<Post> posts = PostTableInteract.getUserPosts(uid);
            for (Post p : posts) {
                if (p.isActive == null) continue;
                if (p.isActive) {
                    feed.itemList.add(p);
                }
            }
            System.out.println(new Gson().toJson(feed.itemList));
            return feed;
        }
        else {
            PostFeed feed = new PostFeed();
            feed.id = uid;
            ArrayList<Post> posts = PostTableInteract.getUserPosts(uid);
            for (Post p : posts) {
                if (p.isActive == null) continue;
                if (!p.isActive) {
                    feed.itemList.add(p);
                }
            }
            System.out.println(new Gson().toJson(feed.itemList));
            return feed;
        }
    }


    @RequestMapping(value="/{id}",method=RequestMethod.POST)
    public Integer post(@PathVariable("id") Integer uid, @RequestBody Post post){
        System.out.println("post"+uid);
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
        timeStamp = timeStamp.replace(".","-");
        int count = 0;
        ArrayList<String> imageFileNames = new ArrayList<String>();
        if (post.postPhotos != null) {
            for (String imgstr : post.postPhotos) {
                BufferedImage image = null;
                byte[] imageByte;
                BASE64Decoder decoder = new BASE64Decoder();
                try {
                    imageByte = decoder.decodeBuffer(imgstr);
                    ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
                    image = ImageIO.read(bis);
                    bis.close();
                    File outputfile = new File(  "images/" + timeStamp + "---" + Integer.toString(count));
                    ImageIO.write(image, "jpeg", outputfile);
                    imageFileNames.add(timeStamp + "---" + Integer.toString(count));
                    count ++;
                } catch (IOException e) {
                    System.out.println("image io exception");
                }
            }
        }
        post.postPhotos = imageFileNames;

        System.out.println("uid" + uid);
        if (UserTableInteract.getUser(uid) != null) {
            post.posterName = UserTableInteract.getUser(uid).userName;
        }

        ////
        int postID = PostTableInteract.addPost(post);
        ////

        // this is for subscription
        ArrayList<Subscription> subscriptions = SubscriptionTableInteract.getAllSubscriptions();
        for (Subscription sub : subscriptions) {
            SearchRequest sr = new SearchRequest();
            sr.category = sub.category;
            sr.keyword = sub.query;
            sr.allergy = sub.allergyInfo;
            sr.userID = sub.subscriberID;
            ArrayList<Post> results = PostTableInteract.searchPost(sr);
            for (Post p: results) {
                if (p.posterID == postID) {
                    Notification notification = new Notification();
                    notification.type = Notification.MATCH;
                    notification.title = p.title;
                    notification.posterID = p.posterID;
                    notification.posterName = UserTableInteract.getUser(p.posterID).userName;
                    notification.postID = p.postID;
                    NotificationManager.nm.addNotification(sub.subscriberID,notification);
                }
            }

        }

        return postID;
    }

    @RequestMapping(value="/{id}/{pid}", method=RequestMethod.POST)
    public void confirm(@PathVariable("id") Integer uid, @PathVariable("pid") Integer pid){
        System.out.println("confirm post" + pid);
        Post post  = PostTableInteract.getPost(pid);
        post.isActive = false;
        ArrayList<Integer> requestIDs = post.requestsIDs;
        if (requestIDs != null) {
            for (Integer rid : requestIDs) {

                Request request = RequestTableInteract.getRequest(rid);
                System.out.println(request.status);
                if (!request.status.equals("ACCEPTED")) {
                    request.status = "DENIED";
                }
                else {
                    System.out.println("yeah");
                    Notification notification = new Notification();
                    notification.type = Notification.RATING;
                    notification.fromUserID = post.posterID;
                    notification.fromUserName = UserTableInteract.getUser(post.posterID).userName;
                    notification.toUserID = request.requesterID;
                    notification.toUserName = UserTableInteract.getUser(request.requesterID).userName;
                    notification.title = post.title;
                    System.out.println(notification);
                    System.out.println("sending this to " + notification.toUserID);

                    NotificationManager.nm.addNotification(notification.fromUserID,notification);

                    Notification notification2 = new Notification();
                    notification2.type = Notification.RATING;
                    notification2.toUserID = post.posterID;
                    notification2.toUserName = UserTableInteract.getUser(post.posterID).userName;
                    notification2.fromUserID = request.requesterID;
                    notification2.fromUserName = UserTableInteract.getUser(request.requesterID).userName;
                    notification2.title = post.title;
                    System.out.println("sending this to " + notification2.toUserID);
                    System.out.println(notification2);
                    NotificationManager.nm.addNotification(notification2.fromUserID,notification2);
                }
            }
        }
        System.out.println(post.isActive?"active":"not active");
        PostTableInteract.updatePost(post);
    }


    @RequestMapping(value="/{id}",method=RequestMethod.PUT)
    public String put(@PathVariable("id") Integer uid, @RequestBody Post post){
        if (PostTableInteract.getPost(post.postID)==null) {return "failure";}
        System.out.println("edit post"+post.postID);
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
        timeStamp = timeStamp.replace(".","-");
        int count = 0;
        ArrayList<String> imageFileNames = new ArrayList<String>();
        if (post.postPhotos != null) {
            for (String imgstr : post.postPhotos) {
                BufferedImage image = null;
                byte[] imageByte;
                BASE64Decoder decoder = new BASE64Decoder();
                try {
                    imageByte = decoder.decodeBuffer(imgstr);
                    ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
                    image = ImageIO.read(bis);
                    bis.close();
                    File outputfile = new File(  "images/" + timeStamp + "---" + Integer.toString(count));
                    ImageIO.write(image, "jpeg", outputfile);
                    imageFileNames.add(timeStamp + "---" + Integer.toString(count));
                    String s = ""; //no use just to suppress warning
                    count ++;
                } catch (IOException e) {
                    System.out.println("image io exception");
                }
            }
        }
        post.postPhotos = imageFileNames;
        if (UserTableInteract.getUser(uid) != null) {
            post.posterName = UserTableInteract.getUser(uid).userName;
        }

        PostTableInteract.updatePost(post);
        return "success";
    }

    @RequestMapping(value="/{id}/{postID}",method=RequestMethod.DELETE)
    public String delete(@PathVariable("id") Integer id, @PathVariable("postID") Integer pid){
        System.out.println("delete post"+pid);
        if (PostTableInteract.getPost(pid)==null) {return "failure";}
        PostTableInteract.deletePost(pid);
        return "success";
    }


}
