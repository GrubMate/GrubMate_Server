package controller;

import java.util.ArrayList;

public class Notification {
    public int type;
    public static final int REQUEST = 1;
    public static final int MATCH = 2;
    public static final int ACCEPTED = 3;
    public static final int RATING = 4;
    public static final int DENIED = 5;
    public static final int TEXT = 100;


    public Integer requestID = -1;
    public Integer requesterID  = -1;
    public Integer targetPostID = -1;
    public String requesterName = "defualt";
    public String status = "defualt";
    public ArrayList<Double> address;
    public Integer postID = -1;
    public Integer posterID = -1;
    public String posterName = "defualt";
    public String title = "defualt";

    /*
        REQUEST should have
        reuestID
        requesterID
        requesterName
        targetPostID
        title
        address
     */

    /*
        MATCH should have
        postID
        posterID
 	posterName
        title
     */

    /*
        ACCEPTED should have
        requestID
        title
        posterID
     */
}