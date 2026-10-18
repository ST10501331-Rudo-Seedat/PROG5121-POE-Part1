package com.mycompany.prog5121;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MessageTest {
    private Message message;

    @BeforeEach
    void setUp() {
        message = new Message();
    }

    @Test
    void acceptsValidSouthAfricanRecipientCell() {
        assertEquals(
                "Cell phone number successfully captured.",
                message.checkRecipientCell("+27838968976"));
    }

    @Test
    void rejectsRecipientCellWithoutInternationalCode() {
        assertEquals(
                "Cell phone number is incorrectly formatted or does not contain an international code, please correct the number and try again.",
                message.checkRecipientCell("0838968976"));
    }

    @Test
    void acceptsMessageAtThe250CharacterLimit() {
        assertEquals(
                "Message ready to send.",
                message.checkMessageLength("a".repeat(250)));
    }

    @Test
    void reportsCharactersOverTheMessageLimit() {
        assertEquals(
                "Message exceeds 250 characters by 1, please reduce size.",
                message.checkMessageLength("a".repeat(251)));
    }

    @Test
    void createsHashInRequiredFormat() {
        assertEquals(
                "00:0:HITONIGHT",
                message.createMessageHash("0012345678", 0, "Hi, tonight!"));
    }

    @Test
    void sendsValidMessageAndTracksItsDetails() {
        assertEquals(
                "Message sent successfully.",
                message.sentMessage("+27838968976", "Hi, tonight!"));

        assertEquals(1, message.returnTotalMessages());
        assertEquals(10, message.getMessageId().length());
        assertTrue(message.printMessages().contains("Message Hash: "
                + message.getMessageHash()));
        assertTrue(message.printMessages().contains("Message: Hi, tonight!"));
    }

    @Test
    void doesNotCountInvalidRecipientAsSentMessage() {
        assertEquals(
                "Cell phone number is incorrectly formatted or does not contain an international code, please correct the number and try again.",
                message.sentMessage("0838968976", "Hi, tonight!"));

        assertEquals(0, message.returnTotalMessages());
    }

    @Test
    void returnsEmptyReportWhenNoMessagesWereSent() {
        assertEquals("No messages have been sent.", message.printMessages());
        assertEquals(0, message.returnTotalMessages());
    }
}
