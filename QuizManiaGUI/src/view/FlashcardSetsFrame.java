package view;

import controller.Controller;
import model.FlashcardSet;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * FlashcardSetsFrame is the UI frame for managing and interacting with flashcard sets.
 *
 * <p>This class provides a graphical interface to view, add, delete, edit, and play flashcards sets.</p>
 *
 * @author Kaye Moran
 * @author Jessica Puente
 * @author Crsitian Gligan
 */
public class FlashcardSetsFrame extends JFrame {
    private Controller controller;
    private JPanel pnlMain = new JPanel();
    private JButton btnBack = new JButton();
    private JButton btnAddNewSet = new JButton();
    private JButton btnOpen = new JButton();
    private JButton btnPlay = new JButton();
    private JButton btnDeleteSet = new JButton();
    private JButton btnEditSetName = new JButton();

    private JList setsList = new JList();

    /**
     * Constructs a new FlashcardSetsFrame
     *
     * @param controller The controller object that handles the logic for user interactions.
     */
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

        btnDeleteSet.setText("Delete");
        pnlMain.add(btnDeleteSet);
        btnDeleteSet.addActionListener(e -> onDeleteSetBtnClick());
        springLayout.putConstraint(SpringLayout.WEST, btnDeleteSet, 20, SpringLayout.EAST, btnBack);
        springLayout.putConstraint(SpringLayout.NORTH, btnDeleteSet, 0, SpringLayout.NORTH, btnBack);

        btnEditSetName.setText("Edit");
        pnlMain.add(btnEditSetName);
        btnEditSetName.addActionListener(e -> onEditSetNameBtnClick());
        springLayout.putConstraint(SpringLayout.WEST, btnEditSetName, 20, SpringLayout.EAST, btnDeleteSet);
        springLayout.putConstraint(SpringLayout.NORTH, btnEditSetName, 0, SpringLayout.NORTH, btnDeleteSet);
        springLayout.putConstraint(SpringLayout.EAST, btnEditSetName, -20, SpringLayout.WEST, btnAddNewSet);


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

        btnPlay.setText("Play");
        pnlMain.add(btnPlay);
        springLayout.putConstraint(SpringLayout.WEST, btnPlay, 0, SpringLayout.WEST, setsList);
        springLayout.putConstraint(SpringLayout.SOUTH, btnPlay, -20, SpringLayout.SOUTH, pnlMain);
        btnPlay.addActionListener(e -> onBtnPlayClick());

        this.pack();
        this.setVisible(true);
        this.setSize(new Dimension(400, 400));
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    /**
     * Handles the click event for Edit button, initiating the editing of a flashcard set's name.
     */
    private void onEditSetNameBtnClick() {
        controller.handleEditFlashcardSet();
    }

    /**
     * Handles the click event for the Delete button, initiating the deletion of a flashcard set.
     */
    private void onDeleteSetBtnClick() {
        controller.handleDeleteSet();
    }

    /**
     * Handles the click for the Add New Set button, initiating the addition of a flashcard set.
     */
    private void onBtnAddNewSetClick() {
        controller.handleAddNewSet();
    }

    /**
     * Handles the click event for the Open button, opening the selected flashcard set.
     */
    private void onBackBtnClick() {
        controller.handleBackToMainScreen();
    }

    /**
     * Handles the click event for the Open button, opening the selected flashcard set.
     */
    private void onBtnOpenClick() {
        controller.openSelectedSet();
    }

    /**
     * Handles the click event for the Play button, starting the play mode for the selected flashcard set.
     */
    private void onBtnPlayClick() {
        controller.onPlayButtonClick();
    }

    /**
     * Retrieves the currently selected flashcard set.
     *
     * @return The selected FlashcardSet object.
     */
    public FlashcardSet getSelectedSet() {
        FlashcardSet flashcardSet = (FlashcardSet) setsList.getSelectedValue();
        return flashcardSet;
    }

    /**
     * Returns the JList component for displaying flashcard sets.
     *
     * @return the JList component displaying flashcard sets.
     */
    public JList getSetsList() { //Returns JList component for displaying flashcard sets
        return setsList;
    }

    /**
     * Displays the provided lisy of flashcard sets in the JList component.
     *
     * @param flashcardSets The list of FlashcardSets object to display.
     */
    public void displayFlashcardsSetsList(List<FlashcardSet> flashcardSets) {
        setsList.setListData(flashcardSets.toArray());
        if(!flashcardSets.isEmpty()) {
            setsList.setSelectedIndex(0);
        }
    }
}
