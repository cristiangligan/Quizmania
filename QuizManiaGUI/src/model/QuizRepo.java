package model;

import view.FlashcardSetsFrame;
import view.QuizzesScreen;
import javax.swing.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuizRepo {

    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
    private UserManager userManager;
    public Connection connection;
    private QuizzesScreen quizzesScreen;
    public static final String UPDATE_QUIZ_LIST = "update_quiz_list";

    public QuizRepo(UserManager userManager, Connection connection) {
        this.userManager = userManager;
        this.connection = connection;
    }

    // savannah o emma allows user to add quizzes
    public void addNewQuiz(String newQuizTitle) {
        if((newQuizTitle != null) && !newQuizTitle.isBlank()) {
         String insertQuery = "INSERT INTO public.quiz (title, user_id) VALUES (?, ?)";
         try {
             PreparedStatement statement = connection.prepareStatement(insertQuery);
             statement.setString(1, newQuizTitle);
             statement.setInt(2, userManager.getCurrentUser().getId());
             int rowCount = statement.executeUpdate();
             propertyChangeSupport.firePropertyChange(UPDATE_QUIZ_LIST, null, getQuiz(userManager.getCurrentUser()));
         } catch (SQLException e) {
             throw new RuntimeException(e);
            }
        }
    }
//    public void updateQuizTitle(Quiz quiz, String newQuizTitle) { // edit function
//        if ((newQuizTitle != null) && !newQuizTitle.isBlank()) {
//            String updateQuery = "UPDATE public.flashcards_set SET title = ? WHERE id = ?";
//            try {
//                PreparedStatement statement = connection.prepareStatement(updateQuery);
//                statement.setString(1, newQuizTitle);
//                statement.setInt(2, quiz.getId());
//                int rowCount = statement.executeUpdate();
//                propertyChangeSupport.firePropertyChange(UPDATE_QUIZ_LIST, null, getQuiz(userManager.getCurrentUser()));
//            } catch (SQLException e) {
//                throw new RuntimeException(e);
//            }
//        }
//    }

    public void setQuizzesScreen(QuizzesScreen quizzesScreen) {
        this.quizzesScreen = quizzesScreen;
    }


    //Sets the list of flashcard sets and updates UI accordingly
   /* public void setQuiz(QuizzesScreen quizzesScreen) {
        //Check if the frame for displaying flashcard sets is not null
        if (quizzesScreen != null) {
            //Create model for the list of flashcard sets
            DefaultListModel<Quiz> model = new DefaultListModel<>();

            for (Quiz quiz1 : quizzesScreen) { //Add each flashcard set to the model
                model.addElement(quiz1);
            }

            quizzesScreen.getQuizList().setModel(model); //Set the model to the list in the frame

        } else {
            System.out.println("QuizzesScreen is null.");
        }
    }*/

    // savannah o emma - creates arraylist for quizzes
    public ArrayList<Quiz> getQuiz(User user) {
        ArrayList<Quiz> quizzes = new ArrayList<>();
        String selectQuizData = "SELECT * FROM public.quiz\n" +
                                "WHERE user_id = ?" +
                                 "ORDER BY id ASC";
        try {
            PreparedStatement statement = connection.prepareStatement(selectQuizData);
            statement.setInt(1, userManager.getCurrentUser().getId());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                int userId = resultSet.getInt("user_id");
                Quiz quiz = new Quiz(id, title, userId);
                quizzes.add(quiz);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return quizzes;
    }



    public void subscribeListener (PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void unsubscribeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }
}
