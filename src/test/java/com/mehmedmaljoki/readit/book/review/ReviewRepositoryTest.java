package com.mehmedmaljoki.readit.book.review;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import javax.sql.DataSource;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

// Anything above this layer will not be injected like @Service, @Component, @Component @Controller
@DataJpaTest(properties =  {
  "spring.flyway.enabled=false", // we need this if you have problems without using a in-memory database
  "spring.jpa.hibernate.ddl-auto=create-drop" // we need this if you have problems without using a in-memory database
})
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

  @Test
  void notNull() throws SQLException {
    assertNotNull(cut);
    assertNotNull(entityManager);
    assertNotNull(testEntityManager);
    assertNotNull(dataSource);

    System.out.println(dataSource.getConnection().getMetaData().getDatabaseProductName());

  }

}
