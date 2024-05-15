package view;

import controller.Controller;
import model.Flashcard;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.List;

public class FlashcardsFrame extends JFrame {
    private Controller controller;
    private JPanel pnlMain = new JPanel();
    private JPanel pnlCenter = new JPanel();
    private JPanel pnlRight = new JPanel();

    private JButton btnBack = new JButton();
    private JButton btnAddNewFlashCard = new JButton();
    private JLabel lblTitle = new JLabel();
    private JList flashcardList = new JList();
    private JTextArea answerTextArea = new JTextArea();

    public FlashcardsFrame(Controller controller, String username) {
        this.controller = controller;
        this.setTitle("Quizmania");
        this.setContentPane(pnlMain);
        pnlMain.setLayout(new BorderLayout());


        //CENTER_PANEL-----START
        SpringLayout springLayoutCenterPanel = new SpringLayout();

        pnlMain.add(pnlCenter, BorderLayout.CENTER);
        pnlCenter.setLayout(springLayoutCenterPanel);

        btnBack.setText("Back");
        pnlCenter.add(btnBack);
        btnBack.addActionListener(e -> onBackBtnClick(username));
        springLayoutCenterPanel.putConstraint(SpringLayout.WEST, btnBack, 20, SpringLayout.WEST, pnlCenter);
        springLayoutCenterPanel.putConstraint(SpringLayout.NORTH, btnBack, 20, SpringLayout.NORTH, pnlCenter);

        lblTitle.setText("Flashcards");
        pnlCenter.add(lblTitle);
        springLayoutCenterPanel.putConstraint(SpringLayout.WEST, lblTitle, 20, SpringLayout.EAST, btnBack);
        springLayoutCenterPanel.putConstraint(SpringLayout.EAST, lblTitle, -20, SpringLayout.WEST, btnAddNewFlashCard);
        springLayoutCenterPanel.putConstraint(SpringLayout.NORTH, lblTitle, 0, SpringLayout.NORTH, btnBack);
        springLayoutCenterPanel.putConstraint(SpringLayout.SOUTH, lblTitle, 0, SpringLayout.SOUTH, btnBack);

        btnAddNewFlashCard.setText("+");
        pnlCenter.add(btnAddNewFlashCard);
        btnAddNewFlashCard.addActionListener(e -> onAddNewFlashcardBtnClick());
        springLayoutCenterPanel.putConstraint(SpringLayout.EAST, btnAddNewFlashCard, -20, SpringLayout.EAST, pnlCenter);
        springLayoutCenterPanel.putConstraint(SpringLayout.NORTH, btnAddNewFlashCard, 0, SpringLayout.NORTH, btnBack);

        flashcardList.setBorder(new TitledBorder("Questions"));
        pnlCenter.add(flashcardList);
        springLayoutCenterPanel.putConstraint(SpringLayout.WEST, flashcardList, 20, SpringLayout.WEST, pnlCenter);
        springLayoutCenterPanel.putConstraint(SpringLayout.EAST, flashcardList, -20, SpringLayout.EAST, pnlCenter);
        springLayoutCenterPanel.putConstraint(SpringLayout.NORTH, flashcardList, 20, SpringLayout.SOUTH, btnBack);
        springLayoutCenterPanel.putConstraint(SpringLayout.SOUTH, flashcardList, -10, SpringLayout.SOUTH, pnlCenter);
        //CENTER_PANEL-----END



        //RIGHT_PANEL-----START
        SpringLayout springLayoutRightPanel = new SpringLayout();

        pnlMain.add(pnlRight, BorderLayout.LINE_END);
        pnlRight.setLayout(springLayoutRightPanel);
        pnlRight.setPreferredSize(new Dimension(200, 0));

        answerTextArea.setEditable(false);
        answerTextArea.setBorder(new TitledBorder("Answer"));
        answerTextArea.setWrapStyleWord(true);
        answerTextArea.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(answerTextArea);
        scrollPane.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );
        pnlRight.add(scrollPane);
        springLayoutRightPanel.putConstraint(SpringLayout.WEST, scrollPane, 10, SpringLayout.WEST, pnlRight);
        springLayoutRightPanel.putConstraint(SpringLayout.EAST, scrollPane, -10, SpringLayout.EAST, pnlRight);
        springLayoutRightPanel.putConstraint(SpringLayout.NORTH, scrollPane, 10, SpringLayout.NORTH, pnlRight);
        springLayoutRightPanel.putConstraint(SpringLayout.SOUTH, scrollPane, -10, SpringLayout.SOUTH, pnlRight);
        //RIGHT_PANEL-----END


        ListSelectionModel listSelectionModel = flashcardList.getSelectionModel();
        listSelectionModel.addListSelectionListener(new SharedListSelectionHandler());
        this.pack();
        this.setVisible(true);
        this.setSize(new Dimension(600, 400));
        this.setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void onAddNewFlashcardBtnClick() {
        controller.handleAddNewFlashcard();
    }

    private void onBackBtnClick(String username) {
        controller.handleBackToFlashcardSetsScreen(username);
    }

    public void displayFlashcardList(List<Flashcard> flashcards) {
        flashcardList.setListData(flashcards.toArray());
        if(!flashcards.isEmpty()) {
            flashcardList.setSelectedIndex(0);
            displayAnswer();
        }
    }

    public void displayAnswer() {
        Flashcard flashcard = (Flashcard) flashcardList.getSelectedValue();
        answerTextArea.setText(flashcard.getAnswer());
    }

    private class SharedListSelectionHandler implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting() && !flashcardList.isSelectionEmpty()) {
                displayAnswer();
            }
        }
    }
}
