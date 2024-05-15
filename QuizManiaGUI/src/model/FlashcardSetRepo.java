package model;

import view.FlashcardSetsFrame;

import javax.swing.*;
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
    private FlashcardSetsFrame flashcardSetsFrame;


    public static final String UPDATE_SETS_LIST = "update_sets_list";

    public FlashcardSetRepo(UserManager userManager, Connection connection) {
        this.userManager = userManager;
        this.connection = connection;
    }

    public void addNewSet(String newSetTitle, String username) {
        if ((newSetTitle != null) && !newSetTitle.isBlank()) {
            String insertQuery = "INSERT INTO public.flashcards_set (title, user_id) VALUES (?, ?)";
            try {
                PreparedStatement statement = connection.prepareStatement(insertQuery);
                statement.setString(1, newSetTitle);
                statement.setInt(2, userManager.getCurrentUserId(username));
                int rowCount = statement.executeUpdate();
                propertyChangeSupport.firePropertyChange(UPDATE_SETS_LIST, null, getFlashcardSets(username));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    //Sets the frame for displaying flashcard sets
    public void setFlashcardSetsFrame(FlashcardSetsFrame flashcardSetsFrame) {

        this.flashcardSetsFrame = flashcardSetsFrame;
    }

    //Sets the list of flashcard sets and updates UI accordingly
    public void setFlashcardSets(List<FlashcardSet> flashcardSets) {
        //Check if the frame for displaying flashcard sets is not null
        if (flashcardSetsFrame != null) {
            //Create model for the list of flashcard sets
            DefaultListModel<FlashcardSet> model = new DefaultListModel<>();

            for (FlashcardSet flashcardSet : flashcardSets) { //Add each flashcard set to the model
                model.addElement(flashcardSet);
            }

            flashcardSetsFrame.getSetsList().setModel(model); //Set the model to the list in the frame

        } else {

        } System.out.println("FlashcardSetsFrame is null.");

    }

    public List<FlashcardSet> getFlashcardSets(String username) {
        ArrayList<FlashcardSet> flashcardSets = new ArrayList<>();
        String selectFlashcardSetData = "SELECT * FROM public.flashcards_set\n" +
                                        "WHERE user_id = (SELECT user_id FROM public.users WHERE username = ?) " +
                                        "ORDER BY id ASC ";
        try {
            PreparedStatement statement = connection.prepareStatement(selectFlashcardSetData);
            statement.setString(1, username);
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

    public List<FlashcardSet> getFlashcardSetsForUser(String username) {
        return getFlashcardSets(username);
    }

    public void subscribeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void unsubscribeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }
}
