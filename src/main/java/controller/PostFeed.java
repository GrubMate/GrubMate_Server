package controller;

import dataClass.Post;

import java.util.ArrayList;

public class PostFeed {
    public Integer id;
    public ArrayList<Post> itemList;
    public PostFeed() {
        itemList = new ArrayList<Post>();
    }
}

