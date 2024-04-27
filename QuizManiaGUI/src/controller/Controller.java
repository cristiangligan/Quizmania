package controller;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

import model.Users;
import view.*;

public class Controller {
    private SigninScreen signinScreen;
    private SignUpScreen signUpScreen;
   //private Users user;
    private Connection connection;

    public Controller() {
        connectToDatabase();
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

    public static void main(String[] args) {
        //SigninScreen signinScreen = new SigninScreen();
        //FlashcardsFrame flashcardsFrame = new FlashcardsFrame();
        QuizzesScreen quizzesScreen = new QuizzesScreen();
        QuizQuestions quizQuestions = new QuizQuestions();

    }
}
