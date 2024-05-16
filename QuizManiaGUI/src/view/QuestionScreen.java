package view;

import javax.swing.*;
import java.awt.*;

public class QuestionScreen extends JDialog{
    private JButton btnNext;
    private JLabel lblQuiz;
    private JPanel pnlMain;
    private JPanel pnlAnswers;
    private JButton BtnC;
    private JButton BtnB;
    private JButton BtnD;
    private JButton BtnA;
    private JTextField txtB;
    private JTextField txtD;
    private JTextField txtC;
    private JTextField txtQuestion;
    private JTextField txtA;
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
