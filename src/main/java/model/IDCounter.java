package model;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;

public class IDCounter {
    private static final String NAME = "counters";
    private static DBCollection counters = SharedObject.mi.db.getCollection(NAME);
    private static DBCursor countersCursor= counters.find();


    public final static String USER = "userID";
    public final static String POST = "postID";
    public final static String REQUEST= "requestID";
    public final static String SUBSCRIPTION = "subscriptionID";
    public final static String GROUP_INFO = "groupinfoID";
    public static int incrementTargetID(String whichTable)
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

    public static void initializeCounters()
    {
        BasicDBObject userCount = new BasicDBObject();
        BasicDBObject postCount = new BasicDBObject();
        BasicDBObject requestCount = new BasicDBObject();
        BasicDBObject subscriptionCount = new BasicDBObject();
        BasicDBObject groupinfoCount = new BasicDBObject();

        userCount.put("id", IDCounter.USER);
        userCount.put("count", 0);

        postCount.put("id", IDCounter.POST);
        postCount.put("count", 0);

        requestCount.put("id", IDCounter.REQUEST);
        requestCount.put("count", 0);

        subscriptionCount.put("id", IDCounter.SUBSCRIPTION);
        subscriptionCount.put("count", 0);

        groupinfoCount.put("id", IDCounter.GROUP_INFO);
        groupinfoCount.put("count", 0);


        counters.insert(userCount);
        counters.insert(postCount);
        counters.insert(requestCount);
        counters.insert(subscriptionCount);
        counters.insert(groupinfoCount);

    }


    public static void main (String args[]) {
        initializeCounters();
    }


}
