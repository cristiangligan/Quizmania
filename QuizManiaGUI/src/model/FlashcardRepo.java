package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * FlashcardRepo manages the operations for flashcards in the database.
 *
 * <p>This class uses a database connection to perform operations suach as
 * retrieving, adding, deleting, and updateing flashcards in the database. It suppoerts property change listeners.
 * </p>
 * @author Cristian Gligan
 */
public class FlashcardRepo {
    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
    private Connection connection;
    private FlashcardSet flashcardSet;

    private Flashcard currentFlashcard;
    public static final String UPDATE_FLASHCARD_LIST = "update_flashcard_list";

    /**
     * Constructs a new flashcard.
     *
     * @param flashcardSet The flashcardSet associated with this repository
     * @param connection The database connection to be used for operations.
     */
    public FlashcardRepo(FlashcardSet flashcardSet, Connection connection) {
        this.flashcardSet = flashcardSet;
        this.connection = connection;
    }

    /**
     * Retrieves a list of flashcards for the specified flashcard set ID.
     *
     * @param selectedFlashcardSetId The ID of the flashcard set to retrieve flashcards for.
     * @return A list of flashcards
     */
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

    /**
     * Adds a new flashcard to the database.
     *
     * @param question The question of the new flashcard.
     * @param answer The answer of the new flashcard.
     */
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

    /**
     * Deletes a flashcard from the database.
     *
     * @param flashcard The flashcard to be deleted.
     */
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

    /**
     * Gets the currect flashcard.
     *
     * @return The current flashcard
     */
    public Flashcard getCurrentFlashcard() {
        return currentFlashcard;
    }

    /**
     * Sets the current flashcard.
     *
     * @param currentFlashcard The flashcard to be set as current.
     */
    public void setCurrentFlashcard(Flashcard currentFlashcard) {
        this.currentFlashcard = currentFlashcard;
    }


    /**
     * Subscribes a listener to property change events.
     *
     * @param listener The listener to be subscribed.
     */
    public void subscribeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    /**
     * Unsubscribe a listener from property change events.
     *
     * @param listener The listener to be unsubscribed.
     */
    public void unsubscribeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

    /**
     * Updates an existing flashcard in the database.
     *
     * @param flashcard The flashcard to be updated.
     */
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
