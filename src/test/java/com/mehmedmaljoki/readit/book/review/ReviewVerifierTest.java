package com.mehmedmaljoki.readit.book.review;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static com.mehmedmaljoki.readit.book.review.RandomReviewParameterResolverExtension.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.emptyIterable;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(RandomReviewParameterResolverExtension.class)
class ReviewVerifierTest {

  private ReviewVerifier reviewVerifier;

  @BeforeEach
  void setUp() {
    reviewVerifier = new ReviewVerifier();
  }

  @AfterEach
  public void tearDown() {
  }

  @BeforeAll
  static void beforeAll() {
    System.out.println("Before all");
  }

  @AfterAll
  static void afterAll() {
    System.out.println("After all");
  }

  @Test
  void shouldFailWhenReviewContainsSwearWord() {
    //convention: should      when       result

    var review = "This book is shit";

    boolean result = reviewVerifier.doesMeetQualityStandards(review);
    assertFalse(result, "ReviewVerifier did not detect swear word");
  }

  @Test
  @DisplayName("Should fail when review contains 'lorem ipsum'")
  void testLoremIpsum() {

    var review = """
      Lorem ipsum dolor sit amet, consectetur adipiscing elit.\s
      Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.
     \s""";

    boolean result = reviewVerifier.doesMeetQualityStandards(review);

    assertFalse(false, "Review contains Lorem ipsum, which is not allowed");
  }

  @ParameterizedTest
  //  @EnumSource -> enums
  //  @MethodSource -> You write the method ro return set or something else that is then executed
  //  @ValueSource
  @CsvFileSource(resources = "/badReview.csv")
  void shouldFailWhenReviewIsOfBadQuality(String review) {
    var result = reviewVerifier.doesMeetQualityStandards(review);

    assertFalse(result, "ReviewVerifier did not detect bad review");
  }

  @ParameterizedTest
  @CsvFileSource(resources = "/goodReview.csv")
  void shouldPassWhenReviewIsOfGoodQuality(String review) {
    var result = reviewVerifier.doesMeetQualityStandards(review);

    assertTrue(result, "ReviewVerifier did not detect good review");
  }

  @RepeatedTest(5)
  void shouldFailWhenRandomReviewQualityIsBad(@RandomReview String review) {
    var result = reviewVerifier.doesMeetQualityStandards(review);

    assertFalse(result, "ReviewVerifier did not detect random bad review");
  }

  @Test
  void shouldPassWhenReviewIsGood() {
    var review = "This book is amazing. I couldn't put it down. " +
      "The characters are so well developed and the plot is so " +
      "engaging. I highly recommend it.";

    var result = reviewVerifier.doesMeetQualityStandards(review);

    assertTrue(result, "ReviewVerifier did not detect good review");
  }

  @Test
  void shouldPassWhenReviewIsGoodHamcrest() {
    var review = "This book is amazing. I couldn't put it down. " +
      "The characters are so well developed and the plot is so " +
      "engaging. I highly recommend it.";

    var result = reviewVerifier.doesMeetQualityStandards(review);

    assertThat("ReviewVerifier did not pass a good review", result, equalTo(true));
    assertThat("Lorem ipsum", endsWith("ipsum"));
    assertThat(List.of(1, 2, 3, 4, 5), hasSize(5));
    assertThat(List.of(1, 2, 3, 4, 5), anyOf(hasSize(5), emptyIterable()));
  }

  @Test
  void shouldPassWhenReviewIsGoodAssertJ() {
    var review = "This book is amazing. I couldn't put it down. " +
      "The characters are so well developed and the plot is so " +
      "engaging. I highly recommend it.";

    var result = reviewVerifier.doesMeetQualityStandards(review);

    Assertions.assertThat(result)
      .withFailMessage("ReviewVerifier did not pass a good review")
      .isEqualTo(true);

    Assertions.assertThat(List.of(1, 2, 3, 4, 5))
      .isNotEmpty()
      .hasSizeBetween(1, 10)
      .hasSize(5)
      .contains(1, 2, 3, 4, 5)
      .doesNotContain(6, 7, 8, 9, 10);
  }

  @Test
  void parallelizationOfTests() throws InterruptedException {
    Thread.sleep(1000);

    var review = "This book is amazing. I couldn't put it down. " +
      "The characters are so well developed and the plot is so " +
      "engaging. I highly recommend it.";

    var result = reviewVerifier.doesMeetQualityStandards(review);

    assertTrue(result, "ReviewVerifier did not detect good review");
  }

  @Test
  void parallelizationOfTests2() throws InterruptedException {
    Thread.sleep(1000);

    var review = "This book is amazing. I couldn't put it down. " +
      "The characters are so well developed and the plot is so " +
      "engaging. I highly recommend it.";

    var result = reviewVerifier.doesMeetQualityStandards(review);

    assertTrue(result, "ReviewVerifier did not detect good review");
  }

  @Test
  void parallelizationOfTests3() throws InterruptedException {
    Thread.sleep(1000);

    var review = "This book is amazing. I couldn't put it down. " +
      "The characters are so well developed and the plot is so " +
      "engaging. I highly recommend it.";

    var result = reviewVerifier.doesMeetQualityStandards(review);

    assertTrue(result, "ReviewVerifier did not detect good review");
  }
}
