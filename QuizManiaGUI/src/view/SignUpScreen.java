package view;

import controller.Controller;

import javax.swing.*;
import java.awt.*;

/**
 * SignUpScreen is the registration window for the appliaction where
 * users can sign up or sign in
 *
 * <p> This class is JDialog that initializes and manages the user interaction
 * fort registration and login
 * </p>
 * @author Emma Handanovic
 * @author Savannah Norgren
 * @author Jessica Puente
 * @author Kaye Moran
 */
public class SignUpScreen extends JDialog {
    private JPanel pnlSignUp;
    private JTextField txtUsername;
    private JPasswordField fieldPassword;
    private JButton btnSignUp;
    private JButton btnSignIn;
    private Controller controller;


    /**
     *Creates a new SignUpScreen
     * @param controller Controller object that handles the logic for
     *                   the users choices on the sign up screen.
     */
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
    /**
     *Handles click on the Sign In Button
     */
    private void onSignInBtnClick() {
        controller.openSignInScreen();
        //Create an instance of sign in screen

    }
    /**
     *Handles click on the Sign Up button by sending username and password
     * to controller for more processing.
     */
    private void onSignUpBtnClick() {
        String username = txtUsername.getText();
        String password = new String(fieldPassword.getPassword());
        controller.handleSignUp(username, password);
    }
}
