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

/**
 * FlashcardPlayScreen is the UI for the flashcard play mode where users can view and
 * interact with flashcard.
 *
 * <p> This class provides a graphical interface to display flashcards,
 * show their name and navigate through them. </p>
 * @author Kaye Moran
 * @author Jessica Puente
 * @author Cristian Gligan
 */
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

    /**
     * Contructs a new FlashcardPlayScreen
     *
     * @param controller The controller object that handles logic for user interaction.
     * @param flashcards The list of flashcards to be displayed.
     */

    public FlashcardPlayScreen(Controller controller, List<Flashcard> flashcards) {
        this.controller = controller;
        this.flashcards = flashcards;
        initializeUI();
    }

    /**
     * Initialize the UI components and layout for the flashcard play screen.
     */
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

    /**
     * Displays the next flashcard, showing either the question or the answer.
     */
    public void displayNextFlashcard() {
        try {
            if (currentIndex < flashcards.size()) {
                Flashcard flashcard = flashcards.get(currentIndex);

                if (showingQuestion) {
                    lblFlashcard.setText("<html>" + flashcard.getQuestion() + "</html>");

                } else {
                    lblFlashcard.setText("<html>" + flashcard.getAnswer() + "</html>");
                    //currentIndex++;
                }
                //showingQuestion = !showingQuestion;

            } else {
                lblFlashcard.setText("All done!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the click event for the next button, displaying the next flashcard.
     */
    private void onNextBtnClick() {
        try {
            if (currentIndex < flashcards.size()) {
                currentIndex++;
                showingQuestion = true;
                displayNextFlashcard();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the click event for the answer button, showing the answer of the current flashcard.
     */
    private void onAnswerBtnClick () {
        if (currentIndex < flashcards.size() &&  showingQuestion) {
            showingQuestion = false;
            displayNextFlashcard();
        }
    }

    /**
     * Handles the click event for the Exit button, exiting flashcard play mode.
     */
    private void onExitBtnClick() {
        controller.handleExitPlayMode();
    }


}



