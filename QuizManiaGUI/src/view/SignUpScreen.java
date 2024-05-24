package view;

import controller.Controller;

import javax.swing.*;
import java.awt.*;

public class SignUpScreen extends JDialog {
    private JPanel pnlSignUp;
    private JTextField txtUsername;
    private JPasswordField fieldPassword;
    private JButton btnSignUp;
    private JButton btnSignIn;
    private Controller controller;



    public SignUpScreen(Controller controller) {
        this.controller = controller;
        setTitle("Sign Up");
        setContentPane(pnlSignUp);
        setResizable(false);
        setPreferredSize(new Dimension(600, 460));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
        setVisible(true);
        btnSignUp.addActionListener(e -> onSignUpBtnClick());
        btnSignIn.addActionListener(e -> onSignInBtnClick());
    }

    private void onSignInBtnClick() {
        controller.openSignInScreen();
        //Create an instance of sign in screen

    }

    private void onSignUpBtnClick() {
        String username = txtUsername.getText();
        String password = new String(fieldPassword.getPassword());
        controller.handleSignUp(username, password);
    }
}
