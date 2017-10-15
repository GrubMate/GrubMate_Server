package model;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import dataClass.Post;
import javafx.geometry.Pos;

public class PostTableInteract {

    static public BasicDBObject addPost(Post post)
    {
        System.out.println("Entering addUser");
        BasicDBObject newPost = new BasicDBObject();

        int newID = SharedObject.mi.incrementTargetID("postID");

        newPost.put(Post.POST_ID, newID);
        newPost.put(Post.POSTER_ID, post.posterID);



//        BasicDBList allergy = new BasicDBList();
//        allergy.add(new BasicDBObject().append("allergy1", true).append("allergy2", false)    );
//        user.put("allergy", allergy);

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





        SharedObject.mi.postTable.insert(newPost);

        System.out.println("a new user with ID: " + post.postID + " is added by " + post.posterID);

        return newPost;
    }

    public BasicDBObject getPost(Post post)
    {
        BasicDBObject query = new BasicDBObject(Post.POST_ID, post.postID);

        SharedObject.mi.postCursor = SharedObject.mi.postTable.find(query);

        BasicDBObject answer = (BasicDBObject) SharedObject.mi.postCursor.next();

        return answer;
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

    public void updatePost(Post post)
    {
        BasicDBObject target = new BasicDBObject(Post.POST_ID, post.postID);

        DBCursor c = SharedObject.mi.postCursor;

        c = SharedObject.mi.postTable.find(target);
        BasicDBObject targetUser = (BasicDBObject) c.next();


        BasicDBObject query = new BasicDBObject();
        query.put(Post.POST_ID, post.postID);
        query.put(Post.POSTER_ID, post.posterID);

        System.out.println("the original query is: " + query);
        //query.put("count", originalValue);

        BasicDBObject newDocument = new BasicDBObject();
        newDocument.put(Post.POST_ID, post.postID);
        query.put(Post.POSTER_ID, post.posterID);
        newDocument = toPostObj(post);


        BasicDBObject updated = new BasicDBObject();
        updated.put("$set", newDocument);

        SharedObject.mi.postTable.update(query, updated);

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

        //uti.printPostTable();

        //pti.clearPostTable();

//        Post any = new Post();
//        any.postID = 20;
//        any.posterID = 777;
//
//        pti.addPost(any);

        pti.printPostTable();

        //pti.deletePost(any);

        Post anyNew = new Post();
        anyNew.postID = 20;
        anyNew.posterID = 777;
        anyNew.title = "dasddad";

        System.out.println(anyNew.postID + "saddaddd");

        pti.updatePost(anyNew);



        pti.printPostTable();
    }

}