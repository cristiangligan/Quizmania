package view;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class FlashcardsFrame extends JFrame {
    private JPanel pnlMain = new JPanel();
    private JPanel pnlCenter = new JPanel();
    private JPanel pnlRight = new JPanel();
//hj
    public FlashcardsFrame() {
        this.setTitle("Quizmania");
        this.setContentPane(pnlMain);
        pnlMain.setLayout(new BorderLayout());


        //CENTER_PANEL-----START
        JButton btnBack = new JButton();
        JButton btnAddNewFlashCard = new JButton();
        JLabel lblTitle = new JLabel();
        JList flashcardsList = new JList();
        SpringLayout springLayoutCenterPanel = new SpringLayout();

        pnlMain.add(pnlCenter, BorderLayout.CENTER);
        pnlCenter.setLayout(springLayoutCenterPanel);

        btnBack.setText("Back");
        pnlCenter.add(btnBack);
        springLayoutCenterPanel.putConstraint(SpringLayout.WEST, btnBack, 20, SpringLayout.WEST, pnlCenter);
        springLayoutCenterPanel.putConstraint(SpringLayout.NORTH, btnBack, 20, SpringLayout.NORTH, pnlCenter);

        lblTitle.setText("Flashcards");
        pnlCenter.add(lblTitle);
        springLayoutCenterPanel.putConstraint(SpringLayout.WEST, lblTitle, 20, SpringLayout.EAST, btnBack);
        springLayoutCenterPanel.putConstraint(SpringLayout.EAST, lblTitle, -20, SpringLayout.WEST, btnAddNewFlashCard);
        springLayoutCenterPanel.putConstraint(SpringLayout.NORTH, lblTitle, 0, SpringLayout.NORTH, btnBack);
        springLayoutCenterPanel.putConstraint(SpringLayout.SOUTH, lblTitle, 0, SpringLayout.SOUTH, btnBack);

        btnAddNewFlashCard.setText("+");
        pnlCenter.add(btnAddNewFlashCard);
        springLayoutCenterPanel.putConstraint(SpringLayout.EAST, btnAddNewFlashCard, -20, SpringLayout.EAST, pnlCenter);
        springLayoutCenterPanel.putConstraint(SpringLayout.NORTH, btnAddNewFlashCard, 0, SpringLayout.NORTH, btnBack);

        flashcardsList.setBorder(new TitledBorder("Questions"));
        pnlCenter.add(flashcardsList);
        springLayoutCenterPanel.putConstraint(SpringLayout.WEST, flashcardsList, 20, SpringLayout.WEST, pnlCenter);
        springLayoutCenterPanel.putConstraint(SpringLayout.EAST, flashcardsList, -20, SpringLayout.EAST, pnlCenter);
        springLayoutCenterPanel.putConstraint(SpringLayout.NORTH, flashcardsList, 20, SpringLayout.SOUTH, btnBack);
        springLayoutCenterPanel.putConstraint(SpringLayout.SOUTH, flashcardsList, -10, SpringLayout.SOUTH, pnlCenter);
        //CENTER_PANEL-----END



        //RIGHT_PANEL-----START
        JTextArea answerTextArea = new JTextArea();
        SpringLayout springLayoutRightPanel = new SpringLayout();

        pnlMain.add(pnlRight, BorderLayout.LINE_END);
        pnlRight.setLayout(springLayoutRightPanel);
        pnlRight.setPreferredSize(new Dimension(200, 0));

        answerTextArea.setBorder(new TitledBorder("Answer"));
        pnlRight.add(answerTextArea);
        springLayoutRightPanel.putConstraint(SpringLayout.WEST, answerTextArea, 10, SpringLayout.WEST, pnlRight);
        springLayoutRightPanel.putConstraint(SpringLayout.EAST, answerTextArea, -10, SpringLayout.EAST, pnlRight);
        springLayoutRightPanel.putConstraint(SpringLayout.NORTH, answerTextArea, 10, SpringLayout.NORTH, pnlRight);
        springLayoutRightPanel.putConstraint(SpringLayout.SOUTH, answerTextArea, -10, SpringLayout.SOUTH, pnlRight);
        //RIGHT_PANEL-----END


        this.pack();
        this.setVisible(true);
        this.setSize(new Dimension(600, 400));
        this.setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
