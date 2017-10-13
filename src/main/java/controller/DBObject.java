package controller;

import model.MongoInitializer;

public class DBObject {
    public static MongoInitializer mi;

    public DBObject() {
    }

    public static void createDBObject() {
        mi = new MongoInitializer();
    }
}
