package model;

import java.util.ArrayList;

public class FlashcardsSet {
    //private ArrayList<Flashcard> flashcards;
    private int id;
    private String title;
    private int user_id;

    public FlashcardsSet(int id, String title, int user_id) {
        this.id = id;
        this.title = title;
        this.user_id = user_id;
        //this.flashcards = flashcards;
    }

    @Override
    public String toString() {
        return id + ". " + title;
    }
}
