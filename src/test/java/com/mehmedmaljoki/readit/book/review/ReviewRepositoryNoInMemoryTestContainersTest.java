package com.mehmedmaljoki.readit.book.review;

import com.mehmedmaljoki.readit.book.managment.Book;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ReviewRepositoryNoInMemoryTestContainersTest {

  @Container
  private static PostgreSQLContainer container = new PostgreSQLContainer("postgres:13")
    .withDatabaseName("test")
    .withUsername("mehmed")
    .withPassword("mehmed");

  @DynamicPropertySource
  private static void properties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", container::getJdbcUrl);
    registry.add("spring.datasource.username", container::getUsername);
    registry.add("spring.datasource.password", container::getPassword);
  }

  @Autowired
  private ReviewRepository cut;

//  @Test
//  @Sql(scripts = "/scripts/INIT_REVIEW_EACH_BOOK.sql") // hard to mentain bc DB schema changes and the script has to be updated
//  void shouldGetTwoReviewStaticsWhenDatabaseContainsTwoBooksWithReview() {
//
//    assertEquals(2, cut.count());
//    assertEquals(2, cut.getReviewStatistics().size());
//
//    cut.getReviewStatistics().forEach(reviewStatistic -> {
//      assertNotNull(reviewStatistic.getId());
//      assertNotNull(reviewStatistic.getAvg());
//      assertNotNull(reviewStatistic.getIsbn());
//    });
//
//  }


}
