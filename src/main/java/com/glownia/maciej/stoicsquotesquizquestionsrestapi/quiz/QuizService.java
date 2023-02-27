package com.glownia.maciej.stoicsquotesquizquestionsrestapi.quiz;

import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * Contains static List of quizzes.
 */
@Service
public class QuizService {

    private static List<Quiz> quizList = new ArrayList<>();

    static {

        Quote quote1 = new Quote("Quote1", "Quote of Marcus Aurelius", Arrays.asList(
                "Marcus Aurelius", "Epictetus", "Seneca", "Zeno"), "Marcus Aurelius");
        Quote quote2 = new Quote("Quote2", "Quote of Epictetus", Arrays.asList(
                "Marcus Aurelius", "Epictetus", "Seneca", "Zeno"), "Epictetus");
        Quote quote3 = new Quote("Quote3", "Quote of Seneca", Arrays.asList(
                "Marcus Aurelius", "Epictetus", "Seneca", "Zeno"), "Seneca");
        Quote quote4 = new Quote("Quote4", "Quote of Zeno", Arrays.asList(
                "Marcus Aurelius", "Epictetus", "Seneca", "Zeno"), "Zeno");

        List<Quote> quotes = new ArrayList<>(Arrays.asList(quote1, quote2, quote3, quote4));

        Quiz quiz = new Quiz(
                "Quiz1", "Stoics' Quotes Quiz",
                "Quiz contains 4 quotes. Each quote is also a question. " +
                        "There are four Stoics: Marcus Aurelius, Epictetus, Seneca, and Zeno. " +
                        "Only one man is an author of specific quote.", quotes);

        quizList.add(quiz);
    }

    public List<Quiz> retrieveAllQuizzes() {
        return quizList;
    }

    public Quiz retrieveQuizQuizById(String quizId) {

        Predicate<? super Quiz> predicate = quiz -> quiz.getId().equalsIgnoreCase(quizId);

        Optional<Quiz> optionalQuiz = quizList.stream().filter(predicate).findFirst();

        if (optionalQuiz.isEmpty()) return null;

        return optionalQuiz.get();
    }

    public List<Quote> retrieveAllQuotes(String quizId) {

        Quiz quiz = retrieveQuizQuizById(quizId);

        if (quiz == null) return null;

        return quiz.getQuotes();
    }

    public Quote retrieveSpecificQuote(String quizId, String quoteId) {

        List<Quote> quotes = retrieveAllQuotes(quizId);

        if (quotes == null) return null;

        Optional<Quote> optionalQuote =
                quotes.stream().filter
                        (quote -> quote.getId().equalsIgnoreCase(quoteId)).findFirst();

        if (optionalQuote.isEmpty()) return null;

        return optionalQuote.get();
    }

    public String addNewQuote(String quizId, Quote quote) {

        List<Quote> quotes = retrieveAllQuotes(quizId);

        quote.setId(generateRandomId());

        quotes.add(quote);

        return quote.getId();
    }

    private String generateRandomId() {
        SecureRandom secureRandom = new SecureRandom();
        String randomId = new BigInteger(32, secureRandom).toString();
        return randomId;
    }

    public String deleteSpecificQuote(String quizId, String quoteId) {

        List<Quote> quotes = retrieveAllQuotes(quizId);

        if (quotes == null) return null;

        Predicate<Quote> quotePredicate = quote -> quote.getId().equalsIgnoreCase(quoteId);
        boolean removed = quotes.removeIf(quotePredicate);//  remove if specific predicate matches)

        if(!removed) return null;

        return quoteId;
    }
}
