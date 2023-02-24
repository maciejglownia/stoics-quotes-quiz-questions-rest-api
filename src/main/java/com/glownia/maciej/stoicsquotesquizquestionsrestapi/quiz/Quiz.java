package com.glownia.maciej.stoicsquotesquizquestionsrestapi.quiz;

import java.util.List;

/**
 * Represents set of questions with quotes.
 */
public class Quiz {

    public Quiz() {
    }

    public Quiz(String id, String title, String description, List<Quote> quotes) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.quotes = quotes;
    }

    private String id;
    private String title;
    private String description;
    private List<Quote> quotes;

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public List<Quote> getQuotes() {
        return quotes;
    }

    @Override
    public String toString() {
        return "Quiz{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", quotes=" + quotes +
                '}';
    }
}

