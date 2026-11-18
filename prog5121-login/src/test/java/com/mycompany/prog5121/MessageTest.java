package com.mycompany.prog5121;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MessageTest {

    @Test
    public void testLongestMessage() {
        Message m = new Message();
        m.sentMessage("+27838884567", "Did you get the cake?");
        m.sentMessage("+27838884567", "Where are you? You are late! I have asked you to be on time.");
        String result = m.getLongestMessage();
        assertTrue(result.contains("Where are you? You are late"));
    }

    @Test
    public void testSearchByMessageId() {
        Message m = new Message();
        m.sentMessage("+27838884567", "Did you get the cake?");
        String id = m.getMessageId();
        String result = m.searchByMessageId(id);
        assertTrue(result.contains("Did you get the cake?"));
    }

    @Test
    public void testSearchByRecipient() {
        Message m = new Message();
        m.sentMessage("+27838884567", "Did you get the cake?");
        m.sentMessage("+27838884567", "Yohoooo, I am at your gate.");
        String result = m.searchByRecipient("+27838884567");
        assertTrue(result.contains("Did you get the cake?"));
        assertTrue(result.contains("Yohoooo"));
    }

    @Test
    public void testDeleteByHash() {
        Message m = new Message();
        m.sentMessage("+27838884567", "Did you get the cake?");
        String hash = m.getMessageHash();
        String result = m.deleteByHash(hash);
        assertTrue(result.contains("successfully deleted"));
        assertEquals(0, m.returnTotalMessages());
    }

    @Test
    public void testDisplayReport() {
        Message m = new Message();
        m.sentMessage("+27838884567", "Did you get the cake?");
        m.sentMessage("+27838884567", "It is dinner time !");
        String result = m.displayReport();
        assertTrue(result.contains("Did you get the cake?"));
        assertTrue(result.contains("It is dinner time"));
    }
}
