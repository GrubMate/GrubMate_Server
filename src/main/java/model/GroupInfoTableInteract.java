package model;

import com.google.gson.Gson;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import dataClass.Group;
import dataClass.Post;
import dataClass.User;

import java.util.ArrayList;
import java.util.Arrays;

public class GroupInfoTableInteract {

    static public Integer addGroupinfo(Group group)
    {
        String groupInfo = new Gson().toJson(group);
        BasicDBObject obj = (BasicDBObject) JSON.parse(groupInfo);

        System.out.println("Entering addGroupInfo");

        int newID = IDCounter.incrementTargetID(IDCounter.GROUP_INFO);

        obj.append(Group.GROUPID, newID);
        SharedObject.mi.groupInfoTable.insert(obj);
        System.out.println("THIS IS TOSTRING        " + obj.get("groupOwnerID") );
        System.out.println("a new group with ID: " + obj.get(Group.GROUPID) + " is added by " + obj.get(Group.GROUP_OWNER_ID));


        BasicDBObject targetUser = new BasicDBObject(User.USER_ID, obj.get(Group.GROUP_OWNER_ID));
        System.out.println("This is the query " + targetUser);


        SharedObject.mi.userCursor = SharedObject.mi.userTable.find(targetUser);


        BasicDBObject getTheAdder = (BasicDBObject) SharedObject.mi.userCursor.next();



        BasicDBList list = (BasicDBList)getTheAdder.get(User.GROUP_ID);

        if(list==null)
        {
            list = new BasicDBList();

        }
        list.add(newID);




        BasicDBObject query = new BasicDBObject();
        query.put(User.USER_ID, obj.get(Group.GROUP_OWNER_ID));

        BasicDBObject newDocument = new BasicDBObject();
        newDocument.put(User.USER_ID, obj.get(Group.GROUP_OWNER_ID));
        newDocument.put(User.GROUP_ID, list);

        BasicDBObject updateObj = new BasicDBObject();
        updateObj.put("$set", newDocument);

        SharedObject.mi.userTable.update(query, updateObj);

        return newID;
    }


    public static void updateGroupInfo(Group group)
    {
        String groupjs = new Gson().toJson(group);

        BasicDBObject obj = (BasicDBObject) JSON.parse(groupjs);

        BasicDBObject query = new BasicDBObject();
        query.put(Group.GROUPID, obj.get(Group.GROUPID));



        BasicDBObject updateObj = new BasicDBObject();
        updateObj.put("$set", obj);


        SharedObject.mi.groupInfoTable.update(query, updateObj);

    }


    public static Group getGroupInfo(Integer groupInfoID) {
        BasicDBObject query = new BasicDBObject(Group.GROUPID, groupInfoID);
        SharedObject.mi.groupInfoCursor = SharedObject.mi.groupInfoTable.find(query);

        if (SharedObject.mi.groupInfoCursor.hasNext()) {
            BasicDBObject answer = (BasicDBObject) SharedObject.mi.groupInfoCursor.next();
            String s = JSON.serialize(answer);
            return new Gson().fromJson(s,Group.class);
        }

        System.out.println("Trying to get non-existing user. returning null");
        return null;
    }

    public void clearGroupInfoTable()
    {
        SharedObject.mi.groupInfoTable.drop();
    }

    public void printGroupInfoTable()
    {
        DBCursor cursor = SharedObject.mi.groupInfoTable.find();
        while (cursor.hasNext())
        {
            DBObject obj = cursor.next();
            System.out.println(obj);
        }
    }


    public static void main(String [] args)
    {
        GroupInfoTableInteract giti = new GroupInfoTableInteract();
        //giti.clearGroupInfoTable();
        giti.printGroupInfoTable();;



    }
}
