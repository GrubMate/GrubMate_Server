package controller;

import dataClass.Group;

import java.util.ArrayList;

public class GroupFeed {
    public Integer id;
    public ArrayList<Group> itemList;
    public GroupFeed () {
        itemList = new ArrayList<Group>();
    }
}

