package com.cogent.service;

import com.cogent.ConnectionFactory;
import com.cogent.entity.Book;
import com.cogent.entity.BorrowedBook;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BorrowDao {
    private static Connection connection = ConnectionFactory.getConnection();

    public BorrowDao(Connection connection) {
        this.connection = connection;
    }

    public String borrowBook(int userId, int bookId) throws SQLException {
        String result = "";
        String query = "SELECT COUNT(*) FROM BorrowedBooks WHERE userId = ? AND returnDate IS NULL";
        try (PreparedStatement stmtCheck = connection.prepareStatement(query)) {
            stmtCheck.setInt(1, userId);
            ResultSet rsCheck = stmtCheck.executeQuery();
            if (rsCheck.next() && rsCheck.getInt(1) >= 5) {
                return "User has already borrowed 5 books";
            }
        }
        query = "SELECT COUNT(*) FROM BorrowedBooks WHERE userId = ? AND bookId = ? AND returnDate IS NULL";
        try (PreparedStatement stmtCheck = connection.prepareStatement(query)) {
            stmtCheck.setInt(1, userId);
            stmtCheck.setInt(2, bookId);
            ResultSet rsCheck = stmtCheck.executeQuery();
            if (rsCheck.next() && rsCheck.getInt(1) > 0) {
                return "User has already borrowed this book";
            }
        }
        String queryInsert = "INSERT INTO BorrowedBooks (userId, bookId, borrowDate) VALUES (?, ?, CURDATE())";
        try (PreparedStatement stmtInsert = connection.prepareStatement(queryInsert)) {
            stmtInsert.setInt(1, userId);
            stmtInsert.setInt(2, bookId);
            stmtInsert.executeUpdate();
        }
        String queryUpdate = "UPDATE Books SET available_copies = available_copies - 1 WHERE bookId = ?";
        try (PreparedStatement stmtUpdate = connection.prepareStatement(queryUpdate)) {
            stmtUpdate.setInt(1, bookId);
            stmtUpdate.executeUpdate();
        }
        result = "Book borrowed successfully";
        return result;
    }

    public static void borrowedBooks(int userId) throws SQLException {
        String query = "SELECT B.bookId, B.title FROM borrowedbooks BB JOIN books B ON BB.bookId = B.bookId WHERE BB.userId = ? AND BB.return_date IS NULL ";
        List<BorrowedBook> borrowedBooks = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("book_id") + ", Title: " + rs.getString("title"));
            }
        }
    }

}


