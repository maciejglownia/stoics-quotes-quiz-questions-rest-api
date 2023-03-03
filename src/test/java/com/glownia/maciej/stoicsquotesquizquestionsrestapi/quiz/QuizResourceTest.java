package com.glownia.maciej.stoicsquotesquizquestionsrestapi.quiz;

import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

// Launch up web context with specific resource -> QuizResource
@WebMvcTest(controllers = QuizResource.class)
public class QuizResourceTest {

    @MockBean
    private QuizService quizService;

    @Autowired
    private MockMvc mockMvc;

    // Mock out specific method
    // Next fire a request

    private static String SPECIFIC_QUOTE_URL = "http://localhost:8080/quizzes/Quiz1/quotes/Quote1";

    private static String GENERIC_QUOTES_URL = "http://localhost:8080/quizzes/Quiz1/quotes";

    private static String SPECIFIC_QUIZ_URL = "http://localhost:8080/quizzes/Quiz1";

    private static String GENERIC_QUIZZES_URL = "http://localhost:8080/quizzes";

    @Test
    void retrieveSpecificQuote404Scenario() throws Exception {

        RequestBuilder requestBuilder =
                MockMvcRequestBuilders.get(SPECIFIC_QUOTE_URL).accept(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        assertEquals(404, mvcResult.getResponse().getStatus());

        System.out.println(mvcResult.getResponse().getContentAsString()); // nothing
        System.out.println(mvcResult.getResponse().getStatus()); // 404
    }

    @Test
    void retrieveSpecificQuoteBasicScenario() throws Exception {

        RequestBuilder requestBuilder =
                MockMvcRequestBuilders.get(SPECIFIC_QUOTE_URL).accept(MediaType.APPLICATION_JSON);

        Quote quote = new Quote("Quote1", "Quote of Marcus Aurelius", Arrays.asList(
                "Marcus Aurelius", "Epictetus", "Seneca", "Zeno"), "Marcus Aurelius");

        when(quizService.retrieveSpecificQuote("Quiz1", "Quote1")).thenReturn(quote);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        String expectedResponse =
                """
                            {
                                "id":"Quote1",
                                "quote":"Quote of Marcus Aurelius",
                                "options":["Marcus Aurelius","Epictetus","Seneca","Zeno"],
                                "correctAnswer":"Marcus Aurelius"
                        }        
                        """;

        MockHttpServletResponse response = mvcResult.getResponse();

        System.out.println(response.getContentAsString()); // object
        System.out.println(response.getStatus()); // 200

        assertEquals(200, response.getStatus());
        JSONAssert.assertEquals(expectedResponse, response.getContentAsString(), false);
    }

    @Test
    void retrieveAllQuotesBasicScenario() throws Exception {

        RequestBuilder requestBuilder =
                MockMvcRequestBuilders.get(GENERIC_QUOTES_URL).accept(MediaType.APPLICATION_JSON);

        Quote quote1 = new Quote("Quote1", "Quote of Marcus Aurelius", Arrays.asList(
                "Marcus Aurelius", "Epictetus", "Seneca", "Zeno"), "Marcus Aurelius");
        Quote quote2 = new Quote("Quote2", "Quote of Epictetus", Arrays.asList(
                "Marcus Aurelius", "Epictetus", "Seneca", "Zeno"), "Epictetus");
        Quote quote3 = new Quote("Quote3", "Quote of Seneca", Arrays.asList(
                "Marcus Aurelius", "Epictetus", "Seneca", "Zeno"), "Seneca");
        Quote quote4 = new Quote("Quote4", "Quote of Zeno", Arrays.asList(
                "Marcus Aurelius", "Epictetus", "Seneca", "Zeno"), "Zeno");

        List<Quote> quotes = new ArrayList<>(Arrays.asList(quote1, quote2, quote3, quote4));

        when(quizService.retrieveAllQuotes("Quiz1")).thenReturn(quotes);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString()); // objects
        System.out.println(mvcResult.getResponse().getStatus()); // 200

        String expectedResponse =
                """
                            [
                              {
                                "id": "Quote1"
                              },
                              {
                                "id": "Quote2"
                              },
                              {
                                "id": "Quote3"
                              },
                              {
                                "id": "Quote4"
                              }
                            ]
                        """;
        assertEquals(200, mvcResult.getResponse().getStatus());
        JSONAssert.assertEquals(expectedResponse, mvcResult.getResponse().getContentAsString(), false);
    }

