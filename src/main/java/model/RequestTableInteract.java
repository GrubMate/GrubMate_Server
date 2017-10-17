package model;

import com.google.gson.Gson;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import dataClass.Post;
import dataClass.Request;
import dataClass.User;

import java.util.ArrayList;

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

        int userID = (int)request.get(Request.REQUESTER_ID);
        BasicDBObject user = (BasicDBObject) JSON.parse(new Gson().toJson(UserTableInteract.getUser(userID)));
        BasicDBList lis = (BasicDBList)(user.get(User.REQUESTS_ID));

        if(lis == null)
        {
            lis  = new BasicDBList();
        }

        lis.add(id);
        user.put(User.REQUESTS_ID,lis);

        UserTableInteract.updateUser(user.toString());

        UserTableInteract.printUserTable();



        int postID = (int)request.get(Request.TARGET_POST_ID);
        BasicDBObject post = (BasicDBObject) JSON.parse(new Gson().toJson(PostTableInteract.getPost(postID)));
        BasicDBList list = (BasicDBList)(post.get(Post.REQUESTS_IDS));
        if(list == null)
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
        BasicDBObject query = new BasicDBObject(Request.REQUEST_ID, id);
        SharedObject.mi.requestCursor = SharedObject.mi.requestTable.find(query);
        BasicDBObject result = (BasicDBObject)SharedObject.mi.requestCursor.next();

        String s = new Gson().toJson(result);

        Request u = new Gson().fromJson(s, Request.class);

        return u;
    }

    public ArrayList<Request> getUserRequests(int userID)
    {
        ArrayList<Request> result = new ArrayList<>();
        User user = UserTableInteract.getUser(userID);
        for(int requestID : user.requestsID)
        {
            result.add(getRequest(requestID));
        }
        return result;
    }


    public static void deleteRequest(int id)
    {
        BasicDBObject query = new BasicDBObject(Request.REQUEST_ID,id);
        SharedObject.mi.requestTable.remove(query);
    }

    public void clearRequestTable()
    {
        SharedObject.mi.requestTable.drop();
    }

    public void printRequestTable()
    {
        DBCursor cursor = SharedObject.mi.requestTable.find();
        while (cursor.hasNext())
        {
            DBObject obj = cursor.next();
            System.out.println(obj);
        }
    }



    public static void main(String [] args)
    {
        RequestTableInteract rti = new RequestTableInteract();

        UserTableInteract uti= new UserTableInteract();


        PostTableInteract pti = new PostTableInteract();

        rti.printRequestTable();

//        pti.printPostTable();
//        uti.printUserTable();
//
//        Request req = new Request();
//        req.requesterID = 7;
//        req.targetPostID = 31;
//        req.status = "active";
//
//        rti.printRequestTable();
//
//
//        rti.addRequest(new Gson().toJson(req));
//
//        rti.printRequestTable();
//        uti.printUserTable();
//        pti.printPostTable();


    }


}