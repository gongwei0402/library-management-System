package com.cogent.service;

import com.cogent.ConnectionFactory;
import com.cogent.entity.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookDao {
    private Connection connection = ConnectionFactory.getConnection();

    public BookDao(Connection connection) {
        this.connection = connection;
    }

    //list books
    public List<Book> searchBooks(String keyword) {
        String query = "SELECT * FROM Books WHERE title LIKE ? OR author LIKE ? OR genre LIKE ?";
        List<Book> books = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            String searchKeyword = "%" + keyword + "%";
            stmt.setString(1, searchKeyword);
            stmt.setString(2, searchKeyword);
            stmt.setString(3, searchKeyword);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                books.add(new Book(rs.getInt("book_id"), rs.getString("title"),
                        rs.getString("author"), rs.getString("genre"), rs.getInt("availableCopies")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }
    //return
    public boolean returnBook(int userId, int bookId) throws SQLException {
        String query = "UPDATE BorrowedBooks SET returnDate = CURATE() WHERE userId = ? AND bookId = ? AND returnDate IS NULL";
        try(PreparedStatement stmt = connection.prepareStatement(query)){
            stmt.setInt(1, userId);
            stmt.setInt(2, bookId);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                String updateBookQuery = "UPDATE Books SET available_copies = available_copies + 1 WHERE book_id = ?";
                try (PreparedStatement updateStmt = connection.prepareStatement(updateBookQuery)) {
                    updateStmt.setInt(1, bookId);
                    updateStmt.executeUpdate();
                }
                return true;
            }
        }
        return  false;

    }



}
