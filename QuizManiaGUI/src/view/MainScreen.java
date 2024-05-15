package view;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainScreen extends JDialog {
    private Controller controller;
    private JPanel pnlMainScreen;
    private JButton btnFlashCard;
    private JButton btnQuiz;
    private JLabel lblChoose;
    private JButton btnLogOut;
    private String username;


    public MainScreen(Controller controller, String username) {
        this.controller = controller;
        this.username = username;

        setTitle("Home");
        setContentPane(pnlMainScreen);
        setResizable(false);
        setPreferredSize(new Dimension(600, 460));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
        setVisible(true);

        btnFlashCard.addActionListener(e -> onFlashCardButtonClick(username));

        btnQuiz.addActionListener(e -> onQuizButtonClick(username));

        btnLogOut.addActionListener(e -> onLogoutButtonClick());

    }

    private void onFlashCardButtonClick(String username) {
        controller.handleFlashcardModeSelected(username);
    }

    private void onQuizButtonClick(String username) {
        controller.handleQuizModeSelected(username);
    }

    private void onLogoutButtonClick() {
        controller.handleLogout();
    }

}
