package model;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.ResultSet;

public class UserManager {
    private Connection connection;
    private User currentUser;

    //Initializes the manager with a database connection
    public UserManager(Connection connection) {
        this.connection = connection;
    }

    public boolean createUser(String username, String password) {
        //Validate username and password
        if (!isValidUsername(username) || !isValidPassword(password)) {
            JOptionPane.showMessageDialog(null, "Username and Password must be between 8-16 characters long.");
            return false;
        }

        String inserQuery = "INSERT INTO public.users (username, password, created_at) VALUES (?, ?, ?)";

        try(PreparedStatement preparedStatement = connection.prepareStatement(inserQuery)) {
            //Set parameters for the prepared statement
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));

            //Execute the update query
            int rowsInserted = preparedStatement.executeUpdate();

            //Check if user was successfully inserted
            if (rowsInserted > 0) {
                return true;
                // JOptionPane.showMessageDialog(null, "Account created successfully!");
            } else {
                JOptionPane.showMessageDialog(null, "Oops! Registration failed. Please try again.");
            }

            return rowsInserted > 0;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Unable to create user. Please try again.");
            e.printStackTrace();
            return false;
        }
    }

    private boolean isValidUsername(String username) {
        //Check if username is between 8-16 characters
        return username.length() >= 8 && username.length() <= 16;
    }

    private boolean isValidPassword(String password) {
        return password.length() >= 8 && password.length() <= 16;
    }

    public boolean userExists(String username) throws SQLException {
        //query to count the number of users with the given username
        String query = "SELECT COUNT(*) FROM public.users WHERE username = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            //Set the username parameter for the prepared statement
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery(); //Execute the query and get the result set
            resultSet.next(); //Move the cursor to the first row of result set
            int count = resultSet.getInt(1); //Retrieve value of the first column
            return count > 0; //Return true if count is greater than 0 (aka user exists)
        }
    }

    public boolean verifyPassword(String username, String password) throws SQLException {
        //Query to retrive the password for the given username
        String verifyQuery = "SELECT password FROM public.users WHERE username = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(verifyQuery)) {
            preparedStatement.setString(1, username); //Set the username parameter for the prepared statement

           try (ResultSet resultSet = preparedStatement.executeQuery()) {
               //Check if the stored password matches the given password
               if (resultSet.next()) {
                   String storedPassword = resultSet.getString("password");
                   return storedPassword.equals(password); //Check if the stored password matched the given password
               } else {
                   return false;
               }
           }

        }
    }

    public int getCurrentUserId(String username) throws SQLException {
        String query = "SELECT user_id FROM public.users WHERE username = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            ResultSet resultset = preparedStatement.executeQuery();

            if (resultset.next()) {
                return resultset.getInt("user_id");

            } else {
                throw new RuntimeException("User could not be found.");
            }
        }
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public User getMatchingUserFromDatabase(String username, String password) {
        User user;
        int id = 0;
        String query = "SELECT user_id FROM public.users WHERE username = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            ResultSet resultset = preparedStatement.executeQuery();
            if (resultset.next()) {
                id = resultset.getInt("user_id");
            }
            user = new User(id, username, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }
}
