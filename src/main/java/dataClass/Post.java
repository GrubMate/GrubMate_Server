package dataClass;

import java.util.ArrayList;

public class Post {
    public Integer postID;
    public Integer posterID;
    public String title;
    public Boolean isHomeMade;
    public ArrayList<Integer> groupIDs;
    public ArrayList<String> postPhotos;
    public ArrayList<String> tags;
    public String category;
    public ArrayList<String> timePeriod;
    public String description;
    public ArrayList<String> address;
    public Integer totalQuantity;
    public Integer leftQuantity;
    public ArrayList<String> requestsIDs;
    public Boolean isActive;
    public Boolean[] allergyInfo;

    public final static String POST_ID = "postID";
    public final static String POSTER_ID= "posterID";
    public final static String TITLE = "title";
    public final static String IS_HOMEMADE = "isHomeMade";
    public final static String GROUP_IDS = "groupIDs";
    public final static String POST_PHOTOS = "postPhotos";
    public final static String TAGS = "tags";
    public final static String CATEGORY = "category";
    public final static String TIME_PERIOD = "timePeriod";
    public final static String DESCRIPTION = "description";
    public final static String ADDRESS = "address";
    public final static String TOTAL_QUANTITY = "totalQuantity";
    public final static String LEFT_QUANTITY = "leftQuantity";
    public final static String REQUESTS_IDS = "requestsIDs";
    public final static String IS_ACTIVE = "isActive";
    public final static String ALLERGY_INFO = "allergyInfo";

}
