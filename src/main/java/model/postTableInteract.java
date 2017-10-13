package model;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import dataClass.Post;
import dataClass.User;

import java.util.ArrayList;
import java.util.List;

public class postTableInteract {
    public BasicDBObject addUser(Post post)
    {
        System.out.println("Entering addUser");
        BasicDBObject user = new BasicDBObject();

        user.put("postID", post.postID);
        user.put("posterID", post.posterID);

        BasicDBList postPhotos = new BasicDBList();
        postPhotos.add(new BasicDBObject().append("numOfRatings", 100).append("averageRating", 6)    );
        user.put("ratings", postPhotos);

        BasicDBList allergy = new BasicDBList();
        postPhotos.add(new BasicDBObject().append("allergy1", true).append("allergy2", false)    );
        user.put("allergy", allergy);


        SharedObject.mi.postTable.insert(user);

        System.out.println("a new user with ID: " + post.postID + " is added by " + post.posterID);

        return user;
    }

    public BasicDBObject getPost(Integer postID)
    {
        BasicDBObject user = new BasicDBObject("postID", postID);

        SharedObject.mi.postCursor = SharedObject.mi.postTable.find(user);

        BasicDBObject answer = (BasicDBObject) SharedObject.mi.postCursor.next();

        return answer;
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
        SharedObject.createDBObject();
        postTableInteract pti = new postTableInteract();

        //uti.printPostTable();

        //uti.clearPostTable();

        Post any = new Post();
        any.postID = 676768;
        any.posterID = 777;

        pti.addUser(any);

        pti.printPostTable();
    }

}
