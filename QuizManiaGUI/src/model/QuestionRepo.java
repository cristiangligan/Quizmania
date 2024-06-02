package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class QuestionRepo {
    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
    private Connection connection;
    private Quiz quiz;
    private Question currentQuestion;
    public static final String UPDATE_QUESTION_LIST = "update_question_list";

    public QuestionRepo(Quiz quiz, Connection connection) {
        this.connection = connection;
        this.quiz = quiz;
    }

    //cristian, savannah, emma author - allows user to add a new question and saves to database
    public int addNewQuestion(Question question/*String questionsText, HashMap<String, Boolean> answer*/) {
        String insertQuestionQuery = "INSERT INTO public.question(text, quiz_id) VALUES (?, ?)";
        int questionId = -1;
        try {
            PreparedStatement questionStatement = connection.prepareStatement(insertQuestionQuery, Statement.RETURN_GENERATED_KEYS);
            questionStatement.setString(1, question.getQuestion());
            questionStatement.setInt(2, question.getQuizId());
            questionStatement.execute();
            ResultSet resultSet = questionStatement.getGeneratedKeys();
            if (resultSet.next()) {
                questionId = resultSet.getInt(1);
            }
            propertyChangeSupport.firePropertyChange(UPDATE_QUESTION_LIST, null, getQuestions(quiz.getId()));
        } catch (SQLException e) {
            throw new RuntimeException("Error adding new questions", e);
        }
        return questionId;
    }

    //cristian och savannah author - allows user to add answers and saves to database
    public void addNewAnswer(Answer answer) {
        String insertAnswerQuery = "INSERT INTO public.answer(text, question_id, correct) VALUES (?, ?, ?)";

        try  (PreparedStatement questionStatement = connection.prepareStatement(insertAnswerQuery)) {

            questionStatement.setString(1, answer.getText());
            questionStatement.setInt(2, answer.getQuestionId());
            questionStatement.setBoolean(3, answer.isCorrect());
            questionStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //savannah cristian o emma - creates a list of questions
    public List<Question> getQuestions(int selectedQuizId) {
        List<Question> questions = new ArrayList<>();
        String selectQuizData = "SELECT * FROM public.question\n" + "WHERE quiz_id = " + selectedQuizId;
        try {
            PreparedStatement statement = connection.prepareStatement(selectQuizData);
            ResultSet resultSet = statement.executeQuery();
//            Question currentQuestion = null;
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String text = resultSet.getString("text");
                int quiz_id = resultSet.getInt("quiz_id");
                Question question = new Question(text, quiz_id);
                question.setId(id);
                questions.add(question);
//                if(currentQuestion.getOptions().isEmpty()){
//                    currentQuestion.getOptions().add(new Options(id, "", false));
//                }
            }
            return questions;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void subscribeListener (PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void unsubscribeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }


}

