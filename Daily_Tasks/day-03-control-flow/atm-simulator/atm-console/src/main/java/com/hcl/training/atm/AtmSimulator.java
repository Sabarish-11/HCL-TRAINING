package com.hcl.training.atm;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AtmSimulator {

    private static final int CORRECT_PIN = 1234;
    private static final int MAX_PIN_ATTEMPTS = 3;
    private static final double INITIAL_BALANCE = 10000.00;

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        double balance = INITIAL_BALANCE;

        List<String> transactions = new ArrayList<>();
        transactions.add(String.format("Opening balance: INR %.2f", balance));

        System.out.println("================================");
        System.out.println("        HCL ATM SIMULATOR        ");
        System.out.println("================================");

        int attempts = 0;
        boolean authenticated = false;

        // PIN validation with maximum 3 attempts
        while (attempts < MAX_PIN_ATTEMPTS) {

            System.out.print("Enter your 4-digit PIN: ");

            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter numbers only.");
                scanner.nextLine();
                continue;
            }

            int pin = scanner.nextInt();
            scanner.nextLine();

            if (pin == CORRECT_PIN) {
                authenticated = true;
                System.out.println("PIN verified successfully.");
                break;
            } else {
                attempts++;
                System.out.println(
                        "Incorrect PIN. Attempts remaining: "
                                + (MAX_PIN_ATTEMPTS - attempts)
                );
            }
        }

        // Block access after 3 failed attempts
        if (!authenticated) {
            System.out.println("Too many incorrect attempts. Card blocked.");
            scanner.close();
            return;
        }

        atmMenu:
do {
            System.out.println();
            System.out.println("========== ATM MENU ==========");
            System.out.println("1. Check Balance");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Mini Statement");
            System.out.println("5. Exit");
            System.out.println("==============================");
            System.out.print("Choose an option: ");

            if (!scanner.hasNextInt()) {
                System.out.println(
                        "Invalid input. Please enter a number from 1 to 5."
                );
                scanner.nextLine();
                continue;
            }

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                case 1:
                    System.out.printf(
                            "Current balance: INR %.2f%n",
                            balance
                    );
                    break;

                case 2:
                    System.out.print("Enter deposit amount: ");

                    if (!scanner.hasNextDouble()) {
                        System.out.println("Invalid amount.");
                        scanner.nextLine();
                        continue;
                    }

                    double deposit = scanner.nextDouble();
                    scanner.nextLine();

                    if (deposit <= 0) {
                        System.out.println(
                                "Deposit must be greater than zero."
                        );
                        continue;
                    } else {
                        balance += deposit;

                        transactions.add(
                                String.format(
                                        "Deposit: +INR %.2f",
                                        deposit
                                )
                        );

                        System.out.printf(
                                "Deposit successful. New balance: INR %.2f%n",
                                balance
                        );
                    }
                    break;

                case 3:
                    System.out.print("Enter withdrawal amount: ");

                    if (!scanner.hasNextDouble()) {
                        System.out.println("Invalid amount.");
                        scanner.nextLine();
                        continue;
                    }

                    double withdrawal = scanner.nextDouble();
                    scanner.nextLine();

                    if (withdrawal <= 0) {
                        System.out.println(
                                "Withdrawal must be greater than zero."
                        );
                        continue;
                    } else if (withdrawal > balance) {
                        System.out.println("Insufficient balance.");
                        continue;
                    } else {
                        balance -= withdrawal;

                        transactions.add(
                                String.format(
                                        "Withdrawal: -INR %.2f",
                                        withdrawal
                                )
                        );

                        System.out.printf(
                                "Withdrawal successful. New balance: INR %.2f%n",
                                balance
                        );
                    }
                    break;

                case 4:
                    System.out.println();
                    System.out.println("======= MINI STATEMENT =======");

                    for (String transaction : transactions) {
                        System.out.println(transaction);
                    }

                    System.out.println("==============================");
                    break;

                case 5:
                    System.out.println(
                            "Thank you for using HCL ATM."
                    );
                    break;

                default:
                    System.out.println(
                            "Invalid option. Please choose 1 to 5."
                    );
                    continue;
            }

            if (choice == 5) {
    break atmMenu;
}

        } while (true);

        scanner.close();
    }
}