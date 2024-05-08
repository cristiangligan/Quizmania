package controller;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
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
    private MainScreen mainScreen;
    private SignupManager signupManager;
    private SignUpScreen signUpScreen;


    public Controller() {
        connectToDatabase();
        userManager = new UserManager(connection);
        signupManager = new SignupManager(connection, userManager);
        flashCardsSetsRepo = new FlashCardsSetsRepo(userManager, connection);
        flashCardsSetsRepo.subscribeListener(this);
        SwingUtilities.invokeLater(() -> {
            signUpScreen = new SignUpScreen(this, signupManager);
        });
    }

    private PreparedStatement preparedStatement(String query, Connection connection) throws SQLException {
        return connection.prepareStatement(query);
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
    }

    public void openSignUpScreen() {
        signUpScreen = new SignUpScreen(this, signupManager);
        signUpScreen.setVisible(true);
        signinScreen.dispose();
    }

    public void openSignInScreen() {
        signinScreen = new SigninScreen(this);
        signinScreen.setVisible(true);
        signUpScreen.dispose();
    }

    public UserManager getUserManager() {
        return userManager;
    }

    public void openMainScreen(String username) {
        mainScreen = new MainScreen(this);
        List<FlashcardsSet> flashcardsSets = flashCardsSetsRepo.getFlashcardsSets();
        //mainScreen1.setFlashcardsSets(flashcardsSets); -- ska fixas
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
