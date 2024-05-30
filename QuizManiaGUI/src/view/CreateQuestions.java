package view;

import controller.Controller;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
    private JButton btnCancel;
    private JButton btnSave;
    private JTextArea areaQuestion = new JTextArea();
    private JScrollPane scrollPaneQuestion;
    private Controller controller;

    public CreateQuestions(Controller controller) {
        this.controller = controller;


        setContentPane(pnlMain);
        setResizable(false);
        setPreferredSize(new Dimension(600, 460));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
        setVisible(true);


        areaQuestion.setWrapStyleWord(true);
        areaQuestion.setLineWrap(true);
        scrollPaneQuestion = new JScrollPane(areaQuestion);
        scrollPaneQuestion.setBorder(new TitledBorder("Question"));
        scrollPaneQuestion.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

//        ButtonGroup btnGroup = new ButtonGroup();
//        btnGroup.add(btnA1);
//        btnGroup.add(btnA2);
//        btnGroup.add(btnA3);
//        btnGroup.add(btnA4);

        btnCancel.addActionListener(e -> onCancelBtnClick());
        btnSave.addActionListener(e -> onSaveBtnClick());


        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       //this.setLocationRelativeTo(null);

    }
    private void onCancelBtnClick() {
        controller.handleCancelQuestionScreen();
        this.dispose();
    }

    private void onSaveBtnClick() {
        controller.handleSaveNewQuestion();
    }

    public String getAns1(){
        return ans1.getText();
    }
    public String getAns2(){
        return ans2.getText();
    }
    public String getAns3(){
        return ans3.getText();
    }
    public String getAns4(){
        return ans4.getText();
    }
    public Boolean getBtn1(){
        return btnA1.isSelected();
    }
    public Boolean getBtn2(){
        return btnA2.isSelected();
    }
    public Boolean getBtn3(){
        return btnA3.isSelected();
    }
    public Boolean getBtn4(){
        return btnA4.isSelected();
    }
    public String getQuestion(){
        JViewport viewport = scrollPaneQuestion.getViewport();
        JTextArea textArea = (JTextArea) viewport.getView();
//        return textArea.getText();
        return textArea.getText();
    }
}
