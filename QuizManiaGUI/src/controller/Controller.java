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
    private FlashcardSetRepo flashcardSetRepo;
    private FlashcardRepo flashcardRepo;
    private FlashcardSetsFrame flashcardSetsFrame;
    private FlashcardsFrame flashcardsFrame;
    private FlashcardFrame flashcardFrame;
    private MainScreen mainScreen;
    private QuestionScreen questionScreen;
    private QuizzesScreen quizzesScreen;
    private QuizQuestions quizQuestions;
    private QuizRepo quizRepo;
    private QuestionRepo questionRepo;
    private SignupManager signupManager;
    private SignUpScreen signUpScreen;
    private FlashcardSet flashcardSet;
    private FlashcardPlayScreen playScreen;
    private Users currentUser;


    public Controller() {
        connectToDatabase();
        userManager = new UserManager(connection);
        signupManager = new SignupManager(connection, userManager);
        flashcardSetRepo = new FlashcardSetRepo(userManager, connection);
        flashcardSetRepo.subscribeListener(this);
        quizRepo = new QuizRepo(userManager, connection);
        quizRepo.subscribeListener(this);
        flashcardRepo = new FlashcardRepo(flashcardSet, connection);
        SwingUtilities.invokeLater(() -> {
            signinScreen = new SigninScreen(this);
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

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case FlashcardSetRepo.UPDATE_SETS_LIST: {
                handleUpdateSetsList((List<FlashcardSet>) evt.getNewValue());
                break;
            }
            case FlashcardRepo.UPDATE_FLASHCARD_LIST: {
                handleUpdateFlashcardList((List<Flashcard>) evt.getNewValue());
                flashcardsFrame.displayAnswer();
                break;
            }
            case QuizRepo.UPDATE_QUIZ_LIST: {
                handleUpdateQuizList((List<Quiz>) evt.getNewValue());
                break;
            }
            case QuestionRepo.UPDATE_QUESTION_LIST: {
                handleUpdateQuestionList((List<Questions>) evt.getNewValue());
                break;
            }

        }
    }

    public void handleUpdateSetsList(List<FlashcardSet> flashcardSets) {
        if (flashcardSetsFrame != null) {
            flashcardSetsFrame.displayFlashcardsSetsList(flashcardSets);
        }
    }


    public void handleUpdateQuizList(List<Quiz> quiz) {
        if (quizzesScreen != null) {
            quizzesScreen.displayQuizzesList(quiz);
        }
    }

    public void handleUpdateFlashcardList(List<Flashcard> flashcards) {
        flashcardsFrame.displayFlashcardList(flashcards);
    }

    public void handleUpdateQuestionList(List<Questions> questions) {
        quizQuestions.displayQuestionList(questions);
    }

    public void openSelectedSet(String username) {
        FlashcardSet flashcardSet = flashcardSetsFrame.getSelectedSet();
        if (flashcardSet != null) {
            flashcardsFrame = new FlashcardsFrame(this, username);
            flashcardRepo = new FlashcardRepo(flashcardSet, connection);
            List<Flashcard> flashcards = flashcardRepo.getFlashcards(flashcardSet.getId());
            handleUpdateFlashcardList(flashcards);
            flashcardSetsFrame.dispose();
            flashcardRepo.subscribeListener(this);
        }
    }

    public void openSelectedQuiz(String username) {
        Quiz quiz = quizzesScreen.getSelectedQuiz();
        if (quiz != null) {
            quizQuestions = new QuizQuestions(this, username);
            questionRepo = new QuestionRepo(quiz, connection);
            List<Questions> questions = questionRepo.getQuestions(quiz.getId());
            handleUpdateQuestionList(questions);
            quizzesScreen.dispose();
            questionRepo.subscribeListener(this);
        }
    }

    public void handleFlashcardModeSelected(String username) {
        flashcardSetsFrame = new FlashcardSetsFrame(this, username);
        flashcardSetRepo.setFlashcardSetsFrame(flashcardSetsFrame);
        List<FlashcardSet> flashcardSets = flashcardSetRepo.getFlashcardSets(username);
        handleUpdateSetsList(flashcardSets);
        mainScreen.dispose();

    }

    public void handleQuizModeSelected(String username) {
        quizzesScreen = new QuizzesScreen(this, username);
        quizRepo.setQuizzesScreen(quizzesScreen);
        mainScreen.dispose();
        List<Quiz> quiz = quizRepo.getQuiz(username);
        handleUpdateQuizList(quiz);
    }


    public void handleAddNewSet(String username) {
        String newSetTitle = JOptionPane.showInputDialog(null, "New set name:");
        flashcardSetRepo.addNewSet(newSetTitle, username);
    }

    public void handleDeleteSet() {
        FlashcardSet flashcardSet = flashcardSetsFrame.getSelectedSet();
        if (flashcardSet != null) {
            flashcardSetRepo.deleteSet(flashcardSet, flashcardSetsFrame.getUsername());
        }
    }

    public void handleDeleteFlashcard() {
        Flashcard flashcard = flashcardsFrame.getSelectedFlashcard();
        if (flashcard != null) {
            flashcardRepo.deleteFlashcard(flashcard);
        }
    }

    public void handleAddNewQuiz(String username) {
        String newQuizTitle = JOptionPane.showInputDialog(null, "New quiz name:");
        quizRepo.addNewQuiz(newQuizTitle, username);
    }

    public void handleAddNewFlashcard() {
        flashcardFrame = new FlashcardFrame(this);
        flashcardsFrame.setEnabled(false);
    }

    /*public void handleAddNewQuestion() {
        quizQuestions = new QuizQuestions(this); // screen where you write question and answer
        quizQuestions.setEnabled(false);
    }*/

    public void handleCancelFlashcardFrame() {
        flashcardFrame.dispose();
        flashcardsFrame.setEnabled(true);
    }

    /*public void handleCancelQuestionScreen() {
        flashcardFrame.dispose();
        flashcardsFrame.setEnabled(true);
    }*/

    public void handleSaveNewFlashcard() {
        String question = flashcardFrame.getQuestion();
        String answer = flashcardFrame.getAnswer();
        if ((!question.isEmpty()) || (!question.isBlank())) {
            flashcardRepo.addNewFlashcard(question, answer);
            flashcardFrame.dispose();
            flashcardsFrame.setEnabled(true);
        }
    }


    /*public void handleSaveNewQuestion() {
        String question = flashcardFrame.getQuestion();
        String answer = flashcardFrame.getAnswer();
        if ((!question.isEmpty()) || (!question.isBlank())) {
            flashcardRepo.addNewFlashcard(question, answer);
            flashcardFrame.dispose();
            flashcardsFrame.setEnabled(true);
        }
    }*/

    public void handleBackToMainScreen(String username) {
        mainScreen = new MainScreen(this, username);
        if (flashcardSetsFrame != null) {
            flashcardSetsFrame.dispose();
        }
        if (quizzesScreen != null) {
            quizzesScreen.dispose();
        }
    }


    public void handleBackToFlashcardSetsScreen(String username) {
        flashcardSetsFrame = new FlashcardSetsFrame(this, username);
        List<FlashcardSet> flashcardSets = flashcardSetRepo.getFlashcardSets(username);
        handleUpdateSetsList(flashcardSets);
        flashcardsFrame.dispose();
    }

    public void handleBackToQuizzesScreen(String username) {
        quizzesScreen = new QuizzesScreen(this, username);
        List<Quiz> quiz = quizRepo.getQuiz(username);
        handleUpdateQuizList(quiz);
        quizQuestions.dispose();
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

    public int getCurrentUserId(String username) {
        try {
            return userManager.getCurrentUserId(username);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public void openMainScreen(String username) {
        mainScreen = new MainScreen(this, username); //New instance of main screen
        //Retrieve flashcard sets for the user
        List<FlashcardSet> flashcardsSets = flashcardSetRepo.getFlashcardSets(username);
        //Set the retrived flashcard sets to the repository
        flashcardSetRepo.setFlashcardSetsFrame(flashcardSetsFrame);

        //List<FlashcardSet> flashcardSets = flashcardSetRepo.getFlashcardSets(username);
        //handleUpdateSetsList(flashcardSets);
    }

    public void handleLogout() {
        clearSessionData();
        closeAllWindows();
        showSigninScreen();
    }

    private void clearSessionData() {
        currentUser = null;
    }

    private void closeAllWindows() {
        if (mainScreen != null) {
            mainScreen.dispose();
        }

    }

    private void showSigninScreen() {
        signinScreen.setVisible(true);
    }


    //-------------------------------------

    public void onPlayButtonClick(String username) {
        FlashcardSet selectedSet = flashcardSetsFrame.getSelectedSet();
        if (selectedSet != null) {
            List<Flashcard> flashcards = flashcardRepo.getFlashcards(selectedSet.getId());
            flashcardSetsFrame.dispose();

            if (!flashcards.isEmpty()) {
                startPlayMode(flashcards, username);
            } else {
                JOptionPane.showMessageDialog(null, "This flashcard is empty.");

            }
        } else {
            JOptionPane.showMessageDialog(null, "Please select a flashcard set.");
        }
    }

    private void startPlayMode( List<Flashcard> flashcards, String username) {
        playScreen = new FlashcardPlayScreen(this, flashcards, username);
    }

    public void handleExitPlayMode(String username) {
        flashcardSetsFrame = new FlashcardSetsFrame(this, username);
        if (playScreen != null) {
            List<FlashcardSet> flashcardSets = flashcardSetRepo.getFlashcardSets(username);
            handleUpdateSetsList(flashcardSets);
        }
        playScreen.dispose();
    }

    public static void main(String[] args) {
        Controller controller = new Controller();
        //MainScreen mainScreen1 = new MainScreen(controller);
        //SigninScreen signinScreen = new SigninScreen();
        //SignUpScreen signUpScreen = new SignUpScreen();
        //FlashcardsFrame flashcardsFrame = new FlashcardsFrame();
        //FlashcardSetsFrame flashcardSetsFrame = new FlashcardSetsFrame(controller);
        //QuizzesScreen quizzesScreen = new QuizzesScreen();
        //QuizQuestions quizQuestions = new QuizQuestions(controller);
        //QuestionScreen questionScreen = new QuestionScreen();
    }
}
