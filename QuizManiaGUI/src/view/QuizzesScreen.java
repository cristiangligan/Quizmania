package view;

import controller.Controller;
import model.Quiz;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class QuizzesScreen extends JFrame {
    private JPanel pnlMain = new JPanel();
    private JButton btnBack = new JButton();
    private JButton btnAddNewQuiz = new JButton();
    private JLabel lblTitle = new JLabel();
    private JButton btnOpen = new JButton();
    private Controller controller;

    private static JList setsList = new JList();
    public QuizzesScreen(Controller controller) {
        this.controller = controller;
        this.setTitle("Quizmania");
        SpringLayout springLayout = new SpringLayout();
        this.setContentPane(pnlMain);
        pnlMain.setLayout(springLayout);

        btnBack.setText("Back");
        pnlMain.add(btnBack);
        springLayout.putConstraint(SpringLayout.WEST, btnBack, 20, SpringLayout.WEST, pnlMain);
        springLayout.putConstraint(SpringLayout.NORTH, btnBack, 20, SpringLayout.NORTH, pnlMain);

        lblTitle.setText("Quizzes");
        pnlMain.add(lblTitle);
        springLayout.putConstraint(SpringLayout.WEST, lblTitle, 20, SpringLayout.EAST, btnBack);
        springLayout.putConstraint(SpringLayout.EAST, lblTitle, -20, SpringLayout.WEST, btnAddNewQuiz);
        springLayout.putConstraint(SpringLayout.NORTH, lblTitle, 0, SpringLayout.NORTH, btnBack);
        springLayout.putConstraint(SpringLayout.SOUTH, lblTitle, 0, SpringLayout.SOUTH, btnBack);

        btnAddNewQuiz.setText("+");
        pnlMain.add(btnAddNewQuiz);
        springLayout.putConstraint(SpringLayout.EAST, btnAddNewQuiz, -20, SpringLayout.EAST, pnlMain);
        springLayout.putConstraint(SpringLayout.NORTH, btnAddNewQuiz, 0, SpringLayout.NORTH, btnBack);
        btnAddNewQuiz.addActionListener(e -> onBtnAddNewQuizClick());

        pnlMain.add(setsList);
        springLayout.putConstraint(SpringLayout.WEST, setsList, 20, SpringLayout.WEST, pnlMain);
        springLayout.putConstraint(SpringLayout.EAST, setsList, -20, SpringLayout.EAST, pnlMain);
        springLayout.putConstraint(SpringLayout.NORTH, setsList, 20, SpringLayout.SOUTH, btnBack);
        springLayout.putConstraint(SpringLayout.SOUTH, setsList, -20, SpringLayout.SOUTH, pnlMain);

        btnOpen.setText("Open");
        pnlMain.add(btnOpen);
        springLayout.putConstraint(SpringLayout.EAST, btnOpen, 0, SpringLayout.EAST, setsList);
        springLayout.putConstraint(SpringLayout.SOUTH, btnOpen, -20, SpringLayout.SOUTH, pnlMain);
        btnOpen.addActionListener(e -> onBtnOpenClick());

        this.pack();
        this.setVisible(true);
        this.setSize(new Dimension(400, 400));
        this.setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void onBtnAddNewQuizClick () {
        controller.handleAddNewQuiz();
    }
    private void onBackBtnClick () {
        controller.handleBackToMainScreen();
    }

    private void onBtnOpenClick () {
        controller.openSelectedQuiz();
    }

    public static Quiz getSelectedQuiz() {
        Quiz quiz;
        quiz = (Quiz) setsList.getSelectedValue();
        return quiz;
    }

    public void displayQuizzesList (List < Quiz > quiz) {
        setsList.setListData(quiz.toArray());
        if (!quiz.isEmpty()) {
            setsList.setSelectedIndex(0);
        }
    }
}
