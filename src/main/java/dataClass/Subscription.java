package dataClass;

import java.util.ArrayList;

public class Subscription {
    public Integer subscriptionID;
    public Integer subscriberID;
    public ArrayList<String> tags;
    public String category;
    public String query;
    public Integer[] timePeriod;
    public ArrayList<Integer> matchedPostIDs;
    public Boolean[] allergyInfo;
    public Boolean isActive;

    public final static String SUBSCRIPTION_ID = "subscriptionID";
    public final static String SUBSCRIBER_ID = "subscriberID";
    public final static String TAGS = "tags";
    public final static String CATEGORY = "category";
    public final static String QUERY = "query";
    public final static String TIME_PERIOD = "timePeriod";
    public final static String MATCHED_POST_IDS = "matchedPostIDs";
    public final static String ALLERGY_INFO = "allergyInfo";
    public final static String IS_ACTIVE = "isActive";
}
