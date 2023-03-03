package com.glownia.maciej.stoicsquotesquizquestionsrestapi.quiz;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

public class JsonAssertTest {

    // It helps a lot.
    // Ignores white spaces.
    // Points where text does not match.
@Test
    void jsonAssertBasic() throws JSONException {

    String expectedResponse =
            """
            {"id":"Quote1",
            "quote":"Quote of Marcus Aurelius",
            "correctAnswer":"Marcus Aurelius"}
            """;

    String actualResponse =
            """
            {
            "id":"Quote1",
            "quote":"Quote of Marcus Aurelius",
            "options":["Marcus Aurelius","Epictetus","Seneca","Zeno"],
            "correctAnswer":"Marcus Aurelius"}
            """;

//    JSONAssert.assertEquals(expectedResponse,actualResponse,true); // need to match all text
    JSONAssert.assertEquals(expectedResponse,actualResponse,false); // no need to match all text
}
}
