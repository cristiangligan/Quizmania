package view;

import model.Flashcard;
import model.FlashcardSet;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.security.spec.ECField;
import java.util.ArrayList;
import java.util.List;

public class FlashcardPlayScreen extends JFrame {
    private List<Flashcard> flashcards;
    private int currentIndex = 0;

    private JLabel lblFlashcard;
    private JButton Btnnext;
    private JButton Btnanswer;

    private boolean showingQuestion = true;

    public FlashcardPlayScreen(List<Flashcard> flashcards) {
        this.flashcards = flashcards;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Play Mode");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());

        lblFlashcard = new JLabel();
        lblFlashcard.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(lblFlashcard, BorderLayout.CENTER);

        Btnnext = new JButton("Next");
        Btnnext.addActionListener(e -> onNextBtnClick());
        mainPanel.add(Btnnext, BorderLayout.SOUTH);

        Btnanswer = new JButton("Answer");
        Btnanswer.addActionListener(e -> onAnswerBtnClick());
        mainPanel.add(Btnanswer, BorderLayout.NORTH);

        add(mainPanel);
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
         /* if (currentIndex < flashcards.size()) {
            currentIndex--;
            Flashcard flashcard = flashcards.get(currentIndex);
            lblFlashcard.setText(flashcard.getAnswer());
            currentIndex++;
        } else {
            JOptionPane.showMessageDialog(null, "No more flashcards!.");
        }

        */


        private void onAnswerBtnClick () {
            if (!showingQuestion) {
                displayNextFlashcard();
            }
        }

        public static void main (String[]args){
            SwingUtilities.invokeLater(() -> {
                List<Flashcard> flashcards = new ArrayList<>();
                new FlashcardPlayScreen(flashcards);
            });
        }
    }



