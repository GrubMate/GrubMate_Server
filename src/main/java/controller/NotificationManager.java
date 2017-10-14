package controller;

import java.util.HashMap;

public class NotificationManager {
    public static NotificationManager nm;
    public HashMap<Integer, NotificationHandler> map;

    NotificationManager() {
        map = new HashMap<Integer, NotificationHandler>();
    }

    public static void createNotificationManager() {
        nm = new NotificationManager();
    }

    public void addHandler(Integer id) {
        map.put(id,new NotificationHandler(id));
    }

    public NotificationHandler getHandler(Integer id) {
        return map.get(id);
    }

    public void deleteHandler(Integer id) {
        map.put(id,null);
    }
}
