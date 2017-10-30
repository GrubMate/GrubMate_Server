package dataClass;

import com.mongodb.BasicDBList;

import java.util.ArrayList;
import java.util.List;

public class User {
    public Integer userID;
    public String userName;
    public String facebookID;
    public String profilePhoto;
    public String bio;
    public Integer numRatings;
    public Double rating;
    public Boolean[] allergy;
    public ArrayList<Integer> allFriends ;
    public ArrayList<Integer> groupID;
    public ArrayList<Integer> postsID;
    public ArrayList<Integer> requestsID;
    public ArrayList<Integer> subscriptionID;
    public ArrayList<String> friendList;
    public ArrayList<String> commentsAsProvider;
    public ArrayList<String> commentsAsConsumer;


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
    public final static String FRIEND_LIST = "friendlist";
    public final static String ALL_FRIENDS = "allFriends";
    public final static String COMMENTS_AS_PROVIDER = "commentsAsProvider";
    public final static String COMMENTS_AS_CONSUMER = "commentsAsConsumer";

}
