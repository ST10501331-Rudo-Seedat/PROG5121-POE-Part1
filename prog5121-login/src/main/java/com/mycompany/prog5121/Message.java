package com.mycompany.prog5121;

import java.util.ArrayList;
import java.util.Random;

public class Message {

    private ArrayList<String> messageIds = new ArrayList<>();
    private ArrayList<String> messageHashes = new ArrayList<>();
    private ArrayList<String> recipients = new ArrayList<>();
    private ArrayList<String> messages = new ArrayList<>();
    private ArrayList<String> sentStatus = new ArrayList<>();

    private String lastMessageId;
    private String lastMessageHash;
    private int totalMessages = 0;

    public boolean checkMessageID(String id) {
        return id != null && id.length() == 10;
    }

    public int checkRecipientCell(String cell) {
        if (cell == null) return 0;
        if (cell.startsWith("+27") && cell.length() == 12) return 1;
        return 0;
    }

    public String createMessageHash(String id, int num, String text) {
        String[] words = text.trim().split("\\s+");
        String first = words[0];
        String last = words[words.length - 1];
        first = first.replaceAll("[^A-Za-z]", "").toUpperCase();
        last = last.replaceAll("[^A-Za-z]", "").toUpperCase();
        String hash = id.substring(0, 2) + ":" + num + ":" + first + ":" + last;
        return hash.toUpperCase();
    }

    public String sentMessage(String recipient, String text) {
        if (checkRecipientCell(recipient) == 0) {
            return "Cell phone number is incorrectly formatted or does not contain international code.";
        }
        if (text.length() > 250) {
            return "Please enter a message of no more than 250 characters.";
        }

        Random rand = new Random();
        long num = 1000000000L + (long) (rand.nextDouble() * 8999999999L);
        String id = String.valueOf(num);

        totalMessages++;
        String hash = createMessageHash(id, totalMessages, text);

        messageIds.add(id);
        messageHashes.add(hash);
        recipients.add(recipient);
        messages.add(text);
        sentStatus.add("Sent");

        lastMessageId = id;
        lastMessageHash = hash;

        return "Message sent successfully.";
    }

    public String getMessageId() {
        return lastMessageId;
    }

    public String getMessageHash() {
        return lastMessageHash;
    }

    public int returnTotalMessages() {
        return totalMessages;
    }

    public String printMessages() {
        if (messages.isEmpty()) {
            return "No messages sent yet.";
        }
        String out = "";
        for (int i = 0; i < messages.size(); i++) {
            out += "Message ID: " + messageIds.get(i) + "\n";
            out += "Message Hash: " + messageHashes.get(i) + "\n";
            out += "Recipient: " + recipients.get(i) + "\n";
            out += "Message: " + messages.get(i) + "\n\n";
        }
        return out;
    }

    // ===== PART 3 =====

    public String getLongestMessage() {
        if (messages.isEmpty()) return "No messages sent yet.";
        int longest = 0;
        for (int i = 1; i < messages.size(); i++) {
            if (messages.get(i).length() > messages.get(longest).length()) {
                longest = i;
            }
        }
        return "Longest Message:\nMessage: " + messages.get(longest)
                + "\nRecipient: " + recipients.get(longest)
                + "\nMessage Hash: " + messageHashes.get(longest);
    }

    public String searchByMessageId(String id) {
        for (int i = 0; i < messageIds.size(); i++) {
            if (messageIds.get(i).equals(id)) {
                return "Recipient: " + recipients.get(i)
                        + "\nMessage: " + messages.get(i);
            }
        }
        return "Message ID not found.";
    }

    public String searchByRecipient(String recipient) {
        String out = "";
        for (int i = 0; i < recipients.size(); i++) {
            if (recipients.get(i).equals(recipient)) {
                out += "Message: " + messages.get(i)
                        + "\nMessage Hash: " + messageHashes.get(i) + "\n\n";
            }
        }
        if (out.equals("")) return "No messages found for this recipient.";
        return out;
    }

    public String deleteByHash(String hash) {
        for (int i = 0; i < messageHashes.size(); i++) {
            if (messageHashes.get(i).equalsIgnoreCase(hash)) {
                String deleted = messages.get(i);
                messageIds.remove(i);
                messageHashes.remove(i);
                recipients.remove(i);
                messages.remove(i);
                sentStatus.remove(i);
                totalMessages--;
                return "Message \"" + deleted + "\" successfully deleted.";
            }
        }
        return "Message Hash not found.";
    }

    public String displayReport() {
        return printMessages();
    }
}
