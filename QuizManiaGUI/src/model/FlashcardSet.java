package model;

public class FlashcardSet {


    private int id;
    private String title;
    private int user_id;

    public FlashcardSet(int id, String title, int user_id) {
        this.id = id;
        this.title = title;
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public int getUser_id() {
        return user_id;
    }
}
