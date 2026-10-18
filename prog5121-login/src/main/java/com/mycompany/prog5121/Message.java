package com.mycompany.prog5121;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Message {
    private static final int MAX_MESSAGE_LENGTH = 250;
    private static final Pattern WORD_PATTERN = Pattern.compile("[\\p{L}\\p{N}]+");

    private final List<SentMessage> sentMessages = new ArrayList<>();
    private final Set<String> usedMessageIds = new HashSet<>();

    private String recipientCell;
    private String messageText;
    private String messageId;
    private String messageHash;

    public Message() {
    }

    public Message(String recipientCell, String messageText) {
        this.recipientCell = recipientCell;
        this.messageText = messageText;
    }

    public String checkRecipientCell(String recipientCell) {
        if (recipientCell != null && recipientCell.matches("^\\+27[0-9]{9}$")) {
            return "Cell phone number successfully captured.";
        }
        return "Cell phone number is incorrectly formatted or does not contain an international code, please correct the number and try again.";
    }

    public String checkRecipientCell() {
        return checkRecipientCell(recipientCell);
    }

    public String checkMessageLength(String messageText) {
        if (messageText == null) {
            return "Message cannot be null.";
        }
        if (messageText.length() <= MAX_MESSAGE_LENGTH) {
            return "Message ready to send.";
        }
        int excessCharacters = messageText.length() - MAX_MESSAGE_LENGTH;
        return "Message exceeds 250 characters by " + excessCharacters + ", please reduce size.";
    }

    public String checkMessageLength() {
        return checkMessageLength(messageText);
    }

    public String createMessageHash(String messageId, int messageNumber, String messageText) {
        if (messageId == null || messageId.length() < 2) {
            throw new IllegalArgumentException("Message ID must contain at least two characters.");
        }
        if (messageNumber < 0) {
            throw new IllegalArgumentException("Message number cannot be negative.");
        }
        if (messageText == null) {
            throw new IllegalArgumentException("Message text cannot be null.");
        }

        Matcher matcher = WORD_PATTERN.matcher(messageText);
        String firstWord = null;
        String lastWord = null;
        while (matcher.find()) {
            if (firstWord == null) {
                firstWord = matcher.group();
            }
            lastWord = matcher.group();
        }

        String firstAndLastWords = firstWord == null
                ? ""
                : firstWord + lastWord;
        return messageId.substring(0, 2).toUpperCase()
                + ":" + messageNumber
                + ":" + firstAndLastWords.toUpperCase();
    }

    public String createMessageHash() {
        if (recipientCell == null || messageText == null) {
            throw new IllegalStateException("Set a recipient and message before creating its hash.");
        }
        if (messageId == null) {
            messageId = generateMessageId();
        }
        messageHash = createMessageHash(messageId, sentMessages.size(), messageText);
        return messageHash;
    }

    public String sentMessage(String recipientCell, String messageText) {
        String recipientStatus = checkRecipientCell(recipientCell);
        if (!recipientStatus.equals("Cell phone number successfully captured.")) {
            return recipientStatus;
        }

        String lengthStatus = checkMessageLength(messageText);
        if (!lengthStatus.equals("Message ready to send.")) {
            return lengthStatus;
        }

        this.recipientCell = recipientCell;
        this.messageText = messageText;
        this.messageId = generateMessageId();
        this.messageHash = createMessageHash(
                messageId, sentMessages.size(), messageText);
        sentMessages.add(new SentMessage(
                messageId, messageHash, recipientCell, messageText));
        return "Message sent successfully.";
    }

    public String sentMessage() {
        return sentMessage(recipientCell, messageText);
    }

    public String printMessages() {
        if (sentMessages.isEmpty()) {
            String emptyReport = "No messages have been sent.";
            System.out.println(emptyReport);
            return emptyReport;
        }

        StringBuilder report = new StringBuilder();
        for (int i = 0; i < sentMessages.size(); i++) {
            SentMessage sent = sentMessages.get(i);
            if (i > 0) {
                report.append(System.lineSeparator());
            }
            report.append("Message ID: ").append(sent.messageId())
                    .append(System.lineSeparator())
                    .append("Message Hash: ").append(sent.messageHash())
                    .append(System.lineSeparator())
                    .append("Recipient: ").append(sent.recipientCell())
                    .append(System.lineSeparator())
                    .append("Message: ").append(sent.messageText());
        }
        String messageReport = report.toString();
        System.out.println(messageReport);
        return messageReport;
    }

    public int returnTotalMessages() {
        return sentMessages.size();
    }

    public String getMessageId() {
        return messageId;
    }

    public String getMessageHash() {
        return messageHash;
    }

    private String generateMessageId() {
        String generated;
        do {
            generated = Long.toString(ThreadLocalRandom.current()
                    .nextLong(1_000_000_000L, 10_000_000_000L));
        } while (!usedMessageIds.add(generated));
        return generated;
    }

    private record SentMessage(
            String messageId,
            String messageHash,
            String recipientCell,
            String messageText) {
    }
}
