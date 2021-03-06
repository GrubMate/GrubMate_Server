package dataClass;

import java.util.ArrayList;

public class Group {
    public Integer groupID;
    public Integer groupOwnerID;
    public String groupName;
    public ArrayList<Integer> memberIDs;
    //public boolean allFriendFlag;

    public final static String GROUPID = "groupID";
    public final static String GROUP_OWNER_ID = "groupOwnerID";
    public final static String GROUP_NAME = "groupName";
    public final static String MEMBER_IDS = "memberIDs";
    public final static String ALL_FRIEND_FLAG = "allFriendFlag";

    public boolean hasUser(int userID)
    {
        for(int id : memberIDs)
        {
            if(id==userID)
            {
                return true;
            }
        }
        return false;
    }



}
