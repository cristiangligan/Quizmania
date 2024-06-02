package view;

import controller.Controller;
import model.Question;
import model.Quiz;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.List;

public class QuizQuestions extends JFrame {
    private JPanel pnlMain = new JPanel();
    private JPanel pnlCenter = new JPanel();
    private JList questionList = new JList();
    private Controller controller;

    //emma o savannah - screen for questions
    public QuizQuestions(Controller controller) {
        this.setTitle("Quizmania");
        this.setContentPane(pnlMain);
        pnlMain.setLayout(new BorderLayout());
        this.controller = controller;


        //CENTER_PANEL-----START
        JButton btnBack = new JButton();
        JButton btnAddNewQuestion = new JButton();
        JLabel lblTitle = new JLabel();
        SpringLayout springLayoutCenterPanel = new SpringLayout();

        pnlMain.add(pnlCenter, BorderLayout.CENTER);
        pnlCenter.setLayout(springLayoutCenterPanel);

        btnBack.setText("Back");
        btnBack.addActionListener(e -> onBackBtnClick());
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
        btnAddNewQuestion.addActionListener(e -> onAddNewQuestionBtnClick());
        springLayoutCenterPanel.putConstraint(SpringLayout.EAST, btnAddNewQuestion, -20, SpringLayout.EAST, pnlCenter);
        springLayoutCenterPanel.putConstraint(SpringLayout.NORTH, btnAddNewQuestion, 0, SpringLayout.NORTH, btnBack);

        questionList.setBorder(new TitledBorder("Questions"));
        pnlCenter.add(questionList);
        springLayoutCenterPanel.putConstraint(SpringLayout.WEST, questionList, 20, SpringLayout.WEST, pnlCenter);
        springLayoutCenterPanel.putConstraint(SpringLayout.EAST, questionList, -20, SpringLayout.EAST, pnlCenter);
        springLayoutCenterPanel.putConstraint(SpringLayout.NORTH, questionList, 20, SpringLayout.SOUTH, btnBack);
        springLayoutCenterPanel.putConstraint(SpringLayout.SOUTH, questionList, -10, SpringLayout.SOUTH, pnlCenter);
        //CENTER_PANEL-----END

        this.pack();
        this.setVisible(true);
        this.setSize(new Dimension(600, 400));
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void onAddNewQuestionBtnClick() {
        controller.handleAddNewQuestion();
    }

    private void onBackBtnClick() {
        controller.handleBackToQuizzesScreen();
    }

    public Question getSelectedQuestion() {
        Question question = (Question) questionList.getSelectedValue();
        return question;
    }

    // savannah o emma - displays list of questions on screen
    public void displayQuestionList(List<Question> questions) {
        questionList.setListData(questions.toArray());
        if(!questions.isEmpty()) {
            questionList.setSelectedIndex(0);
            //displayAnswer();
        }

    }
}
