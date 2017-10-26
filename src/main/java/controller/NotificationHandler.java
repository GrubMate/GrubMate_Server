package controller;

public class NotificationHandler extends Thread{
    private int receiverID;
    private  NotificationManager nm;

    public NotificationHandler(int receiverID, NotificationManager nm) {
        this.receiverID = receiverID;
        this.nm = nm;
    }

    public Notification waitForNotification() {
        this.run();
        if (nm.qMap.get(receiverID)!=null && !nm.qMap.get(receiverID).isEmpty()) {
            return nm.qMap.get(receiverID).remove();
        }
        return null;
    }


    public void run() {
        try{
            int timeout = 5;
            while (timeout > 0) {
                if (nm.qMap.get(receiverID)!=null && !nm.qMap.get(receiverID).isEmpty()) {
                   break;
                }
                timeout --;
                NotificationHandler.sleep(1000);
            }
        }
        catch (InterruptedException e) {
            System.out.println("Notification Handler Error " + receiverID);
        }
    }
}
