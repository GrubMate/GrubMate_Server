package model;

import com.google.gson.Gson;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import dataClass.Group;
import dataClass.Post;
import dataClass.Subscription;
import dataClass.User;

public class SubscriptionTableInteract {
    static public BasicDBObject addSubscription(Subscription sub)
    {
        String subInfo = new Gson().toJson(sub);
        BasicDBObject obj = (BasicDBObject) JSON.parse(subInfo);



        int newID = IDCounter.incrementTargetID(IDCounter.SUBSCRIPTION);

        obj.append(Subscription.SUBSCRIPTION_ID, newID);
        SharedObject.mi.subscriptionTable.insert(obj);

        BasicDBObject targetUser = new BasicDBObject(User.USER_ID, obj.get(Subscription.SUBSCRIBER_ID));

        SharedObject.mi.userCursor = SharedObject.mi.userTable.find(targetUser);


        BasicDBObject getTheAdder = (BasicDBObject) SharedObject.mi.userCursor.next();


        BasicDBList list = (BasicDBList)getTheAdder.get(User.SUBSCRIPTION_ID);
        if(list==null)
        {
            list = new BasicDBList();

        }
        list.add(newID);


        BasicDBObject query = new BasicDBObject();
        query.put(User.USER_ID, obj.get(Subscription.SUBSCRIBER_ID));

        BasicDBObject newDocument = new BasicDBObject();
        newDocument.put(User.USER_ID, obj.get(Subscription.SUBSCRIBER_ID));
        newDocument.put(User.SUBSCRIPTION_ID, list);

        BasicDBObject updateObj = new BasicDBObject();
        updateObj.put("$set", newDocument);

        SharedObject.mi.userTable.update(query, updateObj);





        return obj;
    }

    public void clearSubTable()
    {
        SharedObject.mi.subscriptionTable.drop();
    }

    public void printSubTable()
    {
        DBCursor cursor = SharedObject.mi.subscriptionTable.find();
        while (cursor.hasNext())
        {
            DBObject obj = cursor.next();
            System.out.println(obj);
        }
    }


    public static void main(String [] args)
    {
        SubscriptionTableInteract sti = new SubscriptionTableInteract();
//        UserTableInteract uti = new UserTableInteract();
//
//        Subscription s = new Subscription();
//        s.subscriberID = 7;
//        s.isActive = false;
//
//        String st = new Gson().toJson(s);
//
//        //sti.addSubscription();

        sti.printSubTable();
        //uti.printUserTable();



    }
}
