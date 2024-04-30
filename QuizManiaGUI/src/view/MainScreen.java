package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainScreen extends JDialog {
    private JPanel pnlMainScreen;
    private JButton btnFlashCard;
    private JButton btnQuiz;
    private JLabel lblChoose;
    private JButton btnLogOut;


    public MainScreen() {
        setTitle("Home");
        setContentPane(pnlMainScreen);
        setResizable(false);
        setPreferredSize(new Dimension(600, 460));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();

        btnFlashCard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FlashcardsFrame flashcardsFrame = new FlashcardsFrame();
                flashcardsFrame.setVisible(true);

            }
        });

        btnQuiz.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                QuizzesScreen quizzesScreen = new QuizzesScreen();
                quizzesScreen.setVisible(true);
            }
        });

    }




}
