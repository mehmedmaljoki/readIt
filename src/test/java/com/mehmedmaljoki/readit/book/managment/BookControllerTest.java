package com.mehmedmaljoki.readit.book.managment;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(BookController.class) // only bootstrap the one controller bc you would need all the other stuff for other controllers as well
//@Import(BookManagementService.class) // why? because the BookController needs the BookManagementService
class BookControllerTest {

  /*
    Yes, there’s a difference.
    With @Autowired we inject a Spring bean from the TestContext to our class to use it.

    With @Import we add a specific class(es) to our Spring TestContext configuration.
    For example this class, which may be a @Configuration class that contains multiple
    bean definition (@Bean) which we can then inject into our test using @Autowired.
   */

  @MockBean
  private BookManagementService bookManagementService;




  @Test
  void shouldStart() {
  }

}
