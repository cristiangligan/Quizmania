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
    public void addNewQuestion(Question question/*String questionsText, HashMap<String, Boolean> answer*/) {
        String insertQuestionQuery = "INSERT INTO public.question(text, quiz_id) VALUES (?, ?)";

        try  (PreparedStatement questionStatement = connection.prepareStatement(insertQuestionQuery, Statement.RETURN_GENERATED_KEYS)) {
            //vad är return genereated keys
            questionStatement.setString(1, question.getQuestion());
            questionStatement.setInt(2, question.getQuizId());
            questionStatement.executeUpdate();

            propertyChangeSupport.firePropertyChange(UPDATE_QUESTION_LIST, null, getQuestions(quiz.getId()));
        } catch (SQLException e) {
            throw new RuntimeException("Error adding new questions", e);
        }
    }

//    public void addNewAnswers(){
//        String insertOptionQuery = "INSERT INTO public.answer(text, question_id, correct) VALUES (?, ?, ?)";
//
//        try (PreparedStatement optionStatement = connection.prepareStatement(insertOptionQuery)) {
//            for (String optionText : answer.keySet()) {
//                optionStatement.setString(1, optionText);
//                optionStatement.setInt(2, questionId);
//                optionStatement.setBoolean(3, answer.get(optionText));
//                //    optionStatement.executeUpdate();
//                int optionRowCount = optionStatement.executeUpdate();
//            }
//            //                for (String options: answer.keySet()) {
//  //                  statement = connection.prepareStatement(insertOptionQuery);
////                    statement.setInt(2, questionsId);
////                    statement.setString(1, options);
////                    statement.setBoolean(3, answer.get(options));
////                    rowCount = statement.executeUpdate();
////                    propertyChangeSupport.firePropertyChange(UPDATE_QUESTION_LIST, null, getQuestions(quiz.getId()));
////                }
//            // }
//        }
//    }


    public List<String> getQuestions(int selectedQuizId) {
        List<String> questions = new ArrayList<>();
        String selectQuizData = "SELECT * FROM public.question\n" + "WHERE quiz_id = " + selectedQuizId;
        try {
            PreparedStatement statement = connection.prepareStatement(selectQuizData);
            //statement.setInt(1, selectedQuizId);
            ResultSet resultSet= statement.executeQuery();
//            Question currentQuestion = null;
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                if (currentQuestion == null || currentQuestion.getId() != id) {
                    currentQuestion = new Question(resultSet.getString("text"), selectedQuizId); // name in database
                    //return new ArrayList<>();
                    questions.add(String.valueOf(currentQuestion));
                }
//                if(currentQuestion.getOptions().isEmpty()){
//                    currentQuestion.getOptions().add(new Options(id, "", false));
//                }
                selectQuizData = "SELECT * FROM public.answer\n" + "WHERE question_id = " + selectedQuizId;
                statement = connection.prepareStatement(selectQuizData);
                ResultSet resultSet1 = statement.executeQuery();
                while (resultSet1.next()) {
                    int optionsId = resultSet1.getInt("question_id"); // name in database
                    String optionsText = resultSet1.getString("text"); // name in database
                    boolean isCorrect = resultSet1.getBoolean("correct");
                    Options options = new Options(optionsId, optionsText,isCorrect);
                    currentQuestion.getOptions().add(options);
                    questions.add(optionsText);
                }
                /*String question = resultSet.getString("question");
                int quizId = resultSet.getInt("quiz_set_id");
                Quiz quiz = new Quiz(id, question, quizId);
                questions.add(quiz);*/
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

