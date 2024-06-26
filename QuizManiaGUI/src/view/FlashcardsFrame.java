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
    private JButton btnDeleteFlashcard = new JButton();
    private JButton btnEditFlashcard = new JButton();

    private JButton btnAddNewFlashCard = new JButton();
    private JLabel lblTitle = new JLabel();
    private JList flashcardList = new JList();
    private JTextArea answerTextArea = new JTextArea();


    public FlashcardsFrame(Controller controller) {
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
        btnBack.addActionListener(e -> onBackBtnClick());
        springLayoutCenterPanel.putConstraint(SpringLayout.WEST, btnBack, 20, SpringLayout.WEST, pnlCenter);
        springLayoutCenterPanel.putConstraint(SpringLayout.NORTH, btnBack, 20, SpringLayout.NORTH, pnlCenter);

        btnDeleteFlashcard.setText("Delete");
        pnlCenter.add(btnDeleteFlashcard);
        btnDeleteFlashcard.addActionListener(e -> onDeleteFlashcardBtnClick());
        springLayoutCenterPanel.putConstraint(SpringLayout.WEST, btnDeleteFlashcard, 20, SpringLayout.EAST, btnBack);
        springLayoutCenterPanel.putConstraint(SpringLayout.NORTH, btnDeleteFlashcard, 0, SpringLayout.NORTH, btnBack);

        /*lblTitle.setText("Flashcards");
        pnlCenter.add(lblTitle);
        springLayoutCenterPanel.putConstraint(SpringLayout.WEST, lblTitle, 20, SpringLayout.EAST, btnBack);
        springLayoutCenterPanel.putConstraint(SpringLayout.EAST, lblTitle, -20, SpringLayout.WEST, btnAddNewFlashCard);
        springLayoutCenterPanel.putConstraint(SpringLayout.NORTH, lblTitle, 0, SpringLayout.NORTH, btnBack);
        springLayoutCenterPanel.putConstraint(SpringLayout.SOUTH, lblTitle, 0, SpringLayout.SOUTH, btnBack);*/

        btnEditFlashcard.setText("Edit");
        pnlCenter.add(btnEditFlashcard);
        btnEditFlashcard.addActionListener(e -> onEditFlashcardBtnClick());
        springLayoutCenterPanel.putConstraint(SpringLayout.WEST, btnEditFlashcard, 20, SpringLayout.EAST, btnDeleteFlashcard);
        springLayoutCenterPanel.putConstraint(SpringLayout.NORTH, btnEditFlashcard, 0, SpringLayout.NORTH, btnDeleteFlashcard);
        springLayoutCenterPanel.putConstraint(SpringLayout.EAST, btnEditFlashcard, -20, SpringLayout.WEST, btnAddNewFlashCard);

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
        scrollPane.setVerticalScrollBarPolicy (ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

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
        this.setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void onEditFlashcardBtnClick() {
        controller.handleEditFlashcard();
    }

    private void onDeleteFlashcardBtnClick() {
        controller.handleDeleteFlashcard();
    }

    private void onAddNewFlashcardBtnClick() {
        controller.handleAddNewFlashcard();
    }

    private void onBackBtnClick() {
        controller.handleBackToFlashcardSetsScreen();
    }

    public void displayFlashcardList(List<Flashcard> flashcards) {
        flashcardList.setListData(flashcards.toArray());
        if(!flashcards.isEmpty()) {
            flashcardList.setSelectedIndex(0);
            displayAnswer();
            answerTextArea.setCaretPosition(0);
        }
    }

    public void displayAnswer() {
        Flashcard flashcard = (Flashcard) flashcardList.getSelectedValue();
        if (flashcard != null) {
            answerTextArea.setText(flashcard.getAnswer());
        }
        else {
            answerTextArea.setText("");
        }
    }

    public Flashcard getSelectedFlashcard() {
        Flashcard flashcard = (Flashcard) flashcardList.getSelectedValue();
        return flashcard;
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
