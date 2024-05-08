package model;

import view.SigninScreen;

import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;

public class SignupManager {
    private Connection connection;
    private UserManager userManager;

    //constructor, initialize SignUpManager with connection and userManager
    public SignupManager(Connection connection, UserManager userManager) {
        this.connection = connection;
        this.userManager = userManager;
    }

    //Method to sign up a new user
    public boolean signUp(String username, String password) {
        try {
            //Check if the username already exists
            if (userManager.userExists(username)) {
                JOptionPane.showMessageDialog(null,"This user is already taken.");
                return false; //Return false to indicate that signup failed
            }
            boolean success = userManager.createUser(username, password); //To create a new user
            return success; //Return the result

        } catch (SQLException e) {
            System.out.println("Registration failed. Please try again.");
            e.printStackTrace();
            return false;
        }
    }
}
