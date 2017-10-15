package controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/populate")
public class PopulateNotificationController {

    @RequestMapping(value="/{id}",method= RequestMethod.GET)
    public String get(@PathVariable("id") Integer id, @RequestParam("receiverID") Integer rid, @RequestParam("message") String message){
        NotificationManager.nm.addNotification(rid,message);
        return "sent";
    }

    @RequestMapping(value="/{id}",method= RequestMethod.POST)
    public String post(@PathVariable("id") Integer id, @RequestParam("receiverID") Integer rid, @RequestParam("message") String message){
        NotificationManager.nm.addNotification(rid,message);
        return "sent";
    }

}
