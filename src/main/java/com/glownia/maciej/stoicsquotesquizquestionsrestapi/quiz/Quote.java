package com.glownia.maciej.stoicsquotesquizquestionsrestapi.quiz;

import java.util.List;

/**
 * Represents single Quote which is also a question at the same time.
 * Contains id, quote, 4 options to choose, and correct answer.
 */
public class Quote {

    public Quote() {
    }

    public Quote(String id, String quote, List<String> options, String correctAnswer) {
        this.id = id;
        this.quote = quote;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }

    private String id;
    private String quote;
    private List<String> options;
    private String correctAnswer;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuote() {
        return quote;
    }

    public List<String> getOptions() {
        return options;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    @Override
    public String toString() {
        return "Quote{" +
                "id='" + id + '\'' +
                ", quote='" + quote + '\'' +
                ", answer=" + options +
                ", correctAnswer='" + correctAnswer + '\'' +
                '}';
    }
}
