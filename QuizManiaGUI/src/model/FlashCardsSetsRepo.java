package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FlashCardsSetsRepo {
    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
    private UserManager userManager;
    private Connection connection;


    public static final String UPDATE_SETS_LIST = "update_sets_list";

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
                propertyChangeSupport.firePropertyChange(UPDATE_SETS_LIST, null, getFlashcardSets());
                System.out.println(rowCount);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public List<FlashcardsSet> getFlashcardSets() {
        ArrayList<FlashcardsSet> flashcardsSets = new ArrayList<>();
        String selectFlashcardSetData = "SELECT * FROM public.flashcards_set\n" +
                                        "ORDER BY id ASC ";
        try {
            PreparedStatement statement = connection.prepareStatement(selectFlashcardSetData);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                int userId = resultSet.getInt("user_id");
                FlashcardsSet flashcardsSet = new FlashcardsSet(id, title, userId);
                flashcardsSets.add(flashcardsSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return flashcardsSets;
    }

    public List<Flashcard> getFlashcards(int selectedFlashcardSetId) {
        ArrayList<Flashcard> flashcards = new ArrayList<>();
        String selectFlashcardSetData = "SELECT * FROM public.flashcard\n" +
                                        "WHERE flashcards_set_id = " + selectedFlashcardSetId;
        try {
            PreparedStatement statement = connection.prepareStatement(selectFlashcardSetData);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String question = resultSet.getString("question");
                String answer = resultSet.getString("answer");
                int flashcardSetId = resultSet.getInt("flashcards_set_id");
                Flashcard flashcard = new Flashcard(id, question, answer, flashcardSetId);
                flashcards.add(flashcard);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return flashcards;
    }

    public void subscribeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void unsubscribeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }
}
