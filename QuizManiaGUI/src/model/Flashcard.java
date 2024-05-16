package model;

public class Flashcard {
    private int id;
    private String question;
    private String answer;
    private int flashcardSetId;
    public Flashcard(int id, String question, String answer, int flashcardSetId) {
        this.id = id;
        this.question = question;
        this.answer = answer;
        this.flashcardSetId = flashcardSetId;
    }

    @Override
    public String toString() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }
    public String getQuestion() {
        return question;
    }
}
