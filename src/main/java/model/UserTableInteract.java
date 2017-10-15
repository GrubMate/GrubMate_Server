package model;


import com.google.gson.Gson;
import com.mongodb.*;
import com.mongodb.util.JSON;
import dataClass.Post;
import dataClass.User;

import java.util.ArrayList;
import java.util.List;

public class UserTableInteract {

    static public BasicDBObject addUser(User u)
    {
        String usr = new Gson().toJson(u);

        BasicDBObject obj = (BasicDBObject) JSON.parse(usr);

        System.out.println("Entering addUser");

        //int newID = SharedObject.mi.incrementTargetID("postID");


        BasicDBObject checkFBid = new BasicDBObject(User.FACEBOOK_ID, obj.get(User.FACEBOOK_ID));

        SharedObject.mi.userCursor = SharedObject.mi.userTable.find(checkFBid);

        int newID;
        if(SharedObject.mi.userCursor.hasNext())
        {
            updateUser(usr);
            System.out.println("a new user with ID: " + obj.get(User.USER_ID) + " is now being updated");

        }
        else
        {
            newID = IDCounter.incrementTargetID(IDCounter.USER);
            obj.append(User.USER_ID, newID);
            SharedObject.mi.userTable.insert(obj);

            System.out.println("a new user with ID: " + obj.get(User.USER_ID));

        }



        return obj;
    }


    static public void updateUser(String user)
    {
        BasicDBObject obj = (BasicDBObject) JSON.parse(user);

        BasicDBObject query = new BasicDBObject();
        query.put(User.USER_ID, obj.get(User.USER_ID));



        BasicDBObject updateObj = new BasicDBObject();
        updateObj.put("$set", obj);


        SharedObject.mi.userTable.update(query, updateObj);

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

        User any = new User();
        any.userID = 9;
        any.userName = "qwqewqew";
        any.facebookID = "iii";
        any.profilePhoto = "https:dadada.dadda.com";
        any.bio = "This user i!";

        //uti.addUser(any);

        String s = new Gson().toJson(any);
        uti.updateUser(s);
//        BasicDBObject bo = new BasicDBObject();
//        bo = mi.getUser(123321);
//        System.out.println(bo);

        uti.printUserTable();
    }


}