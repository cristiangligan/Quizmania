package view;

import javax.swing.*;
import java.awt.*;

public class SignSelectorFrame extends JFrame {
    private JPanel panel = new JPanel();
    private JButton signInButton = new JButton();
    private JButton signUpButton = new JButton();

    public SignSelectorFrame() {
        setPreferredSize(new Dimension(300, 400));
        setTitle("Quizmania");

        SpringLayout springLayout = new SpringLayout();
        panel.setLayout(springLayout);

        signInButton.setText("Sign In");
        signInButton.setFont(new Font(Font.SERIF, Font.BOLD, 36));
        panel.add(signInButton);
        springLayout.putConstraint(SpringLayout.NORTH, signInButton, 20, SpringLayout.NORTH, panel);
        springLayout.putConstraint(SpringLayout.WEST, signInButton, 20, SpringLayout.WEST, panel);
        springLayout.putConstraint(SpringLayout.EAST, signInButton, -20, SpringLayout.EAST, panel);
        springLayout.putConstraint(SpringLayout.SOUTH, signInButton, -200, SpringLayout.SOUTH, panel);

        signUpButton.setText("Sign Up");
        signUpButton.setFont(new Font(Font.SERIF, Font.BOLD, 36));
        panel.add(signUpButton);
        springLayout.putConstraint(SpringLayout.SOUTH, signUpButton, -20, SpringLayout.SOUTH, panel);
        springLayout.putConstraint(SpringLayout.WEST, signUpButton, 20, SpringLayout.WEST, panel);
        springLayout.putConstraint(SpringLayout.EAST, signUpButton, -20, SpringLayout.EAST, panel);
        springLayout.putConstraint(SpringLayout.NORTH, signUpButton, 20, SpringLayout.SOUTH, signInButton);

        setContentPane(panel);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();

        setVisible(true);
    }
}
