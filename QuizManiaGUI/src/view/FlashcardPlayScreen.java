package view;

import controller.Controller;
import model.Flashcard;
import model.FlashcardSet;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.security.spec.ECField;
import java.util.ArrayList;
import java.util.List;

import static java.awt.Color.*;

public class FlashcardPlayScreen extends JFrame {
    private Controller controller;
    private List<Flashcard> flashcards;
    private int currentIndex = 0;

    private JLabel lblFlashcard;
    private JButton Btnnext;
    private JButton Btnanswer;
    private JButton BtnExit;

    private boolean showingQuestion = true;

    public FlashcardPlayScreen(Controller controller, List<Flashcard> flashcards, String username) {
        this.controller = controller;
        this.flashcards = flashcards;
        initializeUI(username);
    }

    private void initializeUI(String username) {
        setTitle("Play Mode");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 300);
        setLocationRelativeTo(null);

        JPanel pnlMain = new JPanel(new BorderLayout());
        JPanel pnLButton = new JPanel(new FlowLayout(FlowLayout.CENTER));

        Font customFont = new Font("DialogInput", Font.BOLD, 14);

        lblFlashcard = new JLabel();
        lblFlashcard.setHorizontalAlignment(SwingConstants.CENTER);
        pnlMain.add(lblFlashcard, BorderLayout.CENTER);
        displayNextFlashcard();

        Btnnext = new JButton("Next");
        Btnnext.addActionListener(e -> onNextBtnClick());
        pnLButton.add(Btnnext);

        Btnanswer = new JButton("Answer");
        Btnanswer.addActionListener(e -> onAnswerBtnClick());
        pnLButton.add(Btnanswer);

        pnlMain.add(pnLButton, BorderLayout.SOUTH);

        BtnExit = new JButton("Exit Play Mode");
        BtnExit.addActionListener(e -> onExitBtnClick(username));
        pnlMain.add(BtnExit, BorderLayout.NORTH);

        Color pnlMainColor = new Color(236, 222, 252);
        pnlMain.setBackground(pnlMainColor);

        Color pnlBtnColor = new Color(0, 7, 45);
        pnLButton.setBackground(pnlBtnColor);
        pnLButton.setFont(customFont);

        Color btnColor = new Color(236, 222, 252);
        Btnnext.setBackground(btnColor);
        Btnnext.setFont(customFont);
        Btnanswer.setBackground(btnColor);
        Btnanswer.setFont(customFont);

        BtnExit.setBackground(Color.WHITE);
        BtnExit.setFont(customFont);

        add(pnlMain);
        setResizable(false);
        setVisible(true);

    }

    public void displayNextFlashcard() {
        try {
            if (currentIndex < flashcards.size()) {
                Flashcard flashcard = flashcards.get(currentIndex);

                if (showingQuestion) {
                    lblFlashcard.setText(flashcard.getQuestion());

                } else {
                    lblFlashcard.setText(flashcard.getAnswer());
                    currentIndex++;
                }
                showingQuestion = !showingQuestion;

            } else {
                lblFlashcard.setText("All done!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void onNextBtnClick() {
        try {
            displayNextFlashcard();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void onAnswerBtnClick () {
        if (!showingQuestion) {
            displayNextFlashcard();
        }
    }

    private void onExitBtnClick(String username) {
        controller.handleExitPlayMode(username);
    }

   /* public static void main (String[]args){
        SwingUtilities.invokeLater(() -> {
            List<Flashcard> flashcards = new ArrayList<>();
            new FlashcardPlayScreen( flashcards);
        });
    }

    */

}



