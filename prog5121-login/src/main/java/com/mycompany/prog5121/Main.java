package com.mycompany.prog5121;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Login login = new Login();

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter your first name: ");
            String firstName = scanner.nextLine();

            System.out.print("Enter your last name: ");
            String lastName = scanner.nextLine();

            System.out.print("Enter a username: ");
            String username = scanner.nextLine();

            System.out.print("Enter a password: ");
            String password = scanner.nextLine();

            System.out.print("Enter your South African cell phone number: ");
            String cell = scanner.nextLine();

            String registrationStatus = login.registerUser(
                    firstName, lastName, username, password, cell);
            System.out.println(registrationStatus);

            if (registrationStatus.startsWith("The two above conditions")) {
                System.out.print("Enter your username to log in: ");
                String loginUsername = scanner.nextLine();

                System.out.print("Enter your password to log in: ");
                String loginPassword = scanner.nextLine();

                System.out.println(login.returnLoginStatus(loginUsername, loginPassword));
            }

            System.out.println();
            System.out.println("POE test data:");
            System.out.println("Username valid: "
                    + login.checkUserName("kyl_1"));
            System.out.println("Password valid: "
                    + login.checkPasswordComplexity("Ch&&sec@ke99!"));
            System.out.println("Cell phone valid: "
                    + login.checkCellPhoneNumber("+27838968976"));
        }
    }
}