package com.cogent;

import com.cogent.entity.Book;
import com.cogent.entity.BorrowedBook;
import com.cogent.entity.Transaction;
import com.cogent.entity.User;
import com.cogent.service.BookDao;
import com.cogent.service.BorrowDao;
import com.cogent.service.UserDao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;


public class App {
    private static final Scanner input = new Scanner(System.in);
    private static final Connection connection = ConnectionFactory.getConnection();
    private  static  User loggedUser = null;


    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("----Library Management System----");
            System.out.println("1. User Login");
            System.out.println("2. Register New User");
            System.out.println("3. Exit");
            int choice = input.nextInt();
            switch (choice) {
                case 1:
                    loginUser();
                    break;
                case 2:
                    register();
                    break;
                case 3:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");

            }
        }
    }
    private static void register() throws SQLException {
        System.out.println("Enter username:");
        String username = input.next();
        System.out.println("Enter password:");
        String password = input.next();
        UserDao userDao = new UserDao();
        boolean success =  userDao.addUser(username, password);
        if (success) {
            System.out.println("Registration successful.");
        } else {
            System.out.println("Registration failed.");
        }
    }

    private static void loginUser() throws SQLException {

        System.out.println("Enter username:");
        String username = input.next();
        System.out.println("Enter password:");
        String password = input.next();
        UserDao userDao = new UserDao();
        loggedUser = userDao.loginUser(username, password);

        if(loggedUser != null){
            System.out.println("Login Successful.");
        }else {
            System.out.println("Can not find user.");
        }

        while (true) {
            System.out.println("----Login Successfully!----");
            System.out.println("1. Search Books");
            System.out.println("2. Borrow Books");
            System.out.println("3. Return Books");
            System.out.println("4. View the Books You Have Borrowed");
            System.out.println("5. Exit");

            int choice = input.nextInt();

            switch (choice) {
                case 1: {
                    System.out.println("Search books(title/author/genre):");
                    String search = input.next();
                    BookDao bookDao = new BookDao(connection);
                    List<Book> books = bookDao.searchBooks(search);
                    for (Book book : books) {
                        System.out.println(book);
                    }
                }

                case 2: {
                    System.out.println("Enter Book ID:");
                    int bookId = input.nextInt();
                    BorrowDao borrowDao = new BorrowDao(connection);
                    String result = borrowDao.borrowBook(loggedUser.getUserId(), bookId);
                    System.out.println(result);
                }

                case 3: {
                    System.out.print("Enter book ID to return: ");
                    int bookId = input.nextInt();
                    BookDao bookDao = new BookDao(connection);
                    boolean success = bookDao.returnBook(loggedUser.getUserId(), bookId);
                    if (success) {
                        System.out.println("Book returned successfully.");
                    } else {
                        System.out.println("Failed to return the book.");
                    }
                }

                case 4: {
                    System.out.print("Enter your User ID : ");
                    int userId = input.nextInt();
                    BorrowDao borrowDao = new BorrowDao(connection);
                    BorrowDao.borrowedBooks(userId);


                }
                case 5:{
                    System.out.println("Exiting...");
                    return;
                }

                default:
                    System.out.println("Invalid choice. Please try again.");
            }

        }
    }


}