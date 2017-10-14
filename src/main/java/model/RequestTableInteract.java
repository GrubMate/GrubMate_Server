package model;

import com.mongodb.BasicDBObject;
import dataClass.Request;

public class RequestTableInteract {
    public BasicDBObject addRequest(Request newRequest)
    {
        BasicDBObject request = toRequestObj(newRequest);
        SharedObject.mi.requestTable.insert(request);
        return request;
    }



    public BasicDBObject toRequestObj(Request newRequest)
    {
        BasicDBObject request = new BasicDBObject();
        request.put(Request.REQUEST_ID, newRequest.requestID );
        request.put(Request.REQUESTER_ID,newRequest.requesterID);
        request.put(Request.TARGET_POST_ID,newRequest.targetPostID);
        request.put(Request.STATUS,newRequest.status);
        request.put(Request.ADDRESS,newRequest.address);
        return request;
    }
}
