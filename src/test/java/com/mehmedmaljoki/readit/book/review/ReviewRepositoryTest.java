package com.mehmedmaljoki.readit.book.review;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import javax.sql.DataSource;

import java.sql.SQLException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

// Anything above this layer will not be injected like @Service, @Component, @Component @Controller
@DataJpaTest(properties =  {
  "spring.flyway.enabled=false", // we need this if you have problems without using a in-memory database
  "spring.jpa.hibernate.ddl-auto=create-drop", // we need this if you have problems without using a in-memory database
  "spring.datasource.driver-class-name=com.p6spy.engine.spy.P6SpyDriver", // overwrites the driver class name
  "spring.datasource.url=jdbc:p6spy:h2:mem:testing;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE", // overwrites the url
})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ReviewRepositoryTest {

  @Autowired
  private EntityManager entityManager;

  /*
   * - is in the test package -> no access on production code
   * - useful if testing first and second level cache
   * - first level(right behind cache):
   *      tries to optimize the time for sending sql statements to the DB
   * - second level cache:
   *     tries to optimize the time for sending sql statements to the DB
   * so if your PROD code says save it does not garantuee that it is saved in the DB
   * so working with EntityManager and Hibernate can be trobeling some times and
   * the TestEntityManager is a good way to test this bc it wraps and bundels
   * more calls in one call.
   */
  @Autowired
  private TestEntityManager testEntityManager;

  @Autowired
  private ReviewRepository cut;

  @Autowired
  private DataSource dataSource;

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

    // var result = cut.save(review);
    // here you get what is persistent and we did not check if it is really saved in the DB
    // here you have only maybe an insert

    var result = testEntityManager.persistFlushFind(review);
    // here you get a insert and select statement

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
