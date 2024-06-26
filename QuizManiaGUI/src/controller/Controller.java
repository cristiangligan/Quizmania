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
    private QuizzesScreen quizzesScreen;
    private QuizQuestionsScreen quizQuestionsScreen;
    private QuizRepo quizRepo;
    private Quiz quiz;
    private QuestionRepo questionRepo;
    private QuizQuestionAnswerFrame quizQuestionAnswerFrame;
    private SignupManager signupManager;
    private SignUpScreen signUpScreen;
    private FlashcardSet flashcardSet;
    private FlashcardPlayScreen playScreen;



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
                handleUpdateQuestionList((List<Question>) evt.getNewValue());
                break;
            }

        }
    }

    public void handleSignIn(String username, String password) {
        try {
            //Verify the username and password with database
            if (userManager.verifyPassword(username, password)) {
                User user = userManager.getMatchingUserFromDatabase(username, password);
                userManager.setCurrentUser(user);
                openMainScreen();
                //Open main screen and dispose sign in screen
                signinScreen.dispose();
            } else {
                //If username or password is invalid
                JOptionPane.showMessageDialog(null, "Incorrect username or password.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Log in failed. Please try again.");
            e.printStackTrace();
        }
    }

    public void handleSignUp(String username, String password) {
        //Attempt to sign up a new user
        if (signupManager.signUp(username, password)) {
            //Display a message if sign up is successful
            User user = userManager.getMatchingUserFromDatabase(username, password);
            userManager.setCurrentUser(user);
            JOptionPane.showMessageDialog(null, "Account created successfully!");
            openMainScreen();
            signUpScreen.dispose();
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

    public void handleUpdateQuestionList(List<Question> questions) {
        quizQuestionsScreen.displayQuestionList(questions);
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
        Quiz quiz = quizzesScreen.getSelectedQuiz();
        if (quiz != null) {
            quizQuestionsScreen = new QuizQuestionsScreen(this);
            quizRepo.setCurrentQuiz(quiz);
            questionRepo = new QuestionRepo(quiz, connection);
            List<Question> questions = questionRepo.getQuestions(quiz.getId());
            handleUpdateQuestionList(questions);
            quizzesScreen.dispose();
            questionRepo.subscribeListener(this);
        }
    }

    public void handleFlashcardModeSelected() {
        flashcardSetsFrame = new FlashcardSetsFrame(this);
        flashcardSetRepo.setFlashcardSetsFrame(flashcardSetsFrame);
        List<FlashcardSet> flashcardSets = flashcardSetRepo.getFlashcardSets(userManager.getCurrentUser());
        handleUpdateSetsList(flashcardSets);
        mainScreen.dispose();

    }

    public void handleQuizModeSelected() {
        quizzesScreen = new QuizzesScreen(this);
        mainScreen.dispose();
        List<Quiz> quiz = quizRepo.getQuiz(userManager.getCurrentUser());
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

    public void handleEditFlashcardSet() {
        FlashcardSet flashcardSet = flashcardSetsFrame.getSelectedSet();
        if (flashcardSet != null) {
            String currentSetTitle = flashcardSet.getTitle();
            String newSetTitle = JOptionPane.showInputDialog(null, "New set name:", currentSetTitle);
            if ((newSetTitle != null) && !newSetTitle.equals(currentSetTitle)) {
                flashcardSetRepo.updateSetTitle(flashcardSet, newSetTitle);
            }
        }
    }

    public void handleDeleteSet() {
        FlashcardSet flashcardSet = flashcardSetsFrame.getSelectedSet();
        if (flashcardSet != null) {
            flashcardSetRepo.deleteSet(flashcardSet);
        }
    }

    public void handleDeleteFlashcard() {
        Flashcard flashcard = flashcardsFrame.getSelectedFlashcard();
        if (flashcard != null) {
            flashcardRepo.deleteFlashcard(flashcard);
        }
    }

    public void handleEditFlashcard() {
        Flashcard flashcard = flashcardsFrame.getSelectedFlashcard();
        if (flashcard != null) {
            flashcardRepo.setCurrentFlashcard(flashcard);
            flashcardFrame = new FlashcardFrame(this);
            flashcardFrame.setQuestion(flashcard.getQuestion());
            flashcardFrame.setAnswer(flashcard.getAnswer());
        }
    }

    public void handleAddNewFlashcard() {
        flashcardFrame = new FlashcardFrame(this);
        flashcardsFrame.setEnabled(false);
    }

    public void handleAddNewQuestion() {
        quizQuestionAnswerFrame = new QuizQuestionAnswerFrame(this);
        quizQuestionsScreen.setEnabled(true);
    }

    public void handleCancelFlashcardFrame() {
        flashcardFrame.dispose();
        flashcardsFrame.setEnabled(true);
    }

    public void handleCancelQuestionScreen() {
        quizQuestionAnswerFrame.dispose();
        quizQuestionsScreen.setEnabled(true);

    }

    public void handleSaveFlashcard() {
        Flashcard flashcard = flashcardRepo.getCurrentFlashcard();
        if (flashcard != null) {
            String question = flashcardFrame.getQuestion();
            String answer = flashcardFrame.getAnswer();
            if ((!question.isEmpty()) || (!question.isBlank())) {
                flashcard.setQuestion(question);
                flashcard.setAnswer(answer);
                flashcardRepo.updateFlashcard(flashcard);
                flashcardRepo.setCurrentFlashcard(null);
                flashcardFrame.dispose();
                flashcardsFrame.setEnabled(true);
            }
            else {
                JOptionPane.showMessageDialog(null, "Question field can not be empty.");
            }
        }
        else {
            String question = flashcardFrame.getQuestion();
            String answer = flashcardFrame.getAnswer();
            if ((!question.isEmpty()) || (!question.isBlank())) {
                flashcardRepo.addNewFlashcard(question, answer);
                flashcardRepo.setCurrentFlashcard(null);
                flashcardFrame.dispose();
                flashcardsFrame.setEnabled(true);
            }
            else {
                JOptionPane.showMessageDialog(null, "Question field can not be empty.");
            }
        }
    }

     public void handleSaveNewQuestion() {
        String questionText = quizQuestionAnswerFrame.getQuestion();
        int quizId = quizRepo.getCurrentQuiz().getId();
        Question questionToAdd = new Question(questionText, quizId);

        int questionId = questionRepo.addNewQuestion(questionToAdd);

        Answer answer1 = new Answer(quizQuestionAnswerFrame.getAns1(), quizQuestionAnswerFrame.getBtn1(), questionId);
        questionRepo.addNewAnswer(answer1);
        Answer answer2 = new Answer(quizQuestionAnswerFrame.getAns2(), quizQuestionAnswerFrame.getBtn2(), questionId);
        questionRepo.addNewAnswer(answer2);
        Answer answer3 = new Answer(quizQuestionAnswerFrame.getAns3(), quizQuestionAnswerFrame.getBtn3(), questionId);
        questionRepo.addNewAnswer(answer3);
        Answer answer4 = new Answer(quizQuestionAnswerFrame.getAns4(), quizQuestionAnswerFrame.getBtn4(), questionId);
        questionRepo.addNewAnswer(answer4);

        quizQuestionAnswerFrame.dispose();
        quizQuestionsScreen.setEnabled(true);
        System.out.println("question saved");
    }




    public void handleBackToMainScreen() {
        mainScreen = new MainScreen(this);
        if (flashcardSetsFrame != null) {
            flashcardSetsFrame.dispose();
        }
        if (quizzesScreen != null) {
            quizzesScreen.dispose();
        }
    }


    public void handleBackToFlashcardSetsScreen() {
        flashcardSetsFrame = new FlashcardSetsFrame(this);
        List<FlashcardSet> flashcardSets = flashcardSetRepo.getFlashcardSets(userManager.getCurrentUser());
        handleUpdateSetsList(flashcardSets);
        flashcardsFrame.dispose();
    }

    public void handleBackToQuizzesScreen() {
        quizzesScreen = new QuizzesScreen(this);
        List<Quiz> quiz = quizRepo.getQuiz(userManager.getCurrentUser());
        handleUpdateQuizList(quiz);
        quizQuestionsScreen.dispose();
    }

    public void openSignUpScreen() {
        signUpScreen = new SignUpScreen(this);
        signinScreen.dispose();
    }

    public void openSignInScreen() {
        signinScreen = new SigninScreen(this);
        signUpScreen.dispose();
    }

    public int getCurrentUserId(String username) {
        try {
            return userManager.getCurrentUserId(username);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
            //heeeej
            //jehefbjedbvjedbvjbved
        }
    }

    public void openMainScreen() {
        mainScreen = new MainScreen(this); //New instance of main screen
        //Retrieve flashcard sets for the user
        User currentUser = userManager.getCurrentUser();
        List<FlashcardSet> flashcardsSets = flashcardSetRepo.getFlashcardSets(currentUser);
        //Set the retrived flashcard sets to the repository
        flashcardSetRepo.setFlashcardSetsFrame(flashcardSetsFrame);

        //List<FlashcardSet> flashcardSets = flashcardSetRepo.getFlashcardSets(username);
        //handleUpdateSetsList(flashcardSets);
    }

    public void handleLogOut() {
        userManager.setCurrentUser(null);
        signinScreen = new SigninScreen(this);
        mainScreen.dispose();
    }


    //-------------------------------------

    public void onPlayButtonClick() {
        FlashcardSet selectedSet = flashcardSetsFrame.getSelectedSet();
        if (selectedSet != null) {
            List<Flashcard> flashcards = flashcardRepo.getFlashcards(selectedSet.getId());
            flashcardSetsFrame.dispose();

            if (!flashcards.isEmpty()) {
                startPlayMode(flashcards);
            } else {
                JOptionPane.showMessageDialog(null, "This flashcard is empty.");

            }
        } else {
            JOptionPane.showMessageDialog(null, "Please select a flashcard set.");
        }
    }

    private void startPlayMode( List<Flashcard> flashcards) {
        playScreen = new FlashcardPlayScreen(this, flashcards);
    }

    public void handleExitPlayMode() {
        flashcardSetsFrame = new FlashcardSetsFrame(this);
        if (playScreen != null) {
            User currentUser = userManager.getCurrentUser();
            List<FlashcardSet> flashcardSets = flashcardSetRepo.getFlashcardSets(currentUser);
            handleUpdateSetsList(flashcardSets);
        }
        playScreen.dispose();
    }

    public static void main(String[] args) {
        Controller controller = new Controller();
    }
}
