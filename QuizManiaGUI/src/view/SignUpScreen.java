package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignUpScreen extends JDialog {
    private JPanel pnlSignUp;
    private JTextField txtUsername;
    private JPasswordField fieldPassword;
    private JButton btnSignUp;

    public SignUpScreen() {
        setTitle("Sign Up");
        setContentPane(pnlSignUp);
        setResizable(false);
        setPreferredSize(new Dimension(600, 460));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();

        setVisible(true);
    }

    public static void main(String[] args) {
        SignUpScreen signUpScreen = new SignUpScreen();
    }
}
