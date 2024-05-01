package model;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FlashCardsSetsRepo {
    private UserManager userManager;
    private Connection connection;
    public FlashCardsSetsRepo(UserManager userManager, Connection connection) {
        this.userManager = userManager;
        this.connection = connection;
    }

    public void addNewSet(String newSetTitle) {

        if ((newSetTitle != null) || !newSetTitle.isBlank()) {
            String insertQuery = "INSERT INTO public.flashcards_set (title, user_id) VALUES (?, ?)";
            try {
                PreparedStatement statement = connection.prepareStatement(insertQuery);
                statement.setString(1, newSetTitle);
                statement.setInt(2, userManager.getCurrentUserId());
                int rowCount = statement.executeUpdate();
                System.out.println(rowCount);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
