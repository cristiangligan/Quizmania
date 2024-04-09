package view;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;

public class SigninScreen extends JDialog{
    private JPanel pnlSignIn;
    private JTextField txtUsername;
    private JPasswordField fieldPassword;
    private JButton btnSignIn;

    public SigninScreen() {
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
                String username = txtUsername.getText();
                String password = String.valueOf(fieldPassword.getPassword());

                user = getAuthenticatedUser(username, password);

                if (user != null) {
                    dispose();
                }
                else {
                    JOptionPane.showMessageDialog(SigninScreen.this, "Username or Password Invalid", "Try again", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

    }

    public User user;
    private User getAuthenticatedUser(String username, String password) {
        User user = null;

        //add database stuff, aka usernames and passwords.

        return user;
    }

    public static void main(String[] args) {
        SigninScreen signinScreen = new SigninScreen();
        User user = signinScreen.user;
        // add invalid stuff after database is connected
    }
}
