package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FlashcardSetRepo {
    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
    private UserManager userManager;
    private Connection connection;


    public static final String UPDATE_SETS_LIST = "update_sets_list";

    public FlashcardSetRepo(UserManager userManager, Connection connection) {
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
                propertyChangeSupport.firePropertyChange(UPDATE_SETS_LIST, null, getFlashcardSets());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public List<FlashcardSet> getFlashcardSets() {
        ArrayList<FlashcardSet> flashcardSets = new ArrayList<>();
        String selectFlashcardSetData = "SELECT * FROM public.flashcards_set\n" +
                                        "ORDER BY id ASC ";
        try {
            PreparedStatement statement = connection.prepareStatement(selectFlashcardSetData);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                int userId = resultSet.getInt("user_id");
                FlashcardSet flashcardSet = new FlashcardSet(id, title, userId);
                flashcardSets.add(flashcardSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return flashcardSets;
    }

    public void subscribeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void unsubscribeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }
}
