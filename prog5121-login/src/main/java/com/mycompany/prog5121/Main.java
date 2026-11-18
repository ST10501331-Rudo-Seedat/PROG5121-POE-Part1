package com.mycompany.prog5121;

import javax.swing.JOptionPane;

public class Main {

    public static void main(String[] args) {

        Login login = new Login();
        Message msg = new Message();

        // --- LOGIN ---
        String firstName = JOptionPane.showInputDialog("Enter First Name:");
        String lastName = JOptionPane.showInputDialog("Enter Last Name:");
        String username = JOptionPane.showInputDialog("Enter Username (must contain _ and max 5 chars):");
        String password = JOptionPane.showInputDialog("Enter Password:");
        String cell = JOptionPane.showInputDialog("Enter Cell (+27...):");

        JOptionPane.showMessageDialog(null, login.registerUser(username, password, cell, firstName, lastName));

        String loginUser = JOptionPane.showInputDialog("Login - Enter Username:");
        String loginPass = JOptionPane.showInputDialog("Login - Enter Password:");
        boolean loggedIn = login.loginUser(loginUser, loginPass);
        JOptionPane.showMessageDialog(null, (null, login.returnLoginStatus(loggedIn));
);

        if (!loggedIn) {
            return;
        }

        JOptionPane.showMessageDialog(null, "Welcome to QuickChat.");

        // --- MAIN MENU ---
        while (true) {
            String choiceStr = JOptionPane.showInputDialog(
                    "Choose:\n1. Send Messages\n2. Show recently sent messages\n3. Stored Messages\n4. Quit");
            if (choiceStr == null) break;
            int choice = Integer.parseInt(choiceStr);

            if (choice == 1) {
                String numStr = JOptionPane.showInputDialog("How many messages do you want to send?");
                int num = Integer.parseInt(numStr);
                for (int i = 0; i < num; i++) {
                    String recipient = JOptionPane.showInputDialog("Enter recipient (+27...):");
                    String text = JOptionPane.showInputDialog("Enter message (max 250 chars):");
                    String result = msg.sentMessage(recipient, text);
                    JOptionPane.showMessageDialog(null, result);
                    if (result.equals("Message sent successfully.")) {
                        JOptionPane.showMessageDialog(null,
                                "Message ID: " + msg.getMessageId()
                                + "\nMessage Hash: " + msg.getMessageHash());
                    }
                }
            } else if (choice == 2) {
                JOptionPane.showMessageDialog(null, msg.printMessages());
            } else if (choice == 3) {
                // PART 3 MENU
                while (true) {
                    String subStr = JOptionPane.showInputDialog(
                            "Stored Messages:\n1. Display Longest Message\n2. Search by Message ID\n3. Search by Recipient\n4. Delete by Hash\n5. Display Report\n6. Back");
                    if (subStr == null) break;
                    int sub = Integer.parseInt(subStr);
                    if (sub == 1) {
                        JOptionPane.showMessageDialog(null, msg.getLongestMessage());
                    } else if (sub == 2) {
                        String id = JOptionPane.showInputDialog("Enter Message ID:");
                        JOptionPane.showMessageDialog(null, msg.searchByMessageId(id));
                    } else if (sub == 3) {
                        String rec = JOptionPane.showInputDialog("Enter Recipient (+27...):");
                        JOptionPane.showMessageDialog(null, msg.searchByRecipient(rec));
                    } else if (sub == 4) {
                        String hash = JOptionPane.showInputDialog("Enter Message Hash to delete:");
                        JOptionPane.showMessageDialog(null, msg.deleteByHash(hash));
                    } else if (sub == 5) {
                        JOptionPane.showMessageDialog(null, msg.displayReport());
                    } else if (sub == 6) {
                        break;
                    }
                }
            } else if (choice == 4) {
                JOptionPane.showMessageDialog(null, "Total messages sent: " + msg.returnTotalMessages());
                break;
            }
        }
    }
}
