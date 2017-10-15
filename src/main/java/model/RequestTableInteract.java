package model;

import com.mongodb.BasicDBObject;
import com.mongodb.util.JSON;
import dataClass.Request;

public class RequestTableInteract {
    public BasicDBObject addRequest(String requestJson)
    {
        BasicDBObject request = (BasicDBObject) JSON.parse(requestJson);
        return null;
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
