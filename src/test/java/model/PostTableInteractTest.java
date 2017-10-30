package model;

import org.junit.Test;

import static org.junit.Assert.*;

public class PostTableInteractTest {
    @Test
    public void getNonExistingPost() throws Exception {
        assertEquals(PostTableInteract.getPost(-1),null);
    }

}