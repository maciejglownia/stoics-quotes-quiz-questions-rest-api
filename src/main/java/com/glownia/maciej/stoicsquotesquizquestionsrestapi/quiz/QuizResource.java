package com.glownia.maciej.stoicsquotesquizquestionsrestapi.quiz;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
