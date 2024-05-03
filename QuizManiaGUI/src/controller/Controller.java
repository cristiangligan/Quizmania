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
    private FlashCardsSetsRepo flashCardsSetsRepo;
    private FlashcardSetsFrame flashcardSetsFrame;
    private FlashcardsFrame flashcardsFrame;
    private MainScreen mainScreen;

    public Controller() {
        connectToDatabase();
        userManager = new UserManager();
        flashCardsSetsRepo = new FlashCardsSetsRepo(userManager, connection);
        flashCardsSetsRepo.subscribeListener(this);
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

    public void addNewSet() {
        String newSetTitle = JOptionPane.showInputDialog(null, "New set name:");
        flashCardsSetsRepo.addNewSet(newSetTitle);
    }

    public void handleUpdateSetsList(List<FlashcardsSet> flashcardsSets) {
        flashcardSetsFrame.displayFlashcardsSetsList(flashcardsSets);
    }

    public void handleUpdateFlashcardList(List<Flashcard> flashcards) {
        flashcardsFrame.displayFlashcardList(flashcards);
    }



    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case FlashCardsSetsRepo.UPDATE_SETS_LIST: {
                handleUpdateSetsList((List<FlashcardsSet>) evt.getNewValue());
            }

        }
    }

    public void handleFlashcardModeSelected() {
        flashcardSetsFrame = new FlashcardSetsFrame(this);
        mainScreen.dispose();
        List<FlashcardsSet> flashcardsSets = flashCardsSetsRepo.getFlashcardSets();
        handleUpdateSetsList(flashcardsSets);
    }

    public void openSelectedSet() {
        flashcardsFrame = new FlashcardsFrame();
        int selectedFlashcardSetId = flashcardSetsFrame.getSelectedSetId();
        List<Flashcard> flashcards = flashCardsSetsRepo.getFlashcards(selectedFlashcardSetId);
        handleUpdateFlashcardList(flashcards);
        flashcardSetsFrame.dispose();
    }

    public static void main(String[] args) {
        Controller controller = new Controller();
        //SigninScreen signinScreen = new SigninScreen();
        //SignUpScreen signUpScreen = new SignUpScreen();
        //FlashcardsFrame flashcardsFrame = new FlashcardsFrame();
        //FlashcardSetsFrame flashcardSetsFrame = new FlashcardSetsFrame(controller);
        // QuizzesScreen quizzesScreen = new QuizzesScreen();
        // QuizQuestions quizQuestions = new QuizQuestions();
    }
}
