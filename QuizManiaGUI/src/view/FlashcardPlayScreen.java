package view;

import controller.Controller;
import model.Flashcard;
import model.FlashcardSet;
import model.User;

import javax.swing.*;
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
    private JScrollPane scrollPane;

    private boolean showingQuestion = true;

    public FlashcardPlayScreen(Controller controller, List<Flashcard> flashcards) {
        this.controller = controller;
        this.flashcards = flashcards;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Play Mode");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 300);
        setLocationRelativeTo(null);

        JPanel pnlMain = new JPanel(new BorderLayout());
        JPanel pnLButton = new JPanel(new FlowLayout(FlowLayout.CENTER));

        Font customFont = new Font("DialogInput", Font.BOLD, 14);

        lblFlashcard = new JLabel();
        lblFlashcard.setVerticalAlignment(SwingConstants.TOP);
        lblFlashcard.setHorizontalAlignment(SwingConstants.CENTER);
        lblFlashcard.setPreferredSize(new Dimension(580, 200));

        pnlMain.add(lblFlashcard, BorderLayout.CENTER);

        scrollPane = new JScrollPane(lblFlashcard);
        pnlMain.add(scrollPane, BorderLayout.CENTER);

        displayNextFlashcard();

        Btnnext = new JButton("Next");
        Btnnext.addActionListener(e -> onNextBtnClick());
        pnLButton.add(Btnnext);

        Btnanswer = new JButton("Answer");
        Btnanswer.addActionListener(e -> onAnswerBtnClick());
        pnLButton.add(Btnanswer);

        pnlMain.add(pnLButton, BorderLayout.SOUTH);

        BtnExit = new JButton("Exit Play Mode");
        BtnExit.addActionListener(e -> onExitBtnClick());
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
                    lblFlashcard.setText("<html>" + flashcard.getQuestion() + "</html>");

                } else {
                    lblFlashcard.setText("<html>" + flashcard.getAnswer() + "</html>");
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
            currentIndex++;
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

    private void onExitBtnClick() {
        controller.handleExitPlayMode();
    }


}



