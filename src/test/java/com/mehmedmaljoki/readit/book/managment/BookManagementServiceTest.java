package com.mehmedmaljoki.readit.book.managment;

import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
class BookManagementServiceTest {

  @MockBean
  private BookManagementService bookManagementService;

  @Autowired
  private MockMvc mockMvc;

  @Test
  @WithMockUser(username = "user")
  void shouldGetEmptyArrayWhenNoBooksExists() throws Exception {
    var mvcResult = this.mockMvc
      .perform(get("/api/books")
        .header(HttpHeaders.ACCEPT, "application/json", MediaType.APPLICATION_JSON))
      .andExpect(status().is(200))
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$.size()", is(0)))
      .andDo(print())
      .andReturn();
  }

  @Test
  @WithMockUser(username = "user")
  void shouldNotReturnXML() throws Exception { // dont write such tests bc its endless
    this.mockMvc
      .perform(get("/api/books")
        .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_XML))
      .andExpect(status().isNotAcceptable())
      .andDo(print());
  }

  @Test
  @WithMockUser(username = "user")
  void shouldGetBooksWhenServiceReturnsBooks() throws Exception {

    var bookOne = createBook(
      1L,
      "1",
      "Effective Java",
      "Joshua Bloch",
      "Best practices for Java programming",
      "Programming",
      416L,
      "Addison-Wesley",
      "https://example.com/thumbnail1.jpg");

    var bookTwo = createBook(
      2L,
      "2",
      "Clean Code",
      "Robert C. Martin",
      "A Handbook of Agile Software Craftsmanship",
      "Programming",
      464L,
      "Prentice Hall",
      "https://example.com/thumbnail2.jpg");

    when(bookManagementService.getAllBooks()).thenReturn(List.of(bookOne, bookTwo));

    this.mockMvc
      .perform(get("/api/books")
        .header(HttpHeaders.ACCEPT, "application/json", MediaType.APPLICATION_JSON))
      .andExpect(status().is(200))
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$.size()", is(2)))

      .andExpect(jsonPath("$[0].id").doesNotExist())
      .andExpect(jsonPath("$[0].isbn", is("1")))
      .andExpect(jsonPath("$[0].title", is("Effective Java")))
      .andExpect(jsonPath("$[0].author", is("Joshua Bloch")))
      .andExpect(jsonPath("$[0].description", is("Best practices for Java programming")))
      .andExpect(jsonPath("$[0].genre", is("Programming")))
      .andExpect(jsonPath("$[0].pages", is(416)))
      .andExpect(jsonPath("$[0].publisher", is("Addison-Wesley")))
      .andExpect(jsonPath("$[0].thumbnailUrl", is("https://example.com/thumbnail1.jpg")))

      .andExpect(jsonPath("$[1].id").doesNotExist())
      .andExpect(jsonPath("$[1].isbn", is("2")))
      .andExpect(jsonPath("$[1].title", is("Clean Code")))
      .andExpect(jsonPath("$[1].author", is("Robert C. Martin")))
      .andExpect(jsonPath("$[1].description", is("A Handbook of Agile Software Craftsmanship")))
      .andExpect(jsonPath("$[1].genre", is("Programming")))
      .andExpect(jsonPath("$[1].pages", is(464)))
      .andExpect(jsonPath("$[1].publisher", is("Prentice Hall")))
      .andExpect(jsonPath("$[1].thumbnailUrl", is("https://example.com/thumbnail2.jpg")));
  }

  private Book createBook(
    Long id,
    String isbn,
    String title,
    String author,
    String description,
    String genre,
    Long pages,
    String publisher,
    String thumbnailUrl) {

    var book = new Book();
    book.setId(id);
    book.setIsbn(isbn);
    book.setTitle(title);
    book.setAuthor(author);
    book.setDescription(description);
    book.setGenre(genre);
    book.setPages(pages);
    book.setPublisher(publisher);
    book.setThumbnailUrl(thumbnailUrl);

    return book;
  }


}
