package com.cogent.service;

import com.cogent.ConnectionFactory;
import com.cogent.entity.User;

import java.sql.*;

public class UserDao {
    private final Connection connection;

    public UserDao() {
        this.connection = ConnectionFactory.getConnection();
    }


    public boolean addUser(String username, String password) throws SQLException {
        String query = "insert into users(username, password) values(?, ?)";
        try(PreparedStatement stmt = connection.prepareStatement(query)){
            stmt.setString(1, username);
            stmt.setString(2, password);
            int updated = stmt.executeUpdate();
            return true;
        }
    }
    public User loginUser(String username, String password) throws SQLException {
        String query = "SELECT * FROM Users WHERE username = ? AND password = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(rs.getInt("userId"), username);
            }
        }
        return null;
    }

}





