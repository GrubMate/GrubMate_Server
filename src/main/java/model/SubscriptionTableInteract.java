package model;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import dataClass.Group;
import dataClass.Subscription;
import dataClass.User;

public class SubscriptionTableInteract {
    static public BasicDBObject addSubscription(String subInfo)
    {
        BasicDBObject obj = (BasicDBObject) JSON.parse(subInfo);

        System.out.println("Entering addGroupInfo");

        //int newID = SharedObject.mi.incrementTargetID("groupinfoID");

        int newID = IDCounter.incrementTargetID(IDCounter.SUBSCRIPTION);

        obj.append(Subscription.SUBSCRIPTION_ID, newID);
        SharedObject.mi.groupInfoTable.insert(obj);
        System.out.println("THIS IS TOSTRING        " + obj.get("groupOwnerID") );
        System.out.println("a new group with ID: " + obj.get(Subscription.SUBSCRIPTION_ID));


        /*
        Fill in here to search all matched posts
        and fill these posts into this new subscription.





         */


        return obj;
    }

    public void clearUserTable()
    {
        SharedObject.mi.subscriptionTable.drop();
    }

    public void printUserTable()
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

    }
}
