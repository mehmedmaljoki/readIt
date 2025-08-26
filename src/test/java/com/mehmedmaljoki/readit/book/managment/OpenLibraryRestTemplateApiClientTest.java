package com.mehmedmaljoki.readit.book.managment;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.client.response.MockRestResponseCreators;
import org.springframework.web.client.HttpServerErrorException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RestClientTest(OpenLibraryRestTemplateApiClient.class)
class OpenLibraryRestTemplateApiClientTest {

  private static final String ISBN = "9780596004651";

  @Autowired
  private OpenLibraryRestTemplateApiClient cut;

  @Autowired
  private MockRestServiceServer mockRestServiceServer;

  @Test
  void shouldInjectBeans() {
    assertNotNull(cut);
    assertNotNull(mockRestServiceServer);
  }

  @Test
  void shouldReturnBookWhenResultIsSuccess() {
    this.mockRestServiceServer
      .expect(requestTo(Matchers.containsString(ISBN))) // this is more genertic than bellow
//      .expect(MockRestRequestMatchers.requestTo("/api/books?jscmd=data&format=json&bibkeys=" + ISBN))
      .andRespond(withSuccess(new ClassPathResource("/stubs/openlibrary/success-" + ISBN + ".json"),
        MediaType.APPLICATION_JSON));

    var book = cut.fetchMetadataForBook(ISBN);

    assertNotNull(book);
    assertEquals(ISBN, book.getIsbn());
    assertEquals("Head first Java", book.getTitle());
    assertEquals("https://covers.openlibrary.org/b/id/388761-S.jpg", book.getThumbnailUrl());
    assertEquals("Kathy Sierra", book.getAuthor());
    assertEquals("O'Reilly", book.getPublisher());
    assertEquals(619, book.getPages());

    assertNull(book.getId()); // bc we create our own latter to save it in the db
  }

  @Test
  void shouldReturnBookWhenResultIsSuccessButLackingAllInformation() {
    String response =
      """
         {
          "9780596004651": {
            "publishers": [
              {
                "name": "O'Reilly"
              }
            ],
            "title": "Head second Java",
            "authors": [
              {
                "url": "https://openlibrary.org/authors/OL1400543A/Kathy_Sierra",
                "name": "Kathy Sierra"
              }
            ],
            "number_of_pages": 42,
            "cover": {
              "small": "https://covers.openlibrary.org/b/id/388761-S.jpg",
              "large": "https://covers.openlibrary.org/b/id/388761-L.jpg",
              "medium": "https://covers.openlibrary.org/b/id/388761-M.jpg"
            }
           }
         }
        """;

    this.mockRestServiceServer
      .expect(requestTo(Matchers.containsString("/api/books")))
      .andRespond(withSuccess(response, MediaType.APPLICATION_JSON));

//    this.mockRestServiceServer
//      .expect(requestTo(Matchers.containsString("/mehi/42")))
//      .andRespond(withSuccess(response, MediaType.APPLICATION_JSON));
//    all mocks are verified with verify through the test

    var book = cut.fetchMetadataForBook(ISBN);


    assertNotNull(book);
    assertEquals(ISBN, book.getIsbn());
    assertEquals("Head second Java", book.getTitle());
    assertEquals("https://covers.openlibrary.org/b/id/388761-S.jpg", book.getThumbnailUrl());
    assertEquals("Kathy Sierra", book.getAuthor());
    assertEquals("O'Reilly", book.getPublisher());
    assertEquals("n.A", book.getDescription());
    assertEquals("n.A", book.getGenre());
    assertEquals(42, book.getPages());

    assertNull(book.getId()); // bc we create our own latter to save it in the db

    // this.mockRestServiceServer.verify();
  }

  @Test
  void shouldPropagateExceptionWhenRemoteSystemIsDown() {
    assertThrows(
      HttpServerErrorException.class,
      () -> {
        this.mockRestServiceServer
          .expect(requestTo("/api/books?jscmd=data&format=json&bibkeys=" + ISBN))
          .andRespond(MockRestResponseCreators.withServerError());

        cut.fetchMetadataForBook(ISBN);
      });
  }

  @Test
  void shouldContainCorrectHeadersWhenRemoteSystemIsInvoked() {

    this.mockRestServiceServer
      .expect(requestTo("/api/books?jscmd=data&format=json&bibkeys=" + ISBN))
      .andExpect(MockRestRequestMatchers.header("X-Custom-Auth", "Duke42"))
      .andExpect(MockRestRequestMatchers.header("X-Customer-Id", "42"))
      .andRespond(
        withSuccess(
          new ClassPathResource("/stubs/openlibrary/success-" + ISBN + ".json"),
          MediaType.APPLICATION_JSON));

    Book result = cut.fetchMetadataForBook(ISBN);

    assertNotNull(result);
  }
}
