package model;

/**
 * Flashcard represents a ingel flashcard with a question and answer.
 *
 * <p> This class provides methods to get and set flashcard's properties,
 * including the question, answer and associated flashcard set ID
 * </p>
 * @author Crsitian Gligan
 * @author Kaye Moran
 * @author Jessica Puente
 */
public class Flashcard {
    private int id;

    private String question;
    private String answer;

    /**
     * Resturns the ID of the flashcard.
     *
     * @return The ID of the flashcard
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the ID of the flashcard set this flashcard belongs to.
     *
     * @return The ID of the flashcard set.
     */
    public int getFlashcardSetId() {
        return flashcardSetId;
    }
    private int flashcardSetId;

    /**
     * Constructs a new flashcard with specific ID, question, answer and flashcard.
     *
     * @param id The ID of the flashcard.
     * @param question The question of the flashcard.
     * @param answer The answer of the flashcard.
     * @param flashcardSetId The ID of the flashcard set this flashcard belongs to.
     */
    public Flashcard(int id, String question, String answer, int flashcardSetId) {
        this.id = id;
        this.question = question;
        this.answer = answer;
        this.flashcardSetId = flashcardSetId;
    }

    /**
     *Constructs a new flashcard with the specefied wuestin, answer and flashcard set.
     * @param question The question text of the flashcard.
     * @param answer The answer text of the flashcard.
     * @param flashcardSetId The ID of the flashcard set this flashcard belongs to.
     */
    public Flashcard(String question, String answer, int flashcardSetId) {
        this(Integer.MIN_VALUE, question, answer, flashcardSetId);
    }

    /**
     *Returns string representation of the flashcard, the question text.
     *
     * @return The question text
     */
    @Override
    public String toString() {
        return question;
    }

    /**
     *Gets the answer of the flashcard.
     *
     * @return The answer of the flashcard
     */
    public String getAnswer() {
        return answer;
    }

    /**
     *Returns the question text of the flashcard.
     *
     * @return The question text
     */
    public String getQuestion() {
        return question;
    }

    /**
     *Sets the question text of the flashcard.
     * '
     * @param question The question text to set.
     */
    public void setQuestion(String question) {
        this.question = question;
    }

    /**
     *Sets the answer text of the flashcard.
     *
     * @param answer The answer text to set.
     */
    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
