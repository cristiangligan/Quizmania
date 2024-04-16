package view;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class FlashcardsFrame extends JFrame {
    private JPanel mainPanel = new JPanel();
    private JPanel centerPanel = new JPanel();
    private JPanel rightPanel = new JPanel();
//hj
    public FlashcardsFrame() {
        this.setTitle("Quizmania");
        this.setContentPane(mainPanel);
        mainPanel.setLayout(new BorderLayout());


        //CENTER_PANEL-----START
        JButton backButton = new JButton();
        JButton addNewFlashCardButton = new JButton();
        JLabel titleLabel = new JLabel();
        JList flashcardsList = new JList();
        SpringLayout springLayoutCenterPanel = new SpringLayout();

        mainPanel.add(centerPanel, BorderLayout.CENTER);
        centerPanel.setLayout(springLayoutCenterPanel);

        backButton.setText("Back");
        centerPanel.add(backButton);
        springLayoutCenterPanel.putConstraint(SpringLayout.WEST, backButton, 20, SpringLayout.WEST, centerPanel);
        springLayoutCenterPanel.putConstraint(SpringLayout.NORTH, backButton, 20, SpringLayout.NORTH, centerPanel);

        titleLabel.setText("Flashcards");
        centerPanel.add(titleLabel);
        springLayoutCenterPanel.putConstraint(SpringLayout.WEST, titleLabel, 20, SpringLayout.EAST, backButton);
        springLayoutCenterPanel.putConstraint(SpringLayout.EAST, titleLabel, -20, SpringLayout.WEST, addNewFlashCardButton);
        springLayoutCenterPanel.putConstraint(SpringLayout.NORTH, titleLabel, 0, SpringLayout.NORTH, backButton);
        springLayoutCenterPanel.putConstraint(SpringLayout.SOUTH, titleLabel, 0, SpringLayout.SOUTH, backButton);

        addNewFlashCardButton.setText("+");
        centerPanel.add(addNewFlashCardButton);
        springLayoutCenterPanel.putConstraint(SpringLayout.EAST, addNewFlashCardButton, -20, SpringLayout.EAST, centerPanel);
        springLayoutCenterPanel.putConstraint(SpringLayout.NORTH, addNewFlashCardButton, 0, SpringLayout.NORTH, backButton);

        flashcardsList.setBorder(new TitledBorder("Questions"));
        centerPanel.add(flashcardsList);
        springLayoutCenterPanel.putConstraint(SpringLayout.WEST, flashcardsList, 20, SpringLayout.WEST, centerPanel);
        springLayoutCenterPanel.putConstraint(SpringLayout.EAST, flashcardsList, -20, SpringLayout.EAST, centerPanel);
        springLayoutCenterPanel.putConstraint(SpringLayout.NORTH, flashcardsList, 20, SpringLayout.SOUTH, backButton);
        springLayoutCenterPanel.putConstraint(SpringLayout.SOUTH, flashcardsList, -10, SpringLayout.SOUTH, centerPanel);
        //CENTER_PANEL-----END






        //RIGHT_PANEL-----START
        JTextArea answerTextArea = new JTextArea();
        SpringLayout springLayoutRightPanel = new SpringLayout();

        mainPanel.add(rightPanel, BorderLayout.LINE_END);
        rightPanel.setLayout(springLayoutRightPanel);
        rightPanel.setPreferredSize(new Dimension(200, 0));

        answerTextArea.setBorder(new TitledBorder("Answer"));
        rightPanel.add(answerTextArea);
        springLayoutRightPanel.putConstraint(SpringLayout.WEST, answerTextArea, 10, SpringLayout.WEST, rightPanel);
        springLayoutRightPanel.putConstraint(SpringLayout.EAST, answerTextArea, -10, SpringLayout.EAST, rightPanel);
        springLayoutRightPanel.putConstraint(SpringLayout.NORTH, answerTextArea, 10, SpringLayout.NORTH, rightPanel);
        springLayoutRightPanel.putConstraint(SpringLayout.SOUTH, answerTextArea, -10, SpringLayout.SOUTH, rightPanel);
        //RIGHT_PANEL-----END







        this.pack();
        this.setVisible(true);
        this.setSize(new Dimension(600, 400));
        this.setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