    @Test
    void retrieveSpecificQuizBasicScenario() throws Exception {

        RequestBuilder requestBuilder =
                MockMvcRequestBuilders.get(SPECIFIC_QUIZ_URL).accept(MediaType.APPLICATION_JSON);

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

        when(quizService.retrieveQuizQuizById("Quiz1")).thenReturn(quiz);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        String expectedResponse =
                """
                        {
                          "id": "Quiz1",
                          "title": "Stoics' Quotes Quiz",
                          "quotes": [
                            {
                              "id": "Quote1"
                            },
                            {
                              "id": "Quote2"
                            },
                            {
                              "id": "Quote3"
                            },
                            {
                              "id": "Quote4"
                            }
                          ]
                        }
                        """;

        MockHttpServletResponse response = mvcResult.getResponse();

        System.out.println(response.getContentAsString()); // object
        System.out.println(response.getStatus()); // 200

        assertEquals(200, response.getStatus());
        JSONAssert.assertEquals(expectedResponse, response.getContentAsString(), false);
    }

    @Test
    void retrieveAllQuizzes() throws Exception {

        RequestBuilder requestBuilder =
                MockMvcRequestBuilders.get(GENERIC_QUIZZES_URL).accept(MediaType.APPLICATION_JSON);

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

        List<Quiz> quizList = new ArrayList<>();
        quizList.add(quiz);

        when(quizService.retrieveAllQuizzes()).thenReturn(quizList);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        String expectedResponse =
                """
                        [
                        {
                          "id": "Quiz1",
                          "title": "Stoics' Quotes Quiz",
                          "quotes": [
                            {
                              "id": "Quote1"
                            },
                            {
                              "id": "Quote2"
                            },
                            {
                              "id": "Quote3"
                            },
                            {
                              "id": "Quote4"
                            }
                          ]
                        }
                        ]
                        """;

        MockHttpServletResponse response = mvcResult.getResponse();

        System.out.println(response.getContentAsString()); // object
        System.out.println(response.getStatus()); // 200

        assertEquals(200, response.getStatus());
        JSONAssert.assertEquals(expectedResponse, response.getContentAsString(), false);
    }

    // Assumptions:
    // POST
    // http://localhost:8080/quizzes/Quiz1/quotes
    // 201
    // Location: http://localhost:8080/quizzes/Quiz1/quotes/4110677834

    @Test
    void addNewQuoteBasicScenario() throws Exception {

        String requestBody =
                """
                            {
                                "quote": "Your favorite Quote of Marcus Aurelius",
                                "options":[
                                    "Quote1",
                                    "Quote2",
                                    "Quote3",
                                    "Quote4"
                                ],
                                "correctAnswer": "Marcus Aurelius"
                            }
                        """;

        when(quizService.addNewQuote(anyString(), any())).thenReturn("SOME_ID");

        RequestBuilder requestBuilder =
                MockMvcRequestBuilders
                        .post(GENERIC_QUOTES_URL)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();

        String locationHeader = response.getHeader("Location");
        System.out.println(response.getStatus());
        System.out.println(locationHeader); // result: http://localhost:8080/quizzes/Quiz1/quotes/SOME_ID

        assertEquals(201, response.getStatus());
        assertTrue(locationHeader.contains("quizzes/Quiz1/quotes/SOME_ID"));
    }

}
