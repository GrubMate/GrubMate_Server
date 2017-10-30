package model;

import org.junit.Test;

import static org.junit.Assert.*;

public class GroupInfoTableInteractTest {
    @Test
    public void getNonExistingGroupInfo() throws Exception {
        assertEquals(GroupInfoTableInteract.getGroupInfo(-1),null);
    }

}