package model;


import com.google.gson.Gson;
import com.mongodb.*;
import com.mongodb.util.JSON;
import dataClass.Post;
import dataClass.User;

import java.util.ArrayList;
import java.util.List;

public class UserTableInteract {

    public static Integer addUser(User u)
    {
        //convert the passed in user obj to a json
        //and then convert this new json to BasicDBObject to interact with DB
        String usr = new Gson().toJson(u);
        BasicDBObject obj = (BasicDBObject) JSON.parse(usr);




        BasicDBObject checkFBid = new BasicDBObject(User.FACEBOOK_ID, obj.get(User.FACEBOOK_ID));

        SharedObject.mi.userCursor = SharedObject.mi.userTable.find(checkFBid);

        int newID;
        if(SharedObject.mi.userCursor.hasNext())
        {
            BasicDBObject user = (BasicDBObject)SharedObject.mi.userCursor.next();
            newID = (Integer)user.get(User.USER_ID);


            u.userID = newID;
            //updateUser(u);
            onlyUpdateUserFriendlist(u);
            onlyUpdateAllFriends(u);


        }
        else
        {
            obj.append(User.POSTS_ID, new ArrayList<Integer>());
            newID = IDCounter.incrementTargetID(IDCounter.USER);
            obj.append(User.USER_ID, newID);
            SharedObject.mi.userTable.insert(obj);

        }


        return newID;
    }


    public static void onlyUpdateUserFriendlist(User u)
    {
        String updatedUserObjJson = new Gson().toJson(u);
        BasicDBObject updatedUserObj = (BasicDBObject) JSON.parse(updatedUserObjJson);

        BasicDBObject query = new BasicDBObject();
        query.put(User.USER_ID, updatedUserObj.get(User.USER_ID));

        BasicDBObject newDocument = new BasicDBObject();
        newDocument.put(User.USER_ID, updatedUserObj.get(User.USER_ID));
        newDocument.put(User.FRIEND_LIST, updatedUserObj.get(User.FRIEND_LIST));

        BasicDBObject updateObj = new BasicDBObject();
        updateObj.put("$set", newDocument);

        SharedObject.mi.userTable.update(query, updateObj);
    }

    public static void onlyUpdateAllFriends(User u)
    {
        String updatedUserObjJson = new Gson().toJson(u);
        BasicDBObject updatedUserObj = (BasicDBObject) JSON.parse(updatedUserObjJson);

        BasicDBObject query = new BasicDBObject();
        query.put(User.USER_ID, updatedUserObj.get(User.USER_ID));

        BasicDBObject newDocument = new BasicDBObject();
        newDocument.put(User.USER_ID, updatedUserObj.get(User.USER_ID));
        newDocument.put(User.ALL_FRIENDS, updatedUserObj.get(User.ALL_FRIENDS));

        BasicDBObject updateObj = new BasicDBObject();
        updateObj.put("$set", newDocument);

        SharedObject.mi.userTable.update(query, updateObj);
    }

    public static void updateUser(User user)
    {
        updateUser(new Gson().toJson(user));
    }


    public static void updateUser(String user)
    {
        BasicDBObject obj = (BasicDBObject) JSON.parse(user);

        BasicDBObject query = new BasicDBObject();
        query.put(User.USER_ID, obj.get(User.USER_ID));



        BasicDBObject updateObj = new BasicDBObject();
        updateObj.put("$set", obj);


        SharedObject.mi.userTable.update(query, updateObj);

    }

    public static User getUser(Integer id) {
        BasicDBObject query = new BasicDBObject("userID", id);
        SharedObject.mi.userCursor = SharedObject.mi.userTable.find(query);

        if (SharedObject.mi.userCursor.hasNext()) {
            BasicDBObject answer = (BasicDBObject) SharedObject.mi.userCursor.next();
            String s = JSON.serialize(answer);
            User u = new Gson().fromJson(s, User.class);
            return u;
        }

        System.out.println("Trying to get non-existing user. returning null");
        return null;
    }

    public void clearUserTable()
    {
        SharedObject.mi.userTable.drop();
    }

    public static void printUserTable()
    {
        DBCursor cursor = SharedObject.mi.userTable.find();
        while (cursor.hasNext())
        {
            DBObject obj = cursor.next();
            System.out.println(obj);
        }
    }


    public static Integer getUserIDFromFBID(String fbid)
    {
        BasicDBObject query = new BasicDBObject(User.FACEBOOK_ID, fbid);

        SharedObject.mi.userCursor = SharedObject.mi.userTable.find(query);


        Integer id;
        if(SharedObject.mi.userCursor.hasNext())
        {
            BasicDBObject answer = (BasicDBObject) SharedObject.mi.userCursor.next();
            id = (Integer) answer.get(User.USER_ID);

        }
        else
        {
            id = null;
        }


        return id;
    }

    public static void main(String [] args)
    {
        //SharedObject.createDBObject();
        UserTableInteract uti = new UserTableInteract();

//        uti.printUserTable();
//
//        //uti.clearUserTable();
////
//        User any = new User();
//        //any.userID = 14;
//        //any.userName = "Jie Ji";
//        any.facebookID = "daqiqiqiqiqiqi";
//        //any.profilePhoto = "https:dadada.dadda.com";
//        //any.bio = "This user bad!";
//
//        String[] a = {"c", "sa", "sadad", "dadadadadwwe", "qquququuq"};
//
//        any.friendList = a;



       // uti.addUser(any);
//
//        String s = new Gson().toJson(any);
//        uti.updateUser(s);
////        BasicDBObject bo = new BasicDBObject();
////        bo = mi.getUser(123321);
////        System.out.println(bo);
        //uti.clearUserTable();
        //User user = getUser(8);
        //user.postsID = new ArrayList<Integer>();
        //updateUser(user);

        uti.printUserTable();
        //System.out.println(getUserIDFromFBID("1852883008374531"));
    }


}