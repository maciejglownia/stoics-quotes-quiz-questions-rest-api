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

### Screenshots

![Quizzes](src/main/resources/drawable/quizzes_response.png)

### Dear Visitor

If you see an opportunity to improve my code do not hesitate to contact me:
maciej.k.glownia@gmail.com. If you want to copy it and develop with your own idea, take it and enjoy
your learning path.