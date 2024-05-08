package view;

import controller.Controller;
import model.SignupManager;
import model.User;
import model.UserManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class SigninScreen extends JDialog{
    private JPanel pnlSignIn;
    private JTextField txtUsername;
    private JPasswordField fieldPassword;
    private JButton btnSignIn;
    private JButton btnSignUp;
    private Controller controller;
    private SignupManager signupManager;
    private SignUpScreen signUpScreen;

    public SigninScreen(Controller controller) {
        this.controller = controller;

        setTitle("Sign in");
        setContentPane(pnlSignIn);
        setResizable(false);
        setPreferredSize(new Dimension(900, 474));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();

        setVisible(true);
        btnSignIn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSignIn();
            }
        });

        btnSignUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openSignupScreen();
            }
        });
    }

    private void handleSignIn() {
        //Retrieve the entered username and password
        String username = txtUsername.getText();
        String password = String.valueOf(fieldPassword.getPassword());

        try {
            //Verify the username and password with database
            if (controller.getUserManager().verifyPassword(username, password)) {
                JOptionPane.showMessageDialog(null, "You are now loggen in!");
                controller.openMainScreen(username);
                //Open main screen and dispose sign in screen
                dispose();

            } else {
                //If username or password is invalid
                JOptionPane.showMessageDialog(null, "Incorrect username or password.");

            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Log in failed. Please try again.");
            e.printStackTrace();
        }
    }

    private void openSignupScreen() {
        controller.openSignUpScreen();

    }

}
