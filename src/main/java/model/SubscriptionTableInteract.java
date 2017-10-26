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

import java.util.ArrayList;

public class SubscriptionTableInteract {

    public static BasicDBObject addSubscription(Subscription sub)
    {
        return addSubscription(new Gson().toJson(sub));
    }


    public static BasicDBObject addSubscription(String subInfo)
    {
        BasicDBObject obj = (BasicDBObject) JSON.parse(subInfo);


        //int newID = SharedObject.mi.incrementTargetID("groupinfoID");

        int newID = IDCounter.incrementTargetID(IDCounter.SUBSCRIPTION);

        obj.append(Subscription.SUBSCRIPTION_ID, newID);
        SharedObject.mi.subscriptionTable.insert(obj);


        BasicDBObject targetUser = new BasicDBObject(User.USER_ID, obj.get(Subscription.SUBSCRIBER_ID));
        SharedObject.mi.userCursor = SharedObject.mi.userTable.find(targetUser);


        BasicDBObject getTheAdder = (BasicDBObject) SharedObject.mi.userCursor.next();


        BasicDBList list = (BasicDBList)getTheAdder.get(User.SUBSCRIPTION_ID);
        if(list == null)
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

    public static Subscription getSubscription(Integer subID)
    {
        BasicDBObject query = new BasicDBObject(Subscription.SUBSCRIPTION_ID, subID);

        SharedObject.mi.subscriptionCursor = SharedObject.mi.subscriptionTable.find(query);

        BasicDBObject answer = (BasicDBObject) SharedObject.mi.subscriptionCursor.next();

        String s = JSON.serialize(answer);

        Subscription su = new Gson().fromJson(s, Subscription.class);

        return su;
    }

    public static  ArrayList<Subscription> getUserSubscriptions(int userID)
    {
        ArrayList<Subscription> result = new ArrayList<Subscription>();
        User user = UserTableInteract.getUser(userID);
        if (user.subscriptionID == null) {
            return result;
        }
        for(int subID : user.subscriptionID)
        {
            result.add(getSubscription(subID));
        }
        System.out.println(new Gson().toJson(result));
        return result;
    }

    public static void updateSubscription(Subscription s)
    {
        String sub = new Gson().toJson(s);
        BasicDBObject obj = (BasicDBObject) JSON.parse(sub);

        BasicDBObject query = new BasicDBObject();
        query.put(Subscription.SUBSCRIPTION_ID, obj.get(Subscription.SUBSCRIPTION_ID));



        BasicDBObject updateObj = new BasicDBObject();
        updateObj.put("$set", obj);


        SharedObject.mi.subscriptionTable.update(query, updateObj);
    }


    public static void deleteSubscription(int id)
    {
        BasicDBObject target = new BasicDBObject();
        target.put(Subscription.SUBSCRIPTION_ID, id);

        Subscription sub = getSubscription(id);
        String s = new Gson().toJson(sub);
        BasicDBObject obj = (BasicDBObject) JSON.parse(s);


        Integer subscriberID = (Integer)obj.get(Subscription.SUBSCRIBER_ID);


        User targetU = UserTableInteract.getUser(subscriberID);
        System.out.println(targetU.subscriptionID);
        ArrayList<Integer> subList = targetU.subscriptionID;
        System.out.println( subList.toString() );

        if (targetU.subscriptionID == null)
        {
            System.out.println("Error. No posts list!!!");
            return;
        }
        else
        {
            for(int i = 0; i < targetU.subscriptionID.size(); i++)
            {
                if((Integer)targetU.subscriptionID.get(i) == id)
                {
                    targetU.subscriptionID.remove((Integer)targetU.subscriptionID.get(i));
                }
            }

            for(int i = 0; i < targetU.subscriptionID.size(); i++)
            {
                System.out.println(targetU.subscriptionID.get(i));

            }
        }


        String ss = new Gson().toJson(targetU);
        UserTableInteract.updateUser(ss);



        SharedObject.mi.subscriptionTable.remove(target);

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

    public static ArrayList<Subscription> getAllSubscriptions() {
        DBCursor cursor = SharedObject.mi.subscriptionTable.find();
        ArrayList<Subscription> ret = new ArrayList<Subscription>();
        while (cursor.hasNext())
        {
            DBObject obj = cursor.next();
            String str = JSON.serialize(obj);
            Subscription s = new Gson().fromJson(str, Subscription.class);
            ret.add(s);
        }
        return ret;
    }


    public static void main(String [] args)
    {
        SubscriptionTableInteract sti = new SubscriptionTableInteract();

        sti.printSubTable();



    }
}