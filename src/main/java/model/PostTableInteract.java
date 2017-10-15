package model;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import dataClass.Group;
import dataClass.Post;
import dataClass.User;
import javafx.geometry.Pos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PostTableInteract {

//    static public BasicDBObject addPost(Post post)
//    {
//        System.out.println("Entering addUser");
//        BasicDBObject newPost = new BasicDBObject();
//
//        int newID = SharedObject.mi.incrementTargetID("postID");
//
//        newPost.put(Post.POST_ID, newID);
//        newPost.put(Post.POSTER_ID, post.posterID);
//
//
//
////        BasicDBList allergy = new BasicDBList();
////        allergy.add(new BasicDBObject().append("allergy1", true).append("allergy2", false)    );
////        user.put("allergy", allergy);
//
//        newPost.put(Post.TITLE, post.title);
//        newPost.put(Post.IS_HOMEMADE, post.isHomeMade);
//        newPost.put(Post.GROUP_IDS, post.groupIDs);
//
//        newPost.put(Post.POST_PHOTOS, post.postPhotos);
//
//        newPost.put(Post.TAGS, post.tags);
//        newPost.put(Post.CATEGORY, post.category);
//        newPost.put(Post.TIME_PERIOD, post.timePeriod);
//        newPost.put(Post.DESCRIPTION, post.description);
//        newPost.put(Post.ADDRESS, post.address);
//        newPost.put(Post.TOTAL_QUANTITY, post.totalQuantity);
//        newPost.put(Post.LEFT_QUANTITY, post.leftQuantity);
//        newPost.put(Post.REQUESTS_IDS, post.requestsIDs);
//        newPost.put(Post.IS_ACTIVE, post.isActive);
//        newPost.put(Post.ALLERGY_INFO, post.allergyInfo);
//
//
//
//
//
//        SharedObject.mi.postTable.insert(newPost);
//
//        System.out.println("a new user with ID: " + post.postID + " is added by " + post.posterID);
//
//        return newPost;
//    }


    static public BasicDBObject addPost(String post)
    {
        BasicDBObject obj = (BasicDBObject) JSON.parse(post);

        System.out.println("Entering addUser");

        //int newID = SharedObject.mi.incrementTargetID("postID");

        int newID = IDCounter.incrementTargetID(IDCounter.POST);
        obj.append(Post.POST_ID, newID);
        SharedObject.mi.postTable.insert(obj);

        System.out.println("a new post with ID: " + obj.get(Post.POST_ID) + " is added by " + obj.get(Post.POSTER_ID));


        BasicDBObject targetUser = new BasicDBObject(User.USER_ID, obj.get(Post.POSTER_ID));
        System.out.println("This is the query " + targetUser);
        SharedObject.mi.userCursor = SharedObject.mi.userTable.find(targetUser);


        BasicDBObject getTheAdder = (BasicDBObject) SharedObject.mi.userCursor.next();


        BasicDBList list = (BasicDBList)getTheAdder.get(User.POSTS_ID);
        if(list==null)
        {
            list = new BasicDBList();

        }
        list.add(newID);


        BasicDBObject query = new BasicDBObject();
        query.put(User.USER_ID, obj.get(Post.POSTER_ID));

        BasicDBObject newDocument = new BasicDBObject();
        newDocument.put(User.USER_ID, obj.get(Post.POSTER_ID));
        newDocument.put(User.POSTS_ID, list);

        BasicDBObject updateObj = new BasicDBObject();
        updateObj.put("$set", newDocument);

        SharedObject.mi.userTable.update(query, updateObj);

        return obj;
    }

    public String getPost(Integer postID)
    {
        BasicDBObject query = new BasicDBObject(Post.POST_ID, postID);

        SharedObject.mi.postCursor = SharedObject.mi.postTable.find(query);

        BasicDBObject answer = (BasicDBObject) SharedObject.mi.postCursor.next();

        String s = JSON.serialize(answer);
        return s;
    }

//    public String[] getAllVisiblePosts(Integer userID)
//    {
//        JsonObject output =
//    }



    public BasicDBObject toPostObj(Post post)
    {
        BasicDBObject newPost = new BasicDBObject();

        newPost.put(Post.TITLE, post.title);
        newPost.put(Post.IS_HOMEMADE, post.isHomeMade);
        newPost.put(Post.GROUP_IDS, post.groupIDs);

        newPost.put(Post.POST_PHOTOS, post.postPhotos);

        newPost.put(Post.TAGS, post.tags);
        newPost.put(Post.CATEGORY, post.category);
        newPost.put(Post.TIME_PERIOD, post.timePeriod);
        newPost.put(Post.DESCRIPTION, post.description);
        newPost.put(Post.ADDRESS, post.address);
        newPost.put(Post.TOTAL_QUANTITY, post.totalQuantity);
        newPost.put(Post.LEFT_QUANTITY, post.leftQuantity);
        newPost.put(Post.REQUESTS_IDS, post.requestsIDs);
        newPost.put(Post.IS_ACTIVE, post.isActive);
        newPost.put(Post.ALLERGY_INFO, post.allergyInfo);

        return newPost;
    }

    public void updatePost(String post)
    {
        //BasicDBObject target = new BasicDBObject(Post.POST_ID, post.postID);

        BasicDBObject obj = (BasicDBObject) JSON.parse(post);

        BasicDBObject query = new BasicDBObject();
        query.put(Post.POST_ID, obj.get(Post.POST_ID));
        


        BasicDBObject updateObj = new BasicDBObject();
        updateObj.put("$set", obj);


        SharedObject.mi.postTable.update(query, updateObj);

    }


    public void deletePost(Post post)
    {
        BasicDBObject target = new BasicDBObject();
        target.put(Post.POST_ID, post.postID);
        SharedObject.mi.postTable.remove(target);

//        BasicDBObject toDelete = getPost(post);
//        System.out.println(toDelete);
//        SharedObject.mi.postTable.remove(toDelete);

    }

    public void clearPostTable()
    {
        SharedObject.mi.postTable.drop();
    }

    public void printPostTable()
    {
        DBCursor cursor = SharedObject.mi.postTable.find();
        while (cursor.hasNext())
        {
            DBObject obj = cursor.next();
            System.out.println(obj);
        }
    }

    public static void main(String [] args)
    {
        //SharedObject.createDBObject();
        PostTableInteract pti = new PostTableInteract();
        UserTableInteract uti = new UserTableInteract();


        //uti.printPostTable();

        //pti.clearPostTable();

//        Post any = new Post();
//        any.postID = 20;
//        any.posterID = 777;
//
//        pti.addPost(any);

        pti.printPostTable();
        uti.printUserTable();



        Post p = new Post();
        p.posterID = 7;
        p.title = "Test";

        Gson gson  = new Gson();
        String s = gson.toJson(p);

        addPost(s);




        Post pp = new Post();
        pp.postID = 22;
        pp.posterID = 7;
        pp.title = "wuwuwuwuwuuwuwuwuwu";
        pp.isHomeMade = false;


        Gson gso = new Gson();
        String ss = gso.toJson(pp);

        pti.updatePost(ss);

        pti.printPostTable();


        uti.printUserTable();


    }

}