package com.glownia.maciej.stoicsquotesquizquestionsrestapi.quiz;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * Handles communication client-server.
 */
@RestController
public class QuizResource {

    private QuizService quizService;

    public QuizResource(QuizService quizService) {
        this.quizService = quizService;
    }

    // quiz => quizzes
    @RequestMapping("/quizzes")
    public List<Quiz> retrieveAllQuizzes() {
        return quizService.retrieveAllQuizzes();
    }

    // get specific quiz
    @RequestMapping("/quizzes/{quizId}")
    public Quiz retrieveQuizQuizById(@PathVariable String quizId) {
        Quiz quiz = quizService.retrieveQuizQuizById(quizId);

        // if quiz is null status is still 200 but no content appear, that's why need to protect from it
        if(quiz == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        return quiz;
    }

    @RequestMapping("/quizzes/{quizId}/quotes")
    public List<Quote> retrieveAllQuotes(@PathVariable String quizId) {
        List<Quote> quotes = quizService.retrieveAllQuotes(quizId);

        if(quotes == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        return quotes;
    }
}
