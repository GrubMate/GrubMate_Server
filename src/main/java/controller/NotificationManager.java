package controller;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class NotificationManager {
    public static NotificationManager nm;
    public HashMap<Integer, NotificationHandler> map;
    public HashMap<Integer,Queue<String>> qMap;

    NotificationManager() {
        map = new HashMap<Integer, NotificationHandler>();
        qMap = new HashMap<Integer, Queue<String> >();
    }

    public static void createNotificationManager() {
        nm = new NotificationManager();
    }

    public void addHandler(Integer receiverID) {
        map.put(receiverID,new NotificationHandler(receiverID,this));
    }

    public void addNotification(Integer receiverID, String message) {
        if (qMap.get(receiverID) == null) {
            qMap.put(receiverID, new LinkedList<>());
        }
        qMap.get(receiverID).add(message);
    }

    public NotificationHandler getHandler(Integer receiverID) {
        return map.get(receiverID);
    }

    public void deleteHandler(Integer id) {
        map.put(id,null);
    }
}
