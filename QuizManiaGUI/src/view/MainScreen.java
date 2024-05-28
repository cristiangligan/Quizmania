package view;

import controller.Controller;

import javax.swing.*;
import java.awt.*;

public class MainScreen extends JDialog {
    private Controller controller;
    private JPanel pnlMainScreen;
    private JButton btnFlashCard;
    private JButton btnQuiz;
    private JLabel lblChoose;
    private JButton btnLogOut;


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

    private void onFlashCardButtonClick() {
        controller.handleFlashcardModeSelected();
    }

    private void onQuizButtonClick() {
        controller.handleQuizModeSelected();
    }

    private void onLogOutButtonClick() {
        controller.handleLogOut();
    }

}
