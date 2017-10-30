package model;

import dataClass.User;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserTableInteractTest {
    @Test
    public void getNonExistingUser() throws Exception {
        assertEquals(UserTableInteract.getUser(-1),null);
    }

    @Test
    public void getUserIDFromFBID() throws Exception {
        User user = new User();
        user.facebookID = "nihaonihao";
        Integer userID = UserTableInteract.addUser(user);
        assertEquals(UserTableInteract.getUserIDFromFBID(user.facebookID),userID);
    }

}