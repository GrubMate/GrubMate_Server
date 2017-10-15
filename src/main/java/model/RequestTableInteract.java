package model;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.util.JSON;
import dataClass.Post;
import dataClass.Request;

public class RequestTableInteract {
    public static BasicDBObject addRequest(String requestJson)
    {
        //insert request
        BasicDBObject request = (BasicDBObject) JSON.parse(requestJson);
        int id = IDCounter.incrementTargetID(IDCounter.REQUEST);
        request.append(Request.REQUEST_ID,id);
        SharedObject.mi.requestTable.insert(request);

        int postID = (int)request.get(Request.TARGET_POST_ID);
        BasicDBObject post = (BasicDBObject) JSON.parse(PostTableInteract.getPost(postID));
        ((BasicDBList)(post.get(Post.REQUESTS_IDS))).add(postID);
        PostTableInteract.

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
