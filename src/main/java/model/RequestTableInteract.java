package model;

import com.google.gson.Gson;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.util.JSON;
import dataClass.Post;
import dataClass.Request;

public class RequestTableInteract {

    public static BasicDBObject addRequest(Request request)
    {
        return addRequest(new Gson().toJson(request));
    }

    public static BasicDBObject addRequest(String requestJson)
    {
        //insert request
        BasicDBObject request = (BasicDBObject) JSON.parse(requestJson);
        int id = IDCounter.incrementTargetID(IDCounter.REQUEST);
        request.append(Request.REQUEST_ID,id);
        SharedObject.mi.requestTable.insert(request);

        int postID = (int)request.get(Request.TARGET_POST_ID);
        BasicDBObject post = (BasicDBObject) JSON.parse(new Gson().toJson(PostTableInteract.getPost(postID)));
        BasicDBList list = (BasicDBList)(post.get(Post.REQUESTS_IDS));
        if(list==null)
        {
            list = new BasicDBList();
        }
        list.add(id);
        post.put(Post.REQUESTS_IDS,list);

        PostTableInteract.updatePost(post.toString());

        return request;
    }

    public static Request getRequest(int id)
    {
        BasicDBObject query = new BasicDBObject(Request.REQUEST_ID,id);
        SharedObject.mi.requestCursor = SharedObject.mi.requestTable.find(query);
        BasicDBObject result = (BasicDBObject)SharedObject.mi.requestCursor.next();
        return new Gson().fromJson(result.toString(),Request.class);
    }

    public static void deleteRequest(int id)
    {
        BasicDBObject query = new BasicDBObject(Request.REQUEST_ID,id);
        SharedObject.mi.requestTable.remove(query);
    }


}
