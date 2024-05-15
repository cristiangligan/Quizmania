package model;

import javax.xml.transform.Result;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuizRepo {

    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
    private UserManager userManager;
    public Connection connection;
    public static final String UPDATE_QUIZ_LIST = "update_quiz_list";

    public QuizRepo(UserManager userManager, Connection connection) {
        this.userManager = userManager;
        this.connection = connection;
    }

    public void addNewQuiz(String newQuizTitle, String username) {
        if((newQuizTitle != null || newQuizTitle.isBlank())) {
         String insertQuery = "INSERT INTO public.quiz (title, user_id) VALUES (?, ?)"; // add to database
         try {
             PreparedStatement statement = connection.prepareStatement(insertQuery);
             statement.setString(1, newQuizTitle);
             statement.setInt(2, userManager.getCurrentUserId(username));
             int rowCount = statement.executeUpdate();
             propertyChangeSupport.firePropertyChange(UPDATE_QUIZ_LIST, null, getQuiz());
             System.out.println(rowCount);
         } catch (SQLException e) {
             throw new RuntimeException(e);
            }
        }
    }


    public ArrayList<Quiz> getQuiz() {
        ArrayList<Quiz> quizzes = new ArrayList<>();
        String selectQuizData = "SELECT * FROM public.quiz\n" + // add to database
                                 "ORDER BY id ASC";
        try {
            PreparedStatement statement = connection.prepareStatement(selectQuizData);
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
