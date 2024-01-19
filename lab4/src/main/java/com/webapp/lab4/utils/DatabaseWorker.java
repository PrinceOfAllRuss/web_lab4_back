package com.webapp.lab4.utils;

import com.webapp.lab4.login.User;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Value;

public class DatabaseWorker {

    String url = "jdbc:postgresql://localhost:5432/web_lab4";
    String user_name = "postgres";
    String password = "8574";
    @Value("${spring.datasource.password}")
    String p;
    @Value("${my.datasource.user}")
    String m;

    public Connection getConnectionToDataBase() {
        Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Error");
        }
        try {
            connection = DriverManager.getConnection(url, user_name, password);
        } catch (SQLException e) {
            System.out.println("Database access error");
        }
        return connection;
    }

    public boolean getUserForRegistration(User user) {
        String sql = "SELECT COUNT(*) FROM users_two WHERE name = ?";
        boolean condition = false;
        try {
            Connection connection = getConnectionToDataBase();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, user.getName());
            ResultSet result = preparedStatement.executeQuery();
            result.next();
            if (result.getInt(1) == 1) {
                condition = true;
            }
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println("Database access error");
        }
        
        return condition;
    }

    public boolean getUserForLogin(User user) {
        String sql = "SELECT COUNT(*) FROM users_two WHERE name = ? AND password = ?";
        boolean condition = false;
        try {
            Connection connection = getConnectionToDataBase();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getPassword());
            ResultSet result = preparedStatement.executeQuery();
            result.next();
            if (result.getInt(1) == 1) {
                condition = true;
            }
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println("Database access error");
        }
        return condition;
    }

    public String getUserSecret(String name) {
        String sql = "SELECT * FROM users_two WHERE name = ?";
        String secret = "";
        try {
            Connection connection = getConnectionToDataBase();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            ResultSet result = preparedStatement.executeQuery();
            result.next();
            secret = result.getString(4);
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println("Database access error");
        }
        return secret;
    }

    public void registerUser(User user) {
        String sql = "INSERT INTO users_two (name, password, secret) VALUES(?, ?, ?)";
        try {
            Connection connection = getConnectionToDataBase();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getSecret());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println("Database access error");
        }
    }
}
