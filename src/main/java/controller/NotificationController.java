package controller;

import dataClass.Post;
import model.PostTableInteract;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notification")
public class NotificationController {

    @RequestMapping(value="/{id}",method= RequestMethod.GET)
    public Notification get(@PathVariable("id") Integer id){
        System.out.println("received notification request from" + id);
        if (NotificationManager.nm.getHandler(id)!=null) {
            Notification notification = new Notification();
            notification.type = Notification.TEXT;
            return null;
        }
        NotificationManager.nm.addHandler(id);
        Notification notification = NotificationManager.nm.getHandler(id).waitForNotification();
        NotificationManager.nm.deleteHandler(id);
        System.out.println(id + "'s message is " + notification);
        return notification;
    }

}
