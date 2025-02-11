package com.mehmedmaljoki.readit.book.review;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
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
class ReviewRepositoryNoInMemoryTest {

  @Container
  private static PostgreSQLContainer container = new PostgreSQLContainer("postgres:13")
    .withDatabaseName("test")
    .withUsername("mehmed")
    .withPassword("mehmed");
//  .reuse(true); // if the starting of the container is slow

  @DynamicPropertySource
  private static void properties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", container::getJdbcUrl);
    registry.add("spring.datasource.username", container::getUsername);
    registry.add("spring.datasource.password", container::getPassword);
  }

  @Autowired
  private EntityManager entityManager;

  @Autowired
  private TestEntityManager testEntityManager;

  @Autowired
  private ReviewRepository cut;

  @Autowired
  private DataSource dataSource;

//  @BeforeAll
//  static void beforeAll() {
//    container.start();
//  }

  @BeforeEach
  void beforeEach() {
    assertEquals(0, cut.count());
    // to be clean on every test
    // transactional support
  }

  @Test
  void notNull() throws SQLException {
    assertNotNull(cut);
    assertNotNull(entityManager);
    assertNotNull(testEntityManager);
    assertNotNull(dataSource);

    System.out.println(dataSource.getConnection().getMetaData().getDatabaseProductName());

    var review = new Review();
    review.setTitle("Review 101");
    review.setContent("This is a review");
    review.setCreatedAt(LocalDateTime.now());
    review.setRating(5);
    review.setBook(null);
    review.setUser(null);

    var result = testEntityManager.persistFlushFind(review);

    assertNotNull(result.getId());
  }

  @Test
  void transactionalSupportTest() {
    var review = new Review();
    review.setTitle("Review 101");
    review.setContent("This is a review");
    review.setCreatedAt(LocalDateTime.now());
    review.setRating(5);
    review.setBook(null);
    review.setUser(null);

    var result = cut.save(review);


  }
}
