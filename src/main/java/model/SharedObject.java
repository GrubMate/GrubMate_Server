package model;

public class SharedObject {
    public static MongoInitializer mi;
    public SharedObject(){}
    public static void createDBObject() {
        mi = new MongoInitializer();
    }
}
