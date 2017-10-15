package dataClass;

import com.mongodb.BasicDBList;

import java.util.List;

public class User {
    public Integer userID;
    public String userName;
    public String facebookID;
    public String profilePhoto;
    public String bio;
    public Integer[] ratings;
    public Boolean[] allergy;
    public Integer[] groupID;
    public Integer[] postsID;
    public Integer[] requestsID;
    public Integer[] subscriptionID;
    public String[] facebook_friends;

    public final static String USER_ID = "userID";
    public final static String USER_NAME = "userName";
    public final static String FACEBOOK_ID = "facebookID";
    public final static String PROFILE_PHOTO = "profilePhoto";
    public final static String BIO = "bio";
    public final static String RATINGS = "ratings";
    public final static String ALLERGY = "allergy";
    public final static String GROUP_ID = "groupID";
    public final static String POSTS_ID = "postsID";
    public final static String REQUESTS_ID = "requestsID";
    public final static String SUBSCRIPTION_ID = "subscriptionID";
}
