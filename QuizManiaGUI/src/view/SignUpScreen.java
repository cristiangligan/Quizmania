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

public class SignUpScreen extends JDialog {
    private JPanel pnlSignUp;
    private JTextField txtUsername;
    private JPasswordField fieldPassword;
    private JButton btnSignUp;
    private JButton btnSignIn;
    private Controller controller;
    private SignupManager signupManager;
    private UserManager userManager;


    public SignUpScreen(Controller controller, SignupManager signupManager) {
        this.controller = controller;
        this.signupManager = signupManager;
        setTitle("Sign Up");
        setContentPane(pnlSignUp);
        setResizable(false);
        setPreferredSize(new Dimension(600, 460));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();

        btnSignUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSignup();
            }
        });

        btnSignIn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openSignInScreen();
            }
        });

        setVisible(true);

    }

    private void openSignInScreen() {
        controller.openSignInScreen();
        //Create an instance of sign in screen

    }

    private void handleSignup() {
        String username = txtUsername.getText();
        String password = new String(fieldPassword.getPassword());

        //Attempt to sign up a new user
        if (signupManager.signUp(username, password)) {
            //Display a message if sign up is successful
            JOptionPane.showMessageDialog(null, "Account created successfully!");
        }

    }


}
