package controller;

import com.google.gson.Gson;
import dataClass.Post;
import dataClass.SearchRequest;
import dataClass.Subscription;
import model.PostTableInteract;
import model.SubscriptionTableInteract;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RoutinaryEventHandler extends Thread{

    private HashMap<Integer,String> matches;


    public RoutinaryEventHandler() {
        matches = new HashMap<Integer,String>();
    }

    public void checkSubscription() {
        ArrayList<Subscription> subscriptions = SubscriptionTableInteract.getAllSubscriptions();
        for (Subscription sub : subscriptions) {
            Integer subscriberID = sub.subscriberID;
            String before = "";
            if (matches.get(subscriberID)!=null) {
                before = matches.get(subscriberID);
            }
            SearchRequest sr = new SearchRequest();
            sr.category = sub.category;
            sr.keyword = sub.query;
            sr.allergy = sub.allergyInfo;
            sr.userID = sub.subscriberID;
            ArrayList<Post> results = PostTableInteract.searchPost(sr);
            String now = "";
            for (Post post: results) {
                now += post.posterID + ",";
            }
            if (before.equals(now)) {
                Notification notification = new Notification();
                notification.what = Notification.NEW_MATCH_FOR_SUBSCRIPTION;
                notification.message = "Your subscription has a new match";
                NotificationManager.nm.addNotification(sub.subscriberID,notification);
            }
            matches.put(subscriberID,now);
        }

    }


    public void run() {
        try {
            while (true) {
                RoutinaryEventHandler.sleep(5000);
                checkSubscription();
            }
        }
        catch (InterruptedException e) {
            System.out.println("Routinary Event Handler Error");
        }
    }
}
