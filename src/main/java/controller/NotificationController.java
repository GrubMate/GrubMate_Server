package controller;

import dataClass.Post;
import model.PostTableInteract;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notification")
public class NotificationController {

    @RequestMapping(value="/{id}",method= RequestMethod.GET)
    public String get(@PathVariable("id") Integer id){
        if (NotificationManager.nm.getHandler(id)!=null) {
            return "multi-login error";
        }
        NotificationManager.nm.addHandler(id);
        NotificationManager.nm.getHandler(id).waitForNotification();
        String notification = NotificationManager.nm.getHandler(id).getNotification();
        NotificationManager.nm.deleteHandler(id);
        return "your message is :" + notification;
    }

}
