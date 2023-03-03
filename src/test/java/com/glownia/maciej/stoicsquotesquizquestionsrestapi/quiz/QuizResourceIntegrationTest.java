package com.glownia.maciej.stoicsquotesquizquestionsrestapi.quiz;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Base64;

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

        // Added after added Spring Security
        HttpHeaders headers = createHttpContentTypeAndAuthorizationHeaders();
        HttpEntity<String> httpEntity = new HttpEntity<String>(null, headers);
        ResponseEntity<String> responseEntity =
                template.exchange(SPECIFIC_QUOTE_URL, HttpMethod.GET, httpEntity, String.class);

        //ResponseEntity<String> responseEntity = template.getForEntity(SPECIFIC_QUOTE_URL, String.class);

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

        // Added after added Spring Security
        HttpHeaders headers = createHttpContentTypeAndAuthorizationHeaders();
        HttpEntity<String> httpEntity = new HttpEntity<String>(null, headers);
        ResponseEntity<String> responseEntity =
                template.exchange(GENERIC_QUOTES_URL, HttpMethod.GET, httpEntity, String.class);

        //ResponseEntity<String> responseEntity = template.getForEntity(GENERIC_QUOTES_URL, String.class);

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

        // Added after added Spring Security
        HttpHeaders headers = createHttpContentTypeAndAuthorizationHeaders();
        HttpEntity<String> httpEntity = new HttpEntity<String>(null, headers);
        ResponseEntity<String> responseEntity =
                template.exchange(SPECIFIC_QUIZ_URL, HttpMethod.GET, httpEntity, String.class);

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

        // Added after added Spring Security
        HttpHeaders headers = createHttpContentTypeAndAuthorizationHeaders();
        HttpEntity<String> httpEntity = new HttpEntity<String>(null, headers);
        ResponseEntity<String> responseEntity =
                template.exchange(GENERIC_QUIZZES_URL, HttpMethod.GET, httpEntity, String.class);

        //ResponseEntity<String> responseEntity = template.getForEntity(GENERIC_QUIZZES_URL, String.class);

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

    @Test
    void addNewQuoteBasicScenario() {

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

        HttpHeaders headers = createHttpContentTypeAndAuthorizationHeaders();
        HttpEntity<String> httpEntity = new HttpEntity<String>(requestBody, headers);
        ResponseEntity<String> responseEntity =
                template.exchange(GENERIC_QUOTES_URL, HttpMethod.POST, httpEntity, String.class);

        // System.out.println(responseEntity.getHeaders());
        // System.out.println(responseEntity.getBody());
        // Location:"http://localhost:55906/quizzes/Quiz1/quotes/3457322462
        assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        String locationHeader = responseEntity.getHeaders().get("Location").get(0);
        assertTrue(locationHeader.contains("/quizzes/Quiz1/quotes"));

        // Added after added Spring Security to make all test pass
        ResponseEntity<String> responseEntityDelete =
                template.exchange(locationHeader, HttpMethod.DELETE, httpEntity, String.class);
        assertTrue(responseEntityDelete.getStatusCode().is2xxSuccessful());

        // Always make sure that testing method does not have any side effects
        // Immediately delete quiz questions which were created - now all tests will pass
        // template.delete(locationHeader);
    }

    private HttpHeaders createHttpContentTypeAndAuthorizationHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("Authorization", "Basic " + performBasicAuthEncoding("admin", "password"));
        return headers;
    }

    // In bigger project this can be put to center place where all utilities are
    String performBasicAuthEncoding(String user, String password) {
        String combined = user + ":" + password;
        // Base64 Encoding => Bytes
        byte[] encodeBytes = Base64.getEncoder().encode(combined.getBytes());
        // Bytes => String
        return new String(encodeBytes);
    }
}
