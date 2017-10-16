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
import dataClass.SearchRequest;
import dataClass.User;
import org.springframework.boot.json.GsonJsonParser;

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
    public static BasicDBObject addPost(Post post)
    {
        return addPost(new Gson().toJson(post));
    }


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

    public static Post getPost(Integer postID)
    {
        BasicDBObject query = new BasicDBObject(Post.POST_ID, postID);

        SharedObject.mi.postCursor = SharedObject.mi.postTable.find(query);

        BasicDBObject answer = (BasicDBObject) SharedObject.mi.postCursor.next();

        String s = JSON.serialize(answer);

        Post p = new Gson().fromJson(s, Post.class);

        return p;
    }

    public static ArrayList<Post> getAllVisiblePosts(Integer userID)
    {
        ArrayList<Post> result = new ArrayList<>();
        User user  = UserTableInteract.getUser(userID);

        //go though each of his frineds
        for(int friendID : user.allFriends)
        {
            User friend = UserTableInteract.getUser(friendID);
            //check every post of his frineds
            for(int postID : friend.postsID)
            {
                Post post = PostTableInteract.getPost(postID);
                String pp = new Gson().toJson(post);
                System.out.println(pp);
                if(post.isActive)
                {
                    if(post.groupIDs==null || post.groupIDs.get(0)==null)
                    {
                        result.add(post);
                    }
                    else
                    {
                        for (int groupID : post.groupIDs) {
                            Group visibleGroup = GroupInfoTableInteract.getGroupInfo(groupID);
                            if (visibleGroup.hasUser(user.userID)) {
                                result.add(post);
                            }
                        }
                    }
                }

            }
        }
        return result;
    }

    public ArrayList<Post> searchPost(SearchRequest searchRequest)
    {
        ArrayList<Post> result = new ArrayList<>();
        ArrayList<Post> postPools = getAllVisiblePosts(searchRequest.userID);
        for(Post post : postPools)
        {
            if(searchRequest.match(post))
            {
                result.add(post);
            }
        }
        return result;
    }



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

    public static void updatePost(Post p)
    {
        updatePost(new Gson().toJson(p));
    }


    public static void updatePost(String post)
    {

        BasicDBObject obj = (BasicDBObject) JSON.parse(post);

        BasicDBObject query = new BasicDBObject();
        query.put(Post.POST_ID, obj.get(Post.POST_ID));
        


        BasicDBObject updateObj = new BasicDBObject();
        updateObj.put("$set", obj);


        SharedObject.mi.postTable.update(query, updateObj);

    }


    public void deletePost(int id)
    {
        BasicDBObject target = new BasicDBObject();
        target.put(Post.POST_ID, id);
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
        //UserTableInteract uti = new UserTableInteract();





        pti.printPostTable();


    }

}