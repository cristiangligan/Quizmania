package view;

import javax.swing.*;
import java.awt.*;

public class QuizzesScreen extends JFrame {
    private JPanel pnlMain = new JPanel();
    private JButton btnBack = new JButton();
    private JButton btnAddNewQuiz = new JButton();
    private JLabel lblTitle = new JLabel();

    private JList setsList = new JList();
    public QuizzesScreen() {
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

        pnlMain.add(setsList);
        springLayout.putConstraint(SpringLayout.WEST, setsList, 20, SpringLayout.WEST, pnlMain);
        springLayout.putConstraint(SpringLayout.EAST, setsList, -20, SpringLayout.EAST, pnlMain);
        springLayout.putConstraint(SpringLayout.NORTH, setsList, 20, SpringLayout.SOUTH, btnBack);
        springLayout.putConstraint(SpringLayout.SOUTH, setsList, -20, SpringLayout.SOUTH, pnlMain);

        this.pack();
        this.setVisible(true);
        this.setSize(new Dimension(400, 400));
        this.setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
