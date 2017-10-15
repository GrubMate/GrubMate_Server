package model;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;

public class IDCounter {
    private static final String NAME = "counters";
    private static DBCollection counters = SharedObject.mi.db.getCollection(NAME);
    private static DBCursor countersCursor= counters.find();

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


    public final static String USER_ID = "userID";
    public final static String POST_ID = "postID";
    public final static String REQUEST_ID= "requestID";
    public final static String SUBSCRIPTION_ID = "subscriptionID";
    public final static String GROUP_INFO_ID = "groupinfoID";
}
