package view;

import controller.Controller;
import javax.swing.*;
import java.awt.*;

public class SigninScreen extends JDialog{
    private JPanel pnlSignIn;
    private JTextField txtUsername;
    private JPasswordField fieldPassword;
    private JButton btnSignIn;
    private JButton btnSignUp;
    private Controller controller;

    public SigninScreen(Controller controller) {
        this.controller = controller;

        setTitle("Sign in");
        setContentPane(pnlSignIn);
        setResizable(false);
        setPreferredSize(new Dimension(900, 474));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
        setVisible(true);

        btnSignIn.addActionListener(e -> onSignInBtnClick());
        btnSignUp.addActionListener(e -> openSignupScreen());
    }

    private void onSignInBtnClick() {
        //Retrieve the entered username and password
        String username = txtUsername.getText();
        String password = String.valueOf(fieldPassword.getPassword());
        controller.handleSignIn(username, password);
    }

    private void openSignupScreen() {
        controller.openSignUpScreen();
    }

}
