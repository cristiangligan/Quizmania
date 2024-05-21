package view;

import controller.Controller;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class CreateQuestions extends JFrame{
    private JTextArea ans1 = new JTextArea();
    private JTextArea ans2 = new JTextArea();
    private JTextArea ans3 = new JTextArea();
    private JTextArea ans4 = new JTextArea();
    private JRadioButton btnA1;
    private JRadioButton btnA2;
    private JRadioButton btnA3;
    private JRadioButton btnA4;
    private JPanel pnlMain;
    private JButton btnCancel = new JButton();
    private JButton btnSave = new JButton();
    private JTextArea areaQuestion = new JTextArea();
    JScrollPane scrollPaneQuestion;
    private Controller controller;

    public CreateQuestions(Controller controller) {
        this.controller = controller;
        this.setTitle("Add question");

        setContentPane(pnlMain);
        setResizable(false);
        setPreferredSize(new Dimension(600, 460));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
        setVisible(true);

        areaQuestion.setWrapStyleWord(true);
        areaQuestion.setLineWrap(true);
        JScrollPane scrollPaneQuestion = new JScrollPane(areaQuestion);
        scrollPaneQuestion.setBorder(new TitledBorder("Question"));
        scrollPaneQuestion.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPaneQuestion.add(scrollPaneQuestion);

        ButtonGroup btnGroup = new ButtonGroup();
        btnGroup.add(btnA1);
        btnGroup.add(btnA2);
        btnGroup.add(btnA3);
        btnGroup.add(btnA4);

    }
    private void onCancelBtnClick() {
        controller.handleCancelQuestionScreen();
    }

    private void onSaveBtnClick() {
        /*controller.handleSaveNewQu();*/
    }
}
