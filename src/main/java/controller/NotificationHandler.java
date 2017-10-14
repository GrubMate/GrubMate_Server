package controller;

public class NotificationHandler extends Thread{
    private int receiverID;
    private String notification;

    public NotificationHandler(int receiverID) {
        this.receiverID = receiverID;
        notification = null;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public String waitForNotification() {
        this.run();
        return notification;
    }

    public String getNotification() {
        return notification;
    }

    public void run() {
        try{
            int timeout = 10;
            while (timeout > 0) {
                NotificationHandler.sleep(1000);
                timeout --;
                if (notification!=null) {
                   break;
               }
            }
        }
        catch (InterruptedException e) {
            System.out.println("Notification Handler Error " + receiverID);
        }
    }
}
