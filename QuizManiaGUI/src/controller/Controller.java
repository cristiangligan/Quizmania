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


    public Controller() {
        connectToDatabase();
        userManager = new UserManager(connection);
        flashcardSetRepo = new FlashcardSetRepo(userManager, connection);
        flashcardSetRepo.subscribeListener(this);
        quizRepo = new QuizRepo(userManager, connection);
        quizRepo.subscribeListener(this);
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

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case FlashcardSetRepo.UPDATE_SETS_LIST: {
                handleUpdateSetsList((List<FlashcardSet>) evt.getNewValue());
                break;
            }
            case FlashcardRepo.UPDATE_FLASHCARD_LIST: {
                handleUpdateFlashcardList((List<Flashcard>) evt.getNewValue());
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
        flashcardSetsFrame.displayFlashcardsSetsList(flashcardSets);
    }

    public void handleUpdateQuizList(List<Quiz> quiz) {
        quizzesScreen.displayQuizzesList(quiz);
    }

    public void handleUpdateFlashcardList(List<Flashcard> flashcards) {
        flashcardsFrame.displayFlashcardList(flashcards);
    }

    public void handleUpdateQuestionList(List<Questions> questions) {
        quizQuestions.displayQuestionList(questions);
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

    public void openSelectedQuiz() {
        Quiz quiz = QuizzesScreen.getSelectedQuiz();
        if (quiz != null) {
            quizQuestions = new QuizQuestions(this);
            questionRepo = new QuestionRepo(quiz, connection);
            List<Questions> questions = questionRepo.getQuestions(quiz.getId());
            handleUpdateQuestionList(questions);
            quizzesScreen.dispose();
            questionRepo.subscribeListener(this);
        }
    }

    public void handleFlashcardModeSelected() {
        flashcardSetsFrame = new FlashcardSetsFrame(this);
        mainScreen.dispose();
        List<FlashcardSet> flashcardSets = flashcardSetRepo.getFlashcardSets();
        handleUpdateSetsList(flashcardSets);
    }

    public void handleQuizModeSelected() {
        quizzesScreen = new QuizzesScreen(this);
        mainScreen.dispose();
        List<Quiz> quiz = quizRepo.getQuiz();
        handleUpdateQuizList(quiz);
    }

    public void handleAddNewSet() {
        String newSetTitle = JOptionPane.showInputDialog(null, "New set name:");
        flashcardSetRepo.addNewSet(newSetTitle);
    }

    public void handleAddNewQuiz() {
        String newQuizTitle = JOptionPane.showInputDialog(null, "New quiz name:");
        quizRepo.addNewQuiz(newQuizTitle);
    }

    public void handleAddNewFlashcard() {
        flashcardFrame = new FlashcardFrame(this);
        flashcardsFrame.setEnabled(false);
    }

    /*public void handleAddNewQuestion() {
        quizQuestions = new QuizQuestions(this);
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

    public void handleBackToMainScreen() {
        mainScreen = new MainScreen(this);
        flashcardSetsFrame.dispose();
        quizzesScreen.dispose();

    }

    public void handleBackToFlashcardSetsScreen() {
        flashcardSetsFrame = new FlashcardSetsFrame(this);
        List<FlashcardSet> flashcardSets = flashcardSetRepo.getFlashcardSets();
        handleUpdateSetsList(flashcardSets);
        flashcardsFrame.dispose();
    }

    public void handleBackToQuizzesScreen() { // add button to screen
        quizzesScreen = new QuizzesScreen(this);
        List<Quiz> quiz = quizRepo.getQuiz();
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

    public void openMainScreen(String username) {
        mainScreen = new MainScreen(this);
        List<FlashcardSet> flashcardsSets = flashcardSetRepo.getFlashcardSets();
        //mainScreen1.setFlashcardsSets(flashcardsSets); -- ska fixas
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
