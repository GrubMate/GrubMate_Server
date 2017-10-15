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



    //public DBCollection counters; // store counters of each table
                                    // use: to generate id
    //public DBCursor countersCursor;

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
        //counters = db.getCollection("counters");


        //create iterators for these tables
        userCursor = userTable.find();
        postCursor = postTable.find();
        subscriptionCursor = subscriptionTable.find();
        requestCursor = requestTable.find();
        groupInfoCursor = groupInfoTable.find();
       //countersCursor = counters.find();

    }
/*
    public void initializeCounters()
    {
        BasicDBObject userCount = new BasicDBObject();
        BasicDBObject postCount = new BasicDBObject();
        BasicDBObject requestCount = new BasicDBObject();
        BasicDBObject subscriptionCount = new BasicDBObject();
        BasicDBObject groupinfoCount = new BasicDBObject();

        userCount.put("id", IDCounter.USER_ID);
        userCount.put("count", 0);

        postCount.put("id", IDCounter.POST_ID);
        postCount.put("count", 0);

        requestCount.put("id", IDCounter.REQUEST_ID);
        requestCount.put("count", 0);

        subscriptionCount.put("id", IDCounter.SUBSCRIPTION_ID);
        subscriptionCount.put("count", 0);

        groupinfoCount.put("id", IDCounter.GROUP_INFO_ID);
        groupinfoCount.put("count", 0);


        counters.insert(userCount);
        counters.insert(postCount);
        counters.insert(requestCount);
        counters.insert(subscriptionCount);
        counters.insert(groupinfoCount);

    }
    */
/*
    public int incrementTargetID(String whichTable)
    {
        BasicDBObject target = new BasicDBObject("id", whichTable);
        countersCursor = counters.find(target);
        BasicDBObject targetUser = (BasicDBObject) countersCursor.next();

        int originalValue = targetUser.getInt("count");
        int newValue = originalValue + 1;

        BasicDBObject query = new BasicDBObject();
        query.put("id", whichTable);
        query.put("count", originalValue);

        BasicDBObject newDocument = new BasicDBObject();
        newDocument.put("id", whichTable);
        newDocument.put("count", newValue);

        BasicDBObject updated = new BasicDBObject();
        updated.put("$set", newDocument);

        counters.update(query, updated);

        return newValue;
    }
*/
/*
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
*/
    public void clearUserTable()
    {
        userTable.drop();
    }

    /*
    public void printUserTable()
    {
        DBCursor cursor = counters.find();
        while (cursor.hasNext())
        {
            DBObject obj = cursor.next();
            System.out.println(obj);
        }
    }
*/
    public static void main(String [] args)
    {
        MongoInitializer mi;
        mi = new MongoInitializer();


//        mi.incrementUserID();

//        mi.initializeCounters();

//        //mi.printUserTable();
//
//        User any = new User();
//        any.userID = 999999;
//        any.userName = "dads";
//        any.facebookID = "ddafqwd";
//
//        mi.addUser(any);
//
////        BasicDBObject bo = new BasicDBObject();
////        bo = mi.getUser(123321);
////        System.out.println(bo);
//
        //mi.printUserTable();

    }



}
