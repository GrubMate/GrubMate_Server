package model;

import java.util.ArrayList;
import java.util.List;

import dataClass.User;


import com.mongodb.*;


public class MongoInitializer {
    public Mongo mg;
    public DB db;
    public DBCollection userTable, postTable, subscriptionTable,
            requestTable, groupInfoTable;
    public DBCursor userCursor, postCursor, subscriptionCursor,
            requestCursor, groupInfoCursor;

    public MongoInitializer()
    {
        mg = new Mongo("localhost", 27017);
        db = mg.getDB("GrubMate");

        //creating/getting tables/collection
        userTable = db.getCollection("userTable");
        postTable = db.getCollection("postTable");
        subscriptionTable = db.getCollection("subscriptionTable");
        requestTable = db.getCollection("requestTable");
        groupInfoTable = db.getCollection("grouInfoTable");

        //create iterators for these tables
        userCursor = userTable.find();
        postCursor = postTable.find();
        subscriptionCursor = subscriptionTable.find();
        requestCursor = requestTable.find();
        groupInfoCursor = groupInfoTable.find();
    }

    public BasicDBObject addUser(User usr)
    {
        System.out.println("Entering addUser");
        BasicDBObject user = new BasicDBObject();

        user.put("userID", usr.userID);
        user.put("userName", usr.userName);
        user.put("facebookID", usr.facebookID);
        user.put("profilePhoto", "https://psmedia.playstation.com/is/image/psmedia/meganav-icon-ps4-01-eu-07sep16?$ExploreNav_VisualRow$");
        user.put("bio", "Hello I am John Doe");

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

        System.out.println("yyeyeyeeyeyey" + user);

        userTable.insert(user);

        System.out.println("a new user named: " + usr.userName + " is added!!!");

        return user;
    }

    public BasicDBObject getUser(Integer userID)
    {
        BasicDBObject user = new BasicDBObject("userID", userID);

        userCursor = userTable.find(user);

        BasicDBObject answer = (BasicDBObject)userCursor.next();

        return answer;
    }

    public static void main(String [] args)
    {
        MongoInitializer mi;
        mi = new MongoInitializer();

        User any = new User();
        any.userID = 1121231;
        any.userName = "dads";
        any.facebookID = "ddafqwd";

        mi.addUser(any);


        //mi.addUser("hello");

//        BasicDBObject bo = new BasicDBObject();
//        bo = mi.getUser(123321);
//
//
//
//        System.out.println(bo);

        DBCursor cursor = mi.userTable.find();
        while (cursor.hasNext()) {
            DBObject obj = cursor.next();
            System.out.print(obj);
            //do your thing
        }
    }



}
