package model;

import org.junit.Test;

import static org.junit.Assert.*;

public class SubscriptionTableInteractTest {
    @Test
    public void getNonExistingSubscription() throws Exception {
        assertEquals(SubscriptionTableInteract.getSubscription(-1),null);
    }

}