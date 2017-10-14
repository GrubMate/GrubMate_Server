package model;


import com.mongodb.*;
import dataClass.User;

import java.util.ArrayList;
import java.util.List;

public class UserTableInteract {

    public BasicDBObject addUser(User usr)
    {
        System.out.println("Entering addUser");
        BasicDBObject user = new BasicDBObject();

        int newID = SharedObject.mi.incrementTargetID("userID");

        user.put(User.USER_ID, newID);
        user.put(User.USER_NAME, usr.userName);
        user.put(User.FACEBOOK_ID, usr.facebookID);
        //user.put("profilePhoto", "https://psmedia.playstation.com/is/image/psmedia/meganav-icon-ps4-01-eu-07sep16?$ExploreNav_VisualRow$");
        user.put(User.PROFILE_PHOTO, usr.profilePhoto);

        user.put(User.BIO, usr.bio);

//        BasicDBList ratings = new BasicDBList();
//        ratings.add(new BasicDBObject().append("numOfRatings", 0).append("averageRating", 0)    );
//        user.put("ratings", ratings);

        user.put(User.RATINGS, usr.ratings);

//        BasicDBList allergy = new BasicDBList();
//        allergy.add(new BasicDBObject().append("allergy1", false).append("allergy2", false)    );
//        user.put("allergy", allergy);

        user.put(User.ALLERGY, usr.allergy);



        user.put(User.GROUP_ID, usr.groupID);

        user.put(User.POSTS_ID, usr.postsID);
        user.put(User.REQUESTS_ID, usr.requestsID);
        user.put(User.SUBSCRIPTION_ID, usr.subscriptionID);

//        List<Integer> groupIDs = new ArrayList<Integer>();
//        user.put("groupID", groupIDs);
//
//        List<Integer> postsIDs = new ArrayList<Integer>();
//        user.put("postID", postsIDs);
//
//        List<Integer> requestIDs = new ArrayList<Integer>();
//        user.put("requestID", requestIDs);
//
//        List<Integer> subscriptionIDs = new ArrayList<Integer>();
//        user.put("subscriptionsID", subscriptionIDs);

        SharedObject.mi.userTable.insert(user);

        System.out.println("a new user named: " + usr.userName + " is added!!!");

        return user;
    }

    public BasicDBObject getUser(User user)
    {
        BasicDBObject query = new BasicDBObject("userID", user.userID);

        SharedObject.mi.userCursor = SharedObject.mi.userTable.find(query);

        BasicDBObject answer = (BasicDBObject) SharedObject.mi.userCursor.next();

        return answer;
    }

    public void clearUserTable()
    {
        SharedObject.mi.userTable.drop();
    }

    public void printUserTable()
    {
        DBCursor cursor = SharedObject.mi.userTable.find();
        while (cursor.hasNext())
        {
            DBObject obj = cursor.next();
            System.out.println(obj);
        }
    }

    public static void main(String [] args)
    {
        //SharedObject.createDBObject();
        UserTableInteract uti = new UserTableInteract();

        //uti.printUserTable();

        //uti.clearUserTable();

//        User any = new User();
//
//        any.userName = "dads";
//        any.facebookID = "ddafqwd";
//        any.profilePhoto = "https:wadwad.dadda.com";
//        any.bio = "This user is lazy. No bio!";
//
//        uti.addUser(any);

//        BasicDBObject bo = new BasicDBObject();
//        bo = mi.getUser(123321);
//        System.out.println(bo);

        uti.printUserTable();
    }


}
