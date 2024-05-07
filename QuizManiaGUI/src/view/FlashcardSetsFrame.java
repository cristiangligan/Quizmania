package view;

import controller.Controller;
import model.FlashcardSet;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class FlashcardSetsFrame extends JFrame {
    private Controller controller;
    private JPanel pnlMain = new JPanel();
    private JButton btnBack = new JButton();
    private JButton btnAddNewSet = new JButton();
    private JLabel lblTitle = new JLabel();
    private JButton btnOpen = new JButton();

    private JList setsList = new JList();
    public FlashcardSetsFrame(Controller controller) {
        this.controller = controller;
        this.setTitle("Quizmania");
        SpringLayout springLayout = new SpringLayout();
        this.setContentPane(pnlMain);
        pnlMain.setLayout(springLayout);

        btnBack.setText("Back");
        pnlMain.add(btnBack);
        btnBack.addActionListener(e -> onBackBtnClick());
        springLayout.putConstraint(SpringLayout.WEST, btnBack, 20, SpringLayout.WEST, pnlMain);
        springLayout.putConstraint(SpringLayout.NORTH, btnBack, 20, SpringLayout.NORTH, pnlMain);

        lblTitle.setText("Flashcard sets");
        pnlMain.add(lblTitle);
        springLayout.putConstraint(SpringLayout.WEST, lblTitle, 20, SpringLayout.EAST, btnBack);
        springLayout.putConstraint(SpringLayout.EAST, lblTitle, -20, SpringLayout.WEST, btnAddNewSet);
        springLayout.putConstraint(SpringLayout.NORTH, lblTitle, 0, SpringLayout.NORTH, btnBack);
        springLayout.putConstraint(SpringLayout.SOUTH, lblTitle, 0, SpringLayout.SOUTH, btnBack);

        btnAddNewSet.setText("+");
        pnlMain.add(btnAddNewSet);
        springLayout.putConstraint(SpringLayout.EAST, btnAddNewSet, -20, SpringLayout.EAST, pnlMain);
        springLayout.putConstraint(SpringLayout.NORTH, btnAddNewSet, 0, SpringLayout.NORTH, btnBack);
        btnAddNewSet.addActionListener(e -> onBtnAddNewSetClick());

        pnlMain.add(setsList);
        springLayout.putConstraint(SpringLayout.WEST, setsList, 20, SpringLayout.WEST, pnlMain);
        springLayout.putConstraint(SpringLayout.EAST, setsList, -20, SpringLayout.EAST, pnlMain);
        springLayout.putConstraint(SpringLayout.NORTH, setsList, 20, SpringLayout.SOUTH, btnBack);
        springLayout.putConstraint(SpringLayout.SOUTH, setsList, -60, SpringLayout.SOUTH, pnlMain);

        btnOpen.setText("Open");
        pnlMain.add(btnOpen);
        springLayout.putConstraint(SpringLayout.EAST, btnOpen, 0, SpringLayout.EAST, setsList);
        springLayout.putConstraint(SpringLayout.SOUTH, btnOpen, -20, SpringLayout.SOUTH, pnlMain);
        btnOpen.addActionListener(e -> onBtnOpenClick());

        this.pack();
        this.setVisible(true);
        this.setSize(new Dimension(400, 400));
        this.setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void onBtnAddNewSetClick() {
        controller.handleAddNewSet();
    }
    private void onBackBtnClick() {
        controller.handleBackToMainScreen();
    }

    private void onBtnOpenClick() {
        controller.openSelectedSet();
    }

    public FlashcardSet getSelectedSet() {
        FlashcardSet flashcardSet = (FlashcardSet) setsList.getSelectedValue();
        return flashcardSet;
    }

    public void displayFlashcardsSetsList(List<FlashcardSet> flashcardSets) {
        setsList.setListData(flashcardSets.toArray());
        if(!flashcardSets.isEmpty()) {
            setsList.setSelectedIndex(0);
        }
    }
}
