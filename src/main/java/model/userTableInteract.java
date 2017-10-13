package model;


import com.mongodb.*;
import dataClass.User;

import java.util.ArrayList;
import java.util.List;

public class userTableInteract {

    public BasicDBObject addUser(User usr)
    {
        System.out.println("Entering addUser");
        BasicDBObject user = new BasicDBObject();

        user.put("userID", usr.userID);
        user.put("userName", usr.userName);
        user.put("facebookID", usr.facebookID);
        user.put("profilePhoto", "https://psmedia.playstation.com/is/image/psmedia/meganav-icon-ps4-01-eu-07sep16?$ExploreNav_VisualRow$");
        user.put("bio", "This user is lazy. No bio.");

        BasicDBList ratings = new BasicDBList();
        ratings.add(new BasicDBObject().append("numOfRatings", 100).append("averageRating", 6)    );
        user.put("ratings", ratings);

        BasicDBList allergy = new BasicDBList();
        ratings.add(new BasicDBObject().append("allergy1", true).append("allergy2", false)    );
        user.put("allergy", allergy);

        //BasicDBList groupID = new BasicDBList();
        //ratings.add(new BasicDBObject().append("group", 100).append("averageRating", 6)    );

        List<String> groupIDs = new ArrayList<String>();
        user.put("groupID", groupIDs);

        SharedObject.mi.userTable.insert(user);

        //System.out.println("a new user named: " + usr.userName + " is added!!!");

        return user;
    }

    public BasicDBObject getUser(Integer userID)
    {
        BasicDBObject user = new BasicDBObject("userID", userID);

        SharedObject.mi.userCursor = SharedObject.mi.userTable.find(user);

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
        userTableInteract uti = new userTableInteract();

        //uti.printUserTable();

        uti.clearUserTable();

        User any = new User();
        any.userID = 999999;
        any.userName = "dads";
        any.facebookID = "ddafqwd";

        uti.addUser(any);

//        BasicDBObject bo = new BasicDBObject();
//        bo = mi.getUser(123321);
//        System.out.println(bo);

        uti.printUserTable();
    }


}
