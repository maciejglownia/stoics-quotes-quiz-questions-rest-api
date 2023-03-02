package com.glownia.maciej.stoicsquotesquizquestionsrestapi.quiz;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

// I have to use dynamic port. Not 8080 because it can be busy somehow -> configure webEnvironment to random port above
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class QuizResourceIntegrationTest {

    private static String SPECIFIC_QUOTE_URL = "/quizzes/Quiz1/quotes/Quote1";
    private static String GENERIC_QUOTES_URL = "/quizzes/Quiz1/quotes";
    private static String SPECIFIC_QUIZ_URL = "/quizzes/Quiz1";
    private static String GENERIC_QUIZZES_URL = "/quizzes";

    @Autowired
    private TestRestTemplate template; // the app talk directly to http://localhost:RANDOM_PORT/quizzes/Quiz1/quotes/Quote1

    // Text block from Java 15
    String str = """
            {
              "id": "Quote1",
              "quote": "Quote of Marcus Aurelius",
              "options": [
                "Marcus Aurelius",
                "Epictetus",
                "Seneca",
                "Zeno"
              ],
              "correctAnswer": "Marcus Aurelius"
            }
            """;

    @Test
    void retrieveSpecificQuoteBasicScenario() throws JSONException {

        ResponseEntity<String> responseEntity = template.getForEntity(SPECIFIC_QUOTE_URL, String.class);

        String expectedResponse =
                """
                            {
                                "id":"Quote1",
                                "quote":"Quote of Marcus Aurelius",
                                "correctAnswer":"Marcus Aurelius"
                            }
                        """;

        // NOTE: This order of assertions below is correct
        // check status of response
        assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        // check Content-Type:"application/json"
        assertEquals("application/json", responseEntity.getHeaders().get("Content-Type").get(0));
        // check actual response
        JSONAssert.assertEquals(expectedResponse, responseEntity.getBody(), false); // check [JsonAssertTest] for more
    }

    @Test
    void retrieveAllQuotesBasicScenario() throws JSONException {

        ResponseEntity<String> responseEntity = template.getForEntity(GENERIC_QUOTES_URL, String.class);

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

        assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        assertEquals("application/json", responseEntity.getHeaders().get("Content-Type").get(0));
        JSONAssert.assertEquals(expectedResponse, responseEntity.getBody(), false);
    }

    @Test
    void retrieveSpecificQuizBasicScenario() throws JSONException {

        ResponseEntity<String> responseEntity = template.getForEntity(SPECIFIC_QUIZ_URL, String.class);

        String expectedResponse =
                """
                        {
                          "id": "Quiz1"
                        }
                """;

        assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        assertEquals("application/json", responseEntity.getHeaders().get("Content-Type").get(0));
        JSONAssert.assertEquals(expectedResponse, responseEntity.getBody(), false);
    }

    @Test
    void retrieveAllQuizzesBasicScenario() throws JSONException {

        ResponseEntity<String> responseEntity = template.getForEntity(GENERIC_QUIZZES_URL, String.class);

        String expectedResponse =
                """
                        [
                            {
                            "id": "Quiz1"
                            }
                        ]
                """;

        assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        assertEquals("application/json", responseEntity.getHeaders().get("Content-Type").get(0));
        JSONAssert.assertEquals(expectedResponse, responseEntity.getBody(), false);
    }
}
