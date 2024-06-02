package view;

import controller.Controller;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class QuizQuestionAnswerFrame extends JFrame {
    private Controller controller;

    private JPanel pnlMain = new JPanel();

    private JTextArea questionArea = new JTextArea();

    private JTextArea answer1Area = new JTextArea();
    private JTextArea answer2Area = new JTextArea();
    private JTextArea answer3Area = new JTextArea();
    private JTextArea answer4Area = new JTextArea();

    private JRadioButton rbtnCorrectness1 = new JRadioButton();
    private JRadioButton rbtnCorrectness2 = new JRadioButton();
    private JRadioButton rbtnCorrectness3 = new JRadioButton();
    private JRadioButton rbtnCorrectness4 = new JRadioButton();

    private JButton btnCancel = new JButton();
    private JButton btnSave = new JButton();

    public QuizQuestionAnswerFrame(Controller controller) {
        this.controller = controller;
        this.setTitle("Quizmania");
        SpringLayout springLayout = new SpringLayout();
        this.setContentPane(pnlMain);
        pnlMain.setLayout(springLayout);

        JScrollPane scrollPaneQuestion = new JScrollPane(questionArea);
        JScrollPane scrollPaneAnswer1 = new JScrollPane(answer1Area);
        JScrollPane scrollPaneAnswer2 = new JScrollPane(answer2Area);
        JScrollPane scrollPaneAnswer3 = new JScrollPane(answer3Area);
        JScrollPane scrollPaneAnswer4 = new JScrollPane(answer4Area);

        questionArea.setWrapStyleWord(true);
        questionArea.setLineWrap(true);
        scrollPaneQuestion.setBorder(new TitledBorder("Question"));
        scrollPaneQuestion.setVerticalScrollBarPolicy (ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        pnlMain.add(scrollPaneQuestion);
        springLayout.putConstraint(SpringLayout.NORTH, scrollPaneQuestion, 20, SpringLayout.NORTH, pnlMain);
        springLayout.putConstraint(SpringLayout.WEST, scrollPaneQuestion, 20, SpringLayout.WEST, pnlMain);
        springLayout.putConstraint(SpringLayout.EAST, scrollPaneQuestion, -20, SpringLayout.EAST, pnlMain);
        springLayout.putConstraint(SpringLayout.SOUTH, scrollPaneQuestion, -544, SpringLayout.SOUTH, pnlMain);

        answer1Area.setWrapStyleWord(true);
        answer1Area.setLineWrap(true);
        scrollPaneAnswer1.setBorder(new TitledBorder("Answer 1"));
        scrollPaneAnswer1.setVerticalScrollBarPolicy (ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        pnlMain.add(scrollPaneAnswer1);
        springLayout.putConstraint(SpringLayout.NORTH, scrollPaneAnswer1, 20, SpringLayout.SOUTH, scrollPaneQuestion);
        springLayout.putConstraint(SpringLayout.WEST, scrollPaneAnswer1, 0, SpringLayout.WEST, scrollPaneQuestion);
        springLayout.putConstraint(SpringLayout.EAST, scrollPaneAnswer1, -80, SpringLayout.EAST, pnlMain);
        springLayout.putConstraint(SpringLayout.SOUTH, scrollPaneAnswer1, -20, SpringLayout.NORTH, scrollPaneAnswer2);

        answer2Area.setWrapStyleWord(true);
        answer2Area.setLineWrap(true);
        scrollPaneAnswer2.setBorder(new TitledBorder("Answer 2"));
        scrollPaneAnswer2.setVerticalScrollBarPolicy (ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        pnlMain.add(scrollPaneAnswer2);
        springLayout.putConstraint(SpringLayout.NORTH, scrollPaneAnswer2, 252, SpringLayout.NORTH, pnlMain);
        springLayout.putConstraint(SpringLayout.WEST, scrollPaneAnswer2, 0, SpringLayout.WEST, scrollPaneAnswer1);
        springLayout.putConstraint(SpringLayout.EAST, scrollPaneAnswer2, -80, SpringLayout.EAST, pnlMain);
        springLayout.putConstraint(SpringLayout.SOUTH, scrollPaneAnswer2, -312, SpringLayout.SOUTH, pnlMain);

        answer3Area.setWrapStyleWord(true);
        answer3Area.setLineWrap(true);
        scrollPaneAnswer3.setBorder(new TitledBorder("Answer 3"));
        scrollPaneAnswer3.setVerticalScrollBarPolicy (ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        pnlMain.add(scrollPaneAnswer3);
        springLayout.putConstraint(SpringLayout.NORTH, scrollPaneAnswer3, 20, SpringLayout.SOUTH, scrollPaneAnswer2);
        springLayout.putConstraint(SpringLayout.WEST, scrollPaneAnswer3, 0, SpringLayout.WEST, scrollPaneAnswer2);
        springLayout.putConstraint(SpringLayout.EAST, scrollPaneAnswer3, -80, SpringLayout.EAST, pnlMain);
        springLayout.putConstraint(SpringLayout.SOUTH, scrollPaneAnswer3, -20, SpringLayout.NORTH, scrollPaneAnswer4);

        answer4Area.setWrapStyleWord(true);
        answer4Area.setLineWrap(true);
        scrollPaneAnswer4.setBorder(new TitledBorder("Answer 4"));
        scrollPaneAnswer4.setVerticalScrollBarPolicy (ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        pnlMain.add(scrollPaneAnswer4);
        springLayout.putConstraint(SpringLayout.NORTH, scrollPaneAnswer4, 484, SpringLayout.NORTH, pnlMain);
        springLayout.putConstraint(SpringLayout.WEST, scrollPaneAnswer4, 20, SpringLayout.WEST, pnlMain);
        springLayout.putConstraint(SpringLayout.EAST, scrollPaneAnswer4, -80, SpringLayout.EAST, pnlMain);
        springLayout.putConstraint(SpringLayout.SOUTH, scrollPaneAnswer4, -80, SpringLayout.SOUTH, pnlMain);

        JLabel labelCorrectness = new JLabel("Correct");
        labelCorrectness.setHorizontalAlignment(SwingConstants.CENTER);
        labelCorrectness.setVerticalAlignment(SwingConstants.CENTER);
        pnlMain.add(labelCorrectness);
        springLayout.putConstraint(SpringLayout.NORTH, labelCorrectness, 0, SpringLayout.NORTH, scrollPaneAnswer1);
        springLayout.putConstraint(SpringLayout.WEST, labelCorrectness, 0, SpringLayout.EAST, scrollPaneAnswer1);
        springLayout.putConstraint(SpringLayout.EAST, labelCorrectness, 0, SpringLayout.EAST, pnlMain);

        pnlMain.add(rbtnCorrectness1);
        springLayout.putConstraint(SpringLayout.NORTH, rbtnCorrectness1, 0, SpringLayout.NORTH, labelCorrectness);
        springLayout.putConstraint(SpringLayout.WEST, rbtnCorrectness1, 25, SpringLayout.EAST, scrollPaneAnswer1);
        springLayout.putConstraint(SpringLayout.EAST, rbtnCorrectness1, -20, SpringLayout.EAST, pnlMain);
        springLayout.putConstraint(SpringLayout.SOUTH, rbtnCorrectness1, 0, SpringLayout.SOUTH, scrollPaneAnswer1);

        pnlMain.add(rbtnCorrectness2);
        springLayout.putConstraint(SpringLayout.NORTH, rbtnCorrectness2, 0, SpringLayout.NORTH, scrollPaneAnswer2);
        springLayout.putConstraint(SpringLayout.WEST, rbtnCorrectness2, 25, SpringLayout.EAST, scrollPaneAnswer2);
        springLayout.putConstraint(SpringLayout.EAST, rbtnCorrectness2, -20, SpringLayout.EAST, pnlMain);
        springLayout.putConstraint(SpringLayout.SOUTH, rbtnCorrectness2, 0, SpringLayout.SOUTH, scrollPaneAnswer2);

        pnlMain.add(rbtnCorrectness3);
        springLayout.putConstraint(SpringLayout.NORTH, rbtnCorrectness3, 0, SpringLayout.NORTH, scrollPaneAnswer3);
        springLayout.putConstraint(SpringLayout.WEST, rbtnCorrectness3, 25, SpringLayout.EAST, scrollPaneAnswer3);
        springLayout.putConstraint(SpringLayout.EAST, rbtnCorrectness3, -20, SpringLayout.EAST, pnlMain);
        springLayout.putConstraint(SpringLayout.SOUTH, rbtnCorrectness3, 0, SpringLayout.SOUTH, scrollPaneAnswer3);

        pnlMain.add(rbtnCorrectness4);
        springLayout.putConstraint(SpringLayout.NORTH, rbtnCorrectness4, 0, SpringLayout.NORTH, scrollPaneAnswer4);
        springLayout.putConstraint(SpringLayout.WEST, rbtnCorrectness4, 25, SpringLayout.EAST, scrollPaneAnswer4);
        springLayout.putConstraint(SpringLayout.EAST, rbtnCorrectness4, -20, SpringLayout.EAST, pnlMain);
        springLayout.putConstraint(SpringLayout.SOUTH, rbtnCorrectness4, 0, SpringLayout.SOUTH, scrollPaneAnswer4);

        btnCancel.setText("Cancel");
        btnCancel.addActionListener(e -> onCancelBtnClick());
        pnlMain.add(btnCancel);
        springLayout.putConstraint(SpringLayout.NORTH, btnCancel, 20, SpringLayout.SOUTH, scrollPaneAnswer4);
        springLayout.putConstraint(SpringLayout.WEST, btnCancel, 0, SpringLayout.WEST, scrollPaneAnswer4);
        springLayout.putConstraint(SpringLayout.SOUTH, btnCancel, -20, SpringLayout.SOUTH, pnlMain);

        btnSave.setText("Save");
        btnSave.addActionListener(e -> onSaveBtnClick());
        pnlMain.add(btnSave);
        springLayout.putConstraint(SpringLayout.NORTH, btnSave, 20, SpringLayout.SOUTH, scrollPaneAnswer4);
        springLayout.putConstraint(SpringLayout.EAST, btnSave, -20, SpringLayout.EAST, pnlMain);
        springLayout.putConstraint(SpringLayout.SOUTH, btnSave, -20, SpringLayout.SOUTH, pnlMain);

        this.pack();
        this.setVisible(true);
        this.setSize(new Dimension(600, 684));
        this.setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void onCancelBtnClick() {
        controller.handleCancelQuestionScreen();
        this.dispose();
    }

    private void onSaveBtnClick() {
        controller.handleSaveNewQuestion();
    }

    public String getAns1(){
        return answer1Area.getText();
    }
    public String getAns2(){
        return answer2Area.getText();
    }
    public String getAns3(){
        return answer3Area.getText();
    }
    public String getAns4(){
        return answer4Area.getText();
    }
    public Boolean getBtn1(){
        return rbtnCorrectness1.isSelected();
    }
    public Boolean getBtn2(){
        return rbtnCorrectness2.isSelected();
    }
    public Boolean getBtn3(){
        return rbtnCorrectness3.isSelected();
    }
    public Boolean getBtn4(){
        return rbtnCorrectness4.isSelected();
    }
    public String getQuestion(){
        return questionArea.getText();
    }
}
