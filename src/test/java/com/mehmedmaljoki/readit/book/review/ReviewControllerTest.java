package com.mehmedmaljoki.readit.book.review;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(ReviewController.class)
@WithMockUser(username = "user")
class ReviewControllerTest {

  @MockBean
  private ReviewService reviewService;

  @Autowired
  private MockMvc mockMvc;

  private ObjectMapper objectMapper;

  @BeforeEach
  public void beforeEach() {
    objectMapper = new ObjectMapper();
  }

  @Test
  void shouldReturnTwentyReviewsWithoutAnyOrderWhenNoParametersAreSpecified() throws Exception {

    ArrayNode result = objectMapper.createArrayNode();

    ObjectNode statistics = objectMapper.createObjectNode();
    statistics.put("bookId", 1);
    statistics.put("isbn", "42");
    statistics.put("avg", 89.3);
    statistics.put("ratings", 2);

    result.add(statistics);

    when(reviewService.getAllReviews(20, "none")).thenReturn(result);

    this.mockMvc
      .perform(get("/api/books/reviews"))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.size()", is(1)))
      .andExpect(jsonPath("$[0].bookId").value(1))
      .andExpect(jsonPath("$[0].isbn").value("42"))
      .andExpect(jsonPath("$[0].avg").value(89.3))
      .andExpect(jsonPath("$[0].ratings").value(2));
  }


  @Test
  void getReviewStatistics() {
  }

  @Test
  void createBookReview() {
  }

  @Test
  void deleteBookReview() {
  }

  @Test
  void getReviewById() {
  }
}
