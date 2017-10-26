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
import javafx.geometry.Pos;
import org.springframework.boot.json.GsonJsonParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PostTableInteract {

    public static int addPost(Post post)
    {
        return addPost(new Gson().toJson(post));
    }


    static public int addPost(String post) {
        BasicDBObject obj = (BasicDBObject) JSON.parse(post);

        int newID = IDCounter.incrementTargetID(IDCounter.POST);
        obj.append(Post.POST_ID, newID);
        SharedObject.mi.postTable.insert(obj);

        BasicDBObject targetUser = new BasicDBObject(User.USER_ID, obj.get(Post.POSTER_ID));

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

        return newID;
    }

    public static Post getPost(Integer postID) {
        BasicDBObject query = new BasicDBObject(Post.POST_ID, postID);
        SharedObject.mi.postCursor = SharedObject.mi.postTable.find(query);

        if (SharedObject.mi.postCursor.hasNext()){
            BasicDBObject answer = (BasicDBObject) SharedObject.mi.postCursor.next();
            String s = JSON.serialize(answer);
            Post p = new Gson().fromJson(s, Post.class);
            return p;
        }

        System.out.println("Trying to get non-existing post. returning null");
        return null;
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

    public static ArrayList<Post> searchPost(SearchRequest searchRequest)
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


    public static void deletePost(int id)
    {
        BasicDBObject target = new BasicDBObject();
        target.put(Post.POST_ID, id);

        Post p = getPost(id);
        String s = new Gson().toJson(p);
        BasicDBObject obj = (BasicDBObject) JSON.parse(s);


        Integer posterID = (Integer)obj.get(Post.POSTER_ID);
        System.out.println("poster id is "+ p.posterID);

        User targetU = UserTableInteract.getUser(posterID);
        System.out.println(targetU.postsID);
        ArrayList<Integer> postList = targetU.postsID;
        System.out.println( postList.toString() );

        if (targetU.postsID == null)
        {
            System.out.println("Error. No posts list!!!");
            return;
        }
        else
        {




            for(int i = 0; i < targetU.postsID.size(); i++)
            {
                if((Integer)targetU.postsID.get(i) == id)
                {
                    targetU.postsID.remove((Integer)targetU.postsID.get(i));
                }
            }

            for(int i = 0; i < targetU.postsID.size(); i++)
            {
                System.out.println(targetU.postsID.get(i));

            }

        }



        String ss = new Gson().toJson(targetU);
        UserTableInteract.updateUser(ss);



        SharedObject.mi.postTable.remove(target);

//        BasicDBObject toDelete = getPost(post);
//        System.out.println(toDelete);
//        SharedObject.mi.postTable.remove(toDelete);

    }

    public static  ArrayList<Post> getUserPosts(int userID)
    {
        ArrayList<Post> result = new ArrayList<>();
        User user = UserTableInteract.getUser(userID);
        for(int postID : user.postsID)
        {
            System.out.println("post id" + postID);
            result.add(getPost(postID));
        }
        return result;
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
        PostTableInteract pti = new PostTableInteract();


        Post post = PostTableInteract.getPost(100);
//        post.leftQuantity = 0;
//        post.isActive = false;
//        PostTableInteract.updatePost(post);
        //pti.printPostTable();


    }

}