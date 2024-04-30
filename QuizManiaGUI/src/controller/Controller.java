package controller;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Date;

import model.User;
import model.Users;
import view.*;

import javax.swing.*;

public class Controller {
    private SigninScreen signinScreen;
    private Connection connection;

    public Controller() {
        connectToDatabase();
        SwingUtilities.invokeLater(() -> {
            //SignUpScreen signUpScreen = new SignUpScreen(this);
            MainScreen mainScreen = new MainScreen(this);
            //mainScreen.setVisible(true);
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
        //Controller controller = new Controller();
    }

    /*
    public Users getCurrentUser() {
        return new Users("cristian", "password123", new Date());
    }*/

    public void addNewSet() {
        String newSetTitle = JOptionPane.showInputDialog(null, "New set name:");
        if ((newSetTitle != null) || !newSetTitle.isBlank()) {
            //Users currentUser = getCurrentUser();
            String insertQuery = "INSERT INTO public.flashcards_set (title, user_id) VALUES (?, ?)";
            try {
                PreparedStatement statement = connection.prepareStatement(insertQuery);
                statement.setString(1, newSetTitle);
                statement.setInt(2, 7);
                int rowCount = statement.executeUpdate();
                System.out.println(rowCount);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
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
