package view;

import controller.Controller;

import javax.swing.*;
import java.awt.*;

/**
 * The MainScreen is the frame where in the aplication you can
 * choose between Quiz or Flashcard or log out.
 *
 * <p> This class is a JDialog that initiates and handles the users
 * interaction with these alternatives</p>
 *
 * @author Kaye Moran
 * @author Jessica Puente
 */
public class MainScreen extends JDialog {
    private Controller controller;
    private JPanel pnlMainScreen;
    private JButton btnFlashCard;
    private JButton btnQuiz;
    private JLabel lblChoose;
    private JButton btnLogOut;

    /**
     * Creates a new MainScreen
     * @param controller Handles the logic for the users choice.
     */

    public MainScreen(Controller controller) {
        this.controller = controller;

        setTitle("Home");
        setContentPane(pnlMainScreen);
        setResizable(false);
        setPreferredSize(new Dimension(600, 460));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
        setVisible(true);

        btnFlashCard.addActionListener(e -> onFlashCardButtonClick());

        btnQuiz.addActionListener(e -> onQuizButtonClick());

        btnLogOut.addActionListener(e -> onLogOutButtonClick());

    }

    /**
     * Handles klick button function for flashcards
     */
    private void onFlashCardButtonClick() {
        controller.handleFlashcardModeSelected();
    }
    /**
     * Handles click button function for Quiz
     */
    private void onQuizButtonClick() {
        controller.handleQuizModeSelected();
    }
    /**
     * Handles click for Log out Button
     */
    private void onLogOutButtonClick() {
        controller.handleLogOut();
    }

}
