package model;

public class Flashcard {
    private int id;

    private String question;
    private String answer;

    public int getId() {
        return id;
    }

    public int getFlashcardSetId() {
        return flashcardSetId;
    }

    private int flashcardSetId;
    public Flashcard(int id, String question, String answer, int flashcardSetId) {
        this.id = id;
        this.question = question;
        this.answer = answer;
        this.flashcardSetId = flashcardSetId;
    }

    public Flashcard(String question, String answer, int flashcardSetId) {
        this(Integer.MIN_VALUE, question, answer, flashcardSetId);
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

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
