package com.mehmedmaljoki.readit.book.managment;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(BookController.class) // only bootstrap the one controller bc you would need all the other stuff for other controllers as well
class BookControllerTest {

  @MockBean
  private BookManagementService bookManagementService;



  @Test
  void shouldStart() {
  }

}
