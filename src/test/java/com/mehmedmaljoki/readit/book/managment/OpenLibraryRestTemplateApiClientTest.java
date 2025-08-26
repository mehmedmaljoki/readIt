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

import static org.junit.jupiter.api.Assertions.*;

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
      .expect(MockRestRequestMatchers.requestTo(Matchers.containsString(ISBN))) // this is more genertic than bellow
//      .expect(MockRestRequestMatchers.requestTo("/api/books?jscmd=data&format=json&bibkeys=" + ISBN))
      .andRespond(MockRestResponseCreators.withSuccess(new ClassPathResource("/stubs/openlibrary/success-" + ISBN + ".json"),
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
}
