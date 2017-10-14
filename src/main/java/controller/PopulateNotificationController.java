package controller;

import dataClass.Post;
import model.postTableInteract;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/populate")
public class PopulateNotificationController {

    @RequestMapping(value="/{id}",method= RequestMethod.GET)
    public String get(@PathVariable("id") Integer id, @RequestParam("receiverID") Integer rid, @RequestParam("message") String message){
        if (NotificationManager.nm.getHandler(rid) == null) {
            return "not sent";
        }
        NotificationManager.nm.getHandler(rid).setNotification(message);
        return "sent";
    }

    @RequestMapping(value="/{id}",method= RequestMethod.POST)
    public String post(@PathVariable("id") Integer id, @RequestParam("receiverID") Integer rid, @RequestParam("message") String message){
        if (NotificationManager.nm.getHandler(rid) == null) {
            return "not sent";
        }
        NotificationManager.nm.getHandler(rid).setNotification(message);
        return "sent";
    }

}
