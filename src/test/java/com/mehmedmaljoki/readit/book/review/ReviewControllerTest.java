package com.mehmedmaljoki.readit.book.review;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.endsWith;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oauth2Login;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oidcLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.head;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(ReviewController.class)
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
      .perform(get("/api/books/reviews")
        .with(user("user").roles("USER")))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.size()", is(1)))
      .andExpect(jsonPath("$[0].bookId").value(1))
      .andExpect(jsonPath("$[0].isbn").value("42"))
      .andExpect(jsonPath("$[0].avg").value(89.3))
      .andExpect(jsonPath("$[0].ratings").value(2));
  }

  @Test
  void shouldNotReturnReviewStatisticsWhenUserIsUnauthenticated() throws Exception {
    this.mockMvc
      .perform(get("/api/books/reviews/statistics"))
      .andExpect(status().isUnauthorized());

    verifyNoInteractions(reviewService);
  }

  @Test
//  @WithMockUser(username = "user")
  void shouldReturnReviewStatisticsWhenUserIsAuthenticated() throws Exception {
    this.mockMvc
      .perform(get("/api/books/reviews/statistics")
//      .with(user("user").roles("USER")))
//      .with(oauth2Login()))
//        .with(oidcLogin())
//        .with(httpBasic("user", "password")))
        .with(jwt()))
      .andExpect(status().isOk());

    verify(reviewService).getReviewStatistics();
  }

  @Test
  void shouldCreateNewReviewForAuthenticatedUserWithValidPayload() throws Exception {
    String requestBody = """
      {
        "reviewTitle": "Great Java Book!",
        "reviewContent": "I really like this book!",
        "rating": 5
      }
      """;

    // fake the behavior of the service
    //we have to use for all of the arguments -> argumentMatchers
    when(reviewService.createBookReview(eq("42"), any(BookReviewRequest.class), eq("user"), endsWith("gmail.com")))
      .thenReturn(84L);

    this.mockMvc
      .perform(post("/api/books/{isbn}/reviews", 42)
        .contentType(MediaType.APPLICATION_JSON)
        .content(requestBody)
        .with(jwt().jwt(builder -> builder
          .claim("email", "user@gmail.com")
          .claim("preferred_username", "user"))))
        .andExpect(status().isCreated())
      .andExpect(header().exists("Location"))
      .andExpect(header().string("Location", containsString("/books/42/reviews/84")));
  }

  @Test
  void shouldRejectNewBookReviewForAuthenticatedUsersWithInvalidPayload() throws  Exception {
    // don't test everything here to overload your MockMvc -> test if ratings and so on are missing with
    // Unit tests and against your Bean rules -> Hibernate Validator
    String requestBody = """
      {
        "reviewContent": "I really like this book!",
        "rating": -1
      }
      """;

    this.mockMvc
      .perform(post("/api/books/{isbn}/reviews", 42)
        .contentType(MediaType.APPLICATION_JSON)
        .content(requestBody)
        .with(jwt().jwt(builder -> builder
          .claim("email", "user@gmail.com")
          .claim("preferred_username", "user"))))
      .andExpect(status().isBadRequest())
      .andDo(MockMvcResultHandlers.print());
  }
}
