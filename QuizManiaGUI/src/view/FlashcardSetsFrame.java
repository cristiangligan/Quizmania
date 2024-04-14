package view;

import javax.swing.*;
import java.awt.*;

public class FlashcardSetsFrame extends JFrame {
    private JPanel mainPanel = new JPanel();
    private JButton backButton = new JButton();
    private JButton addNewSetButton = new JButton();
    private JLabel titleLabel = new JLabel();

    private JList setsList = new JList();
    public FlashcardSetsFrame() {
        this.setTitle("Quizmania");
        SpringLayout springLayout = new SpringLayout();
        this.setContentPane(mainPanel);
        mainPanel.setLayout(springLayout);

        backButton.setText("Back");
        mainPanel.add(backButton);
        springLayout.putConstraint(SpringLayout.WEST, backButton, 20, SpringLayout.WEST, mainPanel);
        springLayout.putConstraint(SpringLayout.NORTH, backButton, 20, SpringLayout.NORTH, mainPanel);

        titleLabel.setText("Flashcard sets");
        mainPanel.add(titleLabel);
        springLayout.putConstraint(SpringLayout.WEST, titleLabel, 20, SpringLayout.EAST, backButton);
        springLayout.putConstraint(SpringLayout.EAST, titleLabel, -20, SpringLayout.WEST, addNewSetButton);
        springLayout.putConstraint(SpringLayout.NORTH, titleLabel, 0, SpringLayout.NORTH, backButton);
        springLayout.putConstraint(SpringLayout.SOUTH, titleLabel, 0, SpringLayout.SOUTH, backButton);

        addNewSetButton.setText("+");
        mainPanel.add(addNewSetButton);
        springLayout.putConstraint(SpringLayout.EAST, addNewSetButton, -20, SpringLayout.EAST, mainPanel);
        springLayout.putConstraint(SpringLayout.NORTH, addNewSetButton, 0, SpringLayout.NORTH, backButton);

        mainPanel.add(setsList);
        springLayout.putConstraint(SpringLayout.WEST, setsList, 20, SpringLayout.WEST, mainPanel);
        springLayout.putConstraint(SpringLayout.EAST, setsList, -20, SpringLayout.EAST, mainPanel);
        springLayout.putConstraint(SpringLayout.NORTH, setsList, 20, SpringLayout.SOUTH, backButton);
        springLayout.putConstraint(SpringLayout.SOUTH, setsList, -20, SpringLayout.SOUTH, mainPanel);

        this.pack();
        this.setVisible(true);
        this.setSize(new Dimension(400, 400));
        this.setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
