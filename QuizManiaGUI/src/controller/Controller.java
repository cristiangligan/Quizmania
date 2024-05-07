package controller;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.*;
import java.util.List;

import model.*;
import view.*;

import javax.swing.*;

public class Controller implements PropertyChangeListener {
    private SigninScreen signinScreen;
    private Connection connection;
    private UserManager userManager;
    private FlashcardSetRepo flashcardSetRepo;
    private FlashcardRepo flashcardRepo;
    private FlashcardSetsFrame flashcardSetsFrame;
    private FlashcardsFrame flashcardsFrame;
    private FlashcardFrame flashcardFrame;
    private MainScreen mainScreen;
    private QuestionScreen questionScreen;

    public Controller() {
        connectToDatabase();
        userManager = new UserManager();
        flashcardSetRepo = new FlashcardSetRepo(userManager, connection);
        flashcardSetRepo.subscribeListener(this);
        SwingUtilities.invokeLater(() -> {
            //SignUpScreen signUpScreen = new SignUpScreen(this);
            mainScreen = new MainScreen(this);
        });
    }

    private PreparedStatement preparedStatement(String query, Connection connection) throws SQLException {
        return connection.prepareStatement(query);
    }
    public void createUser(String username, String password) throws SQLException {
        String inserQuery = "INSERT INTO public.users (username, password, created_at) VALUES (?, ?, ?)";

        try(PreparedStatement preparedStatement = preparedStatement(inserQuery, connection)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("User created successfully!");
            }

        } catch (SQLException e) {
            System.out.println("Unable to create user. Please try again.");
            e.printStackTrace();
        }
    }

    public void connectToDatabase() {
        String URL = "jdbc:postgresql://pgserver.mau.se:5432/ao7735";
        String user = "ao7735";
        String password = "ykvdv4um";

        try {
           connection = DriverManager.getConnection(URL, user, password);

            if (connection != null) {
                System.out.println("Connected successfully!");

            } else {
                System.out.println("Not successful!");
            }
        } catch (SQLException e) {
            System.out.println("Connection failed.");
            e.printStackTrace();
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case FlashcardSetRepo.UPDATE_SETS_LIST: {
                handleUpdateSetsList((List<FlashcardSet>) evt.getNewValue());
                break;
            }
            case FlashcardRepo.UPDATE_FLASHCARD_LIST: {
                handleUpdateFlashcardList((List<Flashcard>) evt.getNewValue());
            }

        }
    }

    public void handleUpdateSetsList(List<FlashcardSet> flashcardSets) {
        flashcardSetsFrame.displayFlashcardsSetsList(flashcardSets);
    }

    public void handleUpdateFlashcardList(List<Flashcard> flashcards) {
        flashcardsFrame.displayFlashcardList(flashcards);
    }

    public void openSelectedSet() {
        FlashcardSet flashcardSet = flashcardSetsFrame.getSelectedSet();
        if (flashcardSet != null) {
            flashcardsFrame = new FlashcardsFrame(this);
            flashcardRepo = new FlashcardRepo(flashcardSet, connection);
            List<Flashcard> flashcards = flashcardRepo.getFlashcards(flashcardSet.getId());
            handleUpdateFlashcardList(flashcards);
            flashcardSetsFrame.dispose();
            flashcardRepo.subscribeListener(this);
        }
    }

    public void handleFlashcardModeSelected() {
        flashcardSetsFrame = new FlashcardSetsFrame(this);
        mainScreen.dispose();
        List<FlashcardSet> flashcardSets = flashcardSetRepo.getFlashcardSets();
        handleUpdateSetsList(flashcardSets);
    }

    public void handleAddNewSet() {
        String newSetTitle = JOptionPane.showInputDialog(null, "New set name:");
        flashcardSetRepo.addNewSet(newSetTitle);
    }

    public void handleAddNewFlashcard() {
        flashcardFrame = new FlashcardFrame(this);
        flashcardsFrame.setEnabled(false);
    }

    public void handleCancelFlashcardFrame() {
        flashcardFrame.dispose();
        flashcardsFrame.setEnabled(true);
    }

    public void handleSaveNewFlashcard() {
        String question = flashcardFrame.getQuestion();
        String answer = flashcardFrame.getAnswer();
        if ((!question.isEmpty()) || (!question.isBlank())) {
            flashcardRepo.addNewFlashcard(question, answer);
            flashcardFrame.dispose();
            flashcardsFrame.setEnabled(true);
        }
    }

    public void handleBackToMainScreen() {
        mainScreen = new MainScreen(this);
        flashcardSetsFrame.dispose();
    }

    public void handleBackToFlashcardSetsScreen() {
        flashcardSetsFrame = new FlashcardSetsFrame(this);
        List<FlashcardSet> flashcardSets = flashcardSetRepo.getFlashcardSets();
        handleUpdateSetsList(flashcardSets);
        flashcardsFrame.dispose();
    }

    public static void main(String[] args) {
       // Controller controller = new Controller();
        //SigninScreen signinScreen = new SigninScreen();
        //SignUpScreen signUpScreen = new SignUpScreen();
        //FlashcardsFrame flashcardsFrame = new FlashcardsFrame();
        //FlashcardSetsFrame flashcardSetsFrame = new FlashcardSetsFrame(controller);
        // QuizzesScreen quizzesScreen = new QuizzesScreen();
        // QuizQuestions quizQuestions = new QuizQuestions();
        QuestionScreen questionScreen = new QuestionScreen();
    }
}
