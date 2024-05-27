package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FlashcardRepo {
    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
    private Connection connection;
    private FlashcardSet flashcardSet;

    private Flashcard currentFlashcard;
    public static final String UPDATE_FLASHCARD_LIST = "update_flashcard_list";

    public FlashcardRepo(FlashcardSet flashcardSet, Connection connection) {
        this.flashcardSet = flashcardSet;
        this.connection = connection;
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

    public void addNewFlashcard(String question, String answer) {
        if (flashcardSet != null) {
            String insertQuery = "INSERT INTO public.flashcard (question, answer, flashcards_set_id) VALUES (?, ?, ?)";
            try {
                PreparedStatement statement = connection.prepareStatement(insertQuery);
                statement.setString(1, question);
                statement.setString(2, answer);
                statement.setInt(3, flashcardSet.getId());
                int rowCount = statement.executeUpdate();
                propertyChangeSupport.firePropertyChange(UPDATE_FLASHCARD_LIST, null, getFlashcards(flashcardSet.getId()));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void deleteFlashcard(Flashcard flashcard) {
        int id = flashcard.getId();
        String deleteFlashcardQuery = "DELETE FROM public.flashcard WHERE flashcard.id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(deleteFlashcardQuery);
            statement.setInt(1, id);
            statement.executeUpdate();
            propertyChangeSupport.firePropertyChange(UPDATE_FLASHCARD_LIST, null, getFlashcards(flashcard.getFlashcardSetId()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Flashcard getCurrentFlashcard() {
        return currentFlashcard;
    }

    public void setCurrentFlashcard(Flashcard currentFlashcard) {
        this.currentFlashcard = currentFlashcard;
    }



    public void subscribeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void unsubscribeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

    public void updateFlashcard(Flashcard flashcard) {
        if (flashcard != null) {
            String updateQuery = "UPDATE public.flashcard SET question = ?, answer = ? WHERE id = ?";
            try {
                PreparedStatement statement = connection.prepareStatement(updateQuery);
                statement.setString(1, flashcard.getQuestion());
                statement.setString(2, flashcard.getAnswer());
                statement.setInt(3, flashcard.getId());
                int rowCount = statement.executeUpdate();
                propertyChangeSupport.firePropertyChange(UPDATE_FLASHCARD_LIST, null, getFlashcards(flashcard.getFlashcardSetId()));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
