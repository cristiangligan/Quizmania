package view;

import controller.Controller;
import javax.swing.*;
import java.awt.*;

/**
 * SigninScreen is the login window for the application where users can sign in
 * or navigate to sign up screen
 *
 * <p>This class is a JDialog that initilizes and manages the user's interactions
 * for logging in and navigating to the registration screen.
 * </p>
 *
 * @author Savannah Norgren
 * @author Emma Handanovic
 * @author Kaye Moran
 * @author Jessica Puente
 */
public class SigninScreen extends JDialog{
    private JPanel pnlSignIn;
    private JTextField txtUsername;
    private JPasswordField fieldPassword;
    private JButton btnSignIn;
    private JButton btnSignUp;
    private Controller controller;
    /**
     *
     *Creates a new SignInScreen
     *
     * @param controller Controller object that handles logic for the
     *                   users choices on sign in screen.
     *
     */
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
    /**
     *
     *Handles the click on the SignIn button by retrieving the entered username
     * and password and furthering them to controller.
     */
    private void onSignInBtnClick() {
        //Retrieve the entered username and password
        String username = txtUsername.getText();
        String password = String.valueOf(fieldPassword.getPassword());
        controller.handleSignIn(username, password);
    }
    /**
     *
     *Handles the click on the Sign Up button by opening the sign up screen
     */
    private void openSignupScreen() {
        controller.openSignUpScreen();
    }

}
