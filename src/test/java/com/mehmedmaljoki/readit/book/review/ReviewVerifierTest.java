package com.mehmedmaljoki.readit.book.review;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
    System.out.println("Testing a review");

    boolean result = reviewVerifier.doesMeetQualityStandards(review);
    assertFalse(result, "ReviewVerifier did not detect swear word");
  }

  @Test
  @DisplayName("Should fail when review contains 'lorem ipsum'")
  void testLoremIpsum() {
  }

}
