package view;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class QuizQuestions extends JFrame {
    private JPanel pnlMain = new JPanel();
    private JPanel pnlCenter = new JPanel();
    //hj
    public QuizQuestions() {
        this.setTitle("Quizmania");
        this.setContentPane(pnlMain);
        pnlMain.setLayout(new BorderLayout());


        //CENTER_PANEL-----START
        JButton btnBack = new JButton();
        JButton btnAddNewQuestion = new JButton();
        JLabel lblTitle = new JLabel();
        JList questionsList = new JList();
        SpringLayout springLayoutCenterPanel = new SpringLayout();

        pnlMain.add(pnlCenter, BorderLayout.CENTER);
        pnlCenter.setLayout(springLayoutCenterPanel);

        btnBack.setText("Back");
        pnlCenter.add(btnBack);
        springLayoutCenterPanel.putConstraint(SpringLayout.WEST, btnBack, 20, SpringLayout.WEST, pnlCenter);
        springLayoutCenterPanel.putConstraint(SpringLayout.NORTH, btnBack, 20, SpringLayout.NORTH, pnlCenter);

        lblTitle.setText("Questions");
        pnlCenter.add(lblTitle);
        springLayoutCenterPanel.putConstraint(SpringLayout.WEST, lblTitle, 20, SpringLayout.EAST, btnBack);
        springLayoutCenterPanel.putConstraint(SpringLayout.EAST, lblTitle, -20, SpringLayout.WEST, btnAddNewQuestion);
        springLayoutCenterPanel.putConstraint(SpringLayout.NORTH, lblTitle, 0, SpringLayout.NORTH, btnBack);
        springLayoutCenterPanel.putConstraint(SpringLayout.SOUTH, lblTitle, 0, SpringLayout.SOUTH, btnBack);

        btnAddNewQuestion.setText("+");
        pnlCenter.add(btnAddNewQuestion);
        springLayoutCenterPanel.putConstraint(SpringLayout.EAST, btnAddNewQuestion, -20, SpringLayout.EAST, pnlCenter);
        springLayoutCenterPanel.putConstraint(SpringLayout.NORTH, btnAddNewQuestion, 0, SpringLayout.NORTH, btnBack);

        questionsList.setBorder(new TitledBorder("Questions"));
        pnlCenter.add(questionsList);
        springLayoutCenterPanel.putConstraint(SpringLayout.WEST, questionsList, 20, SpringLayout.WEST, pnlCenter);
        springLayoutCenterPanel.putConstraint(SpringLayout.EAST, questionsList, -20, SpringLayout.EAST, pnlCenter);
        springLayoutCenterPanel.putConstraint(SpringLayout.NORTH, questionsList, 20, SpringLayout.SOUTH, btnBack);
        springLayoutCenterPanel.putConstraint(SpringLayout.SOUTH, questionsList, -10, SpringLayout.SOUTH, pnlCenter);
        //CENTER_PANEL-----END


        this.pack();
        this.setVisible(true);
        this.setSize(new Dimension(600, 400));
        this.setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
