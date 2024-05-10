package view;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QuestionScreen extends JDialog{
    private JButton btnEnter;
    private JLabel lblQuestion;
    private JPanel pnlMain;
    private JPanel pnlAnswers;
    private ButtonGroup btnGroup;
    private JRadioButton[] btnOptions;

    public QuestionScreen() {
        //lägg till sen
        setTitle("");
        setContentPane(pnlMain);
        setResizable(false);
        setPreferredSize(new Dimension(600, 460));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();

        setVisible(true);

        btnOptions = new JRadioButton[4];
        btnGroup = new ButtonGroup();

        for (int i = 0; i < btnOptions.length; i++) {
            int yCoordinate = 100+ i * 50;
            btnOptions[i] = new JRadioButton();
            btnOptions[i].setBounds(50, yCoordinate, 500, 100);
            pnlAnswers.add(btnOptions[i]);
            btnGroup.add(btnOptions[i]);
        }
    }
}
