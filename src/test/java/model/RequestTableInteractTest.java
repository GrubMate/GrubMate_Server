package model;

import org.junit.Test;

import static org.junit.Assert.*;

public class RequestTableInteractTest {
    @Test
    public void getNonExistingRequest() throws Exception {
        assertEquals(RequestTableInteract.getRequest(-1),null);
    }

}