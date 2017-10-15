package dataClass;

public class Request {
    public Integer requestID;
    public Integer requesterID;
    public Integer targetPostID;
    public String status;
    public Double[] address;

    final public static String REQUEST_ID = "requestID";
    final public static String REQUESTER_ID = "requesterID";
    final public static String TARGET_POST_ID = "targetPostID";
    final public static String STATUS = "status";
    final public static String ADDRESS = "address";
}
