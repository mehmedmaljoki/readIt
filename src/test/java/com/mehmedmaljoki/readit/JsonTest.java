package com.mehmedmaljoki.readit;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

public class JsonTest {

  @Test
  void testWithJSONAssert() throws JSONException {
    var result = "{\"name\": \"duke\", \"age\":\"42\", \"hobbies\": [\"java\", \"junit\", \"spring\"]}";
    assertEquals("{\"name\": \"duke\", \"age\":\"42\", \"hobbies\": [\"java\", \"junit\", \"spring\"]}", result, true);
  }
}
