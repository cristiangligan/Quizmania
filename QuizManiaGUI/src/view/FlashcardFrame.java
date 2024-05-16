package view;

import controller.Controller;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class FlashcardFrame extends JFrame {
    private Controller controller;
    private JPanel pnlMain = new JPanel();
    private JButton btnSave = new JButton();
    private JButton btnCancel = new JButton();
    private JTextArea areaQuestion = new JTextArea();
    private JTextArea areaAnswer = new JTextArea();

    public FlashcardFrame(Controller controller) {
        this.controller = controller;
        this.setTitle("Quizmania");
        SpringLayout springLayout = new SpringLayout();
        this.setContentPane(pnlMain);
        pnlMain.setLayout(springLayout);

        areaQuestion.setWrapStyleWord(true);
        areaQuestion.setLineWrap(true);
        JScrollPane scrollPaneQuestion = new JScrollPane(areaQuestion);
        scrollPaneQuestion.setBorder(new TitledBorder("Question"));
        scrollPaneQuestion.setVerticalScrollBarPolicy (ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        pnlMain.add(scrollPaneQuestion);
        springLayout.putConstraint(SpringLayout.WEST, scrollPaneQuestion, 20, SpringLayout.WEST, pnlMain);
        springLayout.putConstraint(SpringLayout.EAST, scrollPaneQuestion, -20, SpringLayout.EAST, pnlMain);
        springLayout.putConstraint(SpringLayout.NORTH, scrollPaneQuestion, 20, SpringLayout.NORTH, pnlMain);
        springLayout.putConstraint(SpringLayout.SOUTH, scrollPaneQuestion, -280, SpringLayout.SOUTH, pnlMain);

        areaAnswer.setWrapStyleWord(true);
        areaAnswer.setLineWrap(true);
        JScrollPane scrollPaneAnswer = new JScrollPane(areaAnswer);
        scrollPaneAnswer.setBorder(new TitledBorder("Answer"));
        scrollPaneAnswer.setVerticalScrollBarPolicy (ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        pnlMain.add(scrollPaneAnswer);
        springLayout.putConstraint(SpringLayout.WEST, scrollPaneAnswer, 0, SpringLayout.WEST, scrollPaneQuestion);
        springLayout.putConstraint(SpringLayout.EAST, scrollPaneAnswer, 0, SpringLayout.EAST, scrollPaneQuestion);
        springLayout.putConstraint(SpringLayout.NORTH, scrollPaneAnswer, 20, SpringLayout.SOUTH, scrollPaneQuestion);
        springLayout.putConstraint(SpringLayout.SOUTH, scrollPaneAnswer, -20, SpringLayout.NORTH, btnCancel);

        btnCancel.setText("Cancel");
        pnlMain.add(btnCancel);
        btnCancel.addActionListener(e -> onCancelBtnClick());
        springLayout.putConstraint(SpringLayout.WEST, btnCancel, 0, SpringLayout.WEST, scrollPaneAnswer);
        springLayout.putConstraint(SpringLayout.SOUTH, btnCancel, -20, SpringLayout.SOUTH, pnlMain);

        btnSave.setText("Save");
        pnlMain.add(btnSave);
        btnSave.addActionListener(e -> onSaveBtnClick());
        springLayout.putConstraint(SpringLayout.EAST, btnSave, 0, SpringLayout.EAST, scrollPaneAnswer);
        springLayout.putConstraint(SpringLayout.SOUTH, btnSave, -20, SpringLayout.SOUTH, pnlMain);

        this.pack();
        this.setVisible(true);
        this.setSize(new Dimension(400, 400));
        this.setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void onCancelBtnClick() {
        controller.handleCancelFlashcardFrame();
    }

    private void onSaveBtnClick() {
        controller.handleSaveNewFlashcard();
    }

    public String getQuestion() {
        String question = areaQuestion.getText();
        return question;
    }
    public String getAnswer() {
        String answer = areaAnswer.getText();
        return answer;
    }
}
