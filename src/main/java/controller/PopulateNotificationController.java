package controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/populate")
public class PopulateNotificationController {

    @RequestMapping(value="/{id}",method= RequestMethod.GET)
    public String get(@PathVariable("id") Integer id, @RequestParam("receiverID") Integer rid, @RequestParam("message") String message,  @RequestParam("what") String what){
        Notification notification = new Notification();
        notification.message = message;
        notification.what = what;
        NotificationManager.nm.addNotification(rid,notification);
        return "sent";
    }

    @RequestMapping(value="/{id}",method= RequestMethod.POST)
    public String post(@PathVariable("id") Integer id, @RequestParam("receiverID") Integer rid, @RequestParam("message") String message,  @RequestParam("what") String what){
        Notification notification = new Notification();
        notification.message = message;
        notification.what = what;
        NotificationManager.nm.addNotification(rid,notification);
        return "sent";
    }

}
