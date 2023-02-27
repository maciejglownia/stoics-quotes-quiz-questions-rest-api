package com.glownia.maciej.stoicsquotesquizquestionsrestapi.quiz;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
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
        if (quiz == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        return quiz;
    }

    @RequestMapping("/quizzes/{quizId}/quotes")
    public List<Quote> retrieveAllQuotes(@PathVariable String quizId) {

        List<Quote> quotes = quizService.retrieveAllQuotes(quizId);

        if (quotes == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        return quotes;
    }

    @RequestMapping("/quizzes/{quizId}/quotes/{quoteId}")
    public Quote retrieveSpecificQuote(@PathVariable String quizId, @PathVariable String quoteId) {

        Quote quote = quizService.retrieveSpecificQuote(quizId, quoteId);

        if (quote == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        return quote;
    }

    @RequestMapping(value="/quizzes/{quizId}/quotes", method = RequestMethod.POST)
    public ResponseEntity<Object> addNewQuote(@PathVariable String quizId, @RequestBody Quote quote) {

        String quoteId = quizService.addNewQuote(quizId, quote);

        // Good practice is send the location of the created resource as part of response.
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{quoteId}").buildAndExpand(quoteId).toUri();
        // ResponseEntity handle retrieving status response equal to 201 - CREATED, instead of 200 is giving by default.
        return ResponseEntity.created(location).build();
    }

    @RequestMapping(value="/quizzes/{quizId}/quotes/{quoteId}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteSpecificQuote(@PathVariable String quizId, @PathVariable String quoteId) {

        quizService.deleteSpecificQuote(quizId, quoteId);
        return ResponseEntity.noContent().build();
    }
}
