package model;


import com.mongodb.*;
import dataClass.User;

import java.util.ArrayList;
import java.util.List;

public class UserTableInteract {

    static public BasicDBObject addUser(User usr)
    {
        return null;
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
