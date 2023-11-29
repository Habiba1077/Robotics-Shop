package com.roboticesproduct;

import java.io.*;
import java.util.Scanner;

public class RoboticsProductSell {
    private static final String USER_FILE = "users.txt";
    private static final String ORDER_FILE = "orders.txt";

    public static void main(String[] args) {
        System.out.println(" !----------ROBOTICS SHOP---------!");
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    registerUser();
                    break;
                case 2:
                    User user = loginUser();
                    if (user != null) {
                        processOrders(user);
                    }
                    break;
                case 3:
                    System.out.println("Exiting the program.");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }

    private static void registerUser() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your username: ");
        String username = scanner.nextLine();


        if (userExists(username)) {
            System.out.println("Username already exists. Please choose a different one.");
            return;
        }

        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        try (PrintWriter writer = new PrintWriter(new FileWriter(USER_FILE, true))) {
            writer.println(username + "," + password);
            System.out.println("Registration successful!");
        } catch (IOException e) {
            System.out.println("Registration Error.");
        }
    }

    private static boolean userExists(String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader(USER_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2 && parts[0].equals(username)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println("Error!!!.");
        }
        return false;
    }

    private static User loginUser() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your username: ");
        String username = scanner.nextLine();

        System.out.print("Enter your password: ");
        String password = scanner.nextLine();


        try (BufferedReader reader = new BufferedReader(new FileReader(USER_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2 && parts[0].equals(username) && parts[1].equals(password)) {
                    System.out.println("Login successful!");
                    return new User(username, password);
                }
            }
            System.out.println("Invalid username or password.Try Again.");
        } catch (IOException e) {
            System.out.println("Error !!!.");
        }

        return null;
    }

    private static void processOrders(User user) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. View and Order Product");
            System.out.println("2. View Ordered Product");
            System.out.println("3. Logout");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    orderProduct(user);
                    break;
                case 2:
                    viewOrders(user);
                    break;
                case 3:
                    System.out.println("Logging out.");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
                    break;
            }
        }
    }

    private static void orderProduct(User user) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Available Products:");
        System.out.println("1. Automated Watering system : 2500 tk");
        System.out.println("2. Arduino UNO R3 : 1200tk");
        System.out.println("3. Water pump : 200tk");
        System.out.print("Enter the product number to order: ");

        int productNumber = scanner.nextInt();
        String productName;

        switch (productNumber) {
            case 1:
                productName ="Automated Watering system : 2500 tk";
                break;
            case 2:
                productName = "Arduino UNO R3 : 1200tk";
                break;
            case 3:
                productName = "Water pump : 200tk";
                break;
            default:
                System.out.println("Invalid product number.");
                return;
        }

        // Save the order information to the file
        try (PrintWriter writer = new PrintWriter(new FileWriter(ORDER_FILE, true))) {
            writer.println(user.getUsername() + "," + productName);
            System.out.println("Order placed successfully!");
        } catch (IOException e) {
            System.out.println("Error.");
        }
    }

    private static void viewOrders(User user) {
        System.out.println("Your Orders:");

        try (BufferedReader reader = new BufferedReader(new FileReader(ORDER_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2 && parts[0].equals(user.getUsername())) {
                    System.out.println(parts[1]);
                }
            }
        } catch (IOException e) {
            System.out.println("Error.");
        }
    }
}
