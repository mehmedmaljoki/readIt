package com.mehmedmaljoki.readit.book.managment;

import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

  @Autowired
  private MockMvc mockMvc;


  @Test
  @WithMockUser(username = "user")
  void shouldGetEmptyArrayWhenNoBooksExists() throws Exception {
    var mvcResult = this.mockMvc
      .perform(get("/api/books")
        .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$.size()", is(0)))
      .andDo(print())
      .andReturn();
  }

  @Test
  @WithMockUser(username = "user")
  void shouldNotReturnXML() throws Exception {
    this.mockMvc
      .perform(get("/api/books")
        .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_XML))
      .andExpect(status().isNotAcceptable());
  }

}
