# stoics-quotes-quiz-questions-rest-api

### Goal

1. Create REST API application which has Quizzes available to the client.
2. Learn: Java, Spring Boot 3

### Disclaimer

Application does not contain real quotes. It shows only mechanism. And it is part of my learning process.

### URLs

#### GET

```
http://localhost:8080/quizzes
```

```
http://localhost:8080/quizzes/Quiz1
```

```
http://localhost:8080/quizzes/Quiz1/quotes
```

```
http://localhost:8080/quizzes/Quiz1/quotes/Quote1
```

#### Response

```
[
    {
        "id": "Quiz1",
        "title": "Stoics' Quotes Quiz",
        "description": "Quiz contains 4 quotes. Each quote is also a question. There are four Stoics: Marcus Aurelius, Epictetus, Seneca, and Zeno. Only one man is an author of specific quote.",
        "quotes": [
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
            },
            {
                "id": "Quote2",
                "quote": "Quote of Epictetus",
                "options": [
                    "Marcus Aurelius",
                    "Epictetus",
                    "Seneca",
                    "Zeno"
                ],
                "correctAnswer": "Epictetus"
            },
            {
                "id": "Quote3",
                "quote": "Quote of Seneca",
                "options": [
                    "Marcus Aurelius",
                    "Epictetus",
                    "Seneca",
                    "Zeno"
                ],
                "correctAnswer": "Seneca"
            },
            {
                "id": "Quote4",
                "quote": "Quote of Zeno",
                "options": [
                    "Marcus Aurelius",
                    "Epictetus",
                    "Seneca",
                    "Zeno"
                ],
                "correctAnswer": "Zeno"
            }
        ]
    }
]
```

### Request Body

Tested
with: [Talend API Tester](https://chrome.google.com/webstore/detail/talend-api-tester-free-ed/aejoelaoggembcahagimdiliamlcdmfm)
available for Chrome.

```
{
    "id": "id",
    "quote": "Your favourite quote",
    "options": [
        "Marcus Aurelius",
        "Epictetus",
        "Seneca",
        "Zeno"
    ],
    "correctAnswer": "Zeno"
}
```

### Screenshots

- Quizzes

![Quizzes](src/main/resources/drawable/quizzes_response.png)

- GET Quote1

![GetQuote1](src/main/resources/drawable/GET_quote1.png)

- POST status=201 (CREATED)

![PostStatus201](src/main/resources/drawable/POST_quote_status_201.png)

- Header Location

![PostStatus201](src/main/resources/drawable/POST_header_location.png)

- DELETE status=204 (NO CONTENT)

![NoContentStatus204](src/main/resources/drawable/DELETE_quote_status_204.png)

- PUT Quote2

![NoContentStatus204](src/main/resources/drawable/PUT_quote2.png)

- Integration Tests GET and POST
 
![IntegrationTestsGetPost](src/main/resources/drawable/integration_tests_passed.png)

- Unit Tests (Mock) GET and POST
 
![UnitTestsMockGetPost](src/main/resources/drawable/unit_mock_test_passed.png)

- Spring Security

![SpringSecurity1](src/main/resources/drawable/spring_security_before_sign_in.png)

![SpringSecurity2](src/main/resources/drawable/spring_security_sign_in.png)

![SpringSecurity3](src/main/resources/drawable/spring_security_after_sign_in.png)

- Spring Security Testing

![SpringSecurityTesting](src/main/resources/drawable/failed_tests_401_after_adding_spring_security.png)

![SpringSecurityTesting](src/main/resources/drawable/pass_tests_afterdding_spring_security_after_refactoring.png)

### Dear Visitor

If you see an opportunity to improve my code do not hesitate to contact me:
maciej.k.glownia@gmail.com. If you want to copy it and develop with your own idea, take it and enjoy
your learning path.
