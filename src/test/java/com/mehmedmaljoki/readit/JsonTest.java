package com.mehmedmaljoki.readit;

import com.jayway.jsonpath.JsonPath;
import org.json.JSONException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

public class JsonTest {

  @Test
  void testWithJSONAssert() throws JSONException {
    var result = "{\"name\": \"duke\", \"age\":\"42\", \"hobbies\": [\"java\", \"junit\", \"spring\"]}";
    assertEquals("{\"name\": \"duke\", \"age\":\"42\", \"hobbies\": [\"java\", \"junit\", \"spring\"]}", result, true);
  }

  @Test
  void testWithJsonPath() {
    var result = "{\"name\": \"duke\", \"age\":\"42\", \"hobbies\": [\"java\", \"junit\", \"spring\"]}";
    Assertions.assertEquals(3, JsonPath.parse(result).read("$.hobbies.length()", Long.class));
    Assertions.assertEquals("duke", JsonPath.parse(result).read("$.name"));
  }
}
