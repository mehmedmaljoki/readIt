package com.mehmedmaljoki.readit.book.review;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

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

  @Autowired
  private ReviewRepository cut;

  @Autowired
  private DataSource dataSource;

  @Test
  void notNull() throws SQLException {
    assertNotNull(cut);
    assertNotNull(entityManager);
    assertNotNull(dataSource);

    System.out.println(dataSource.getConnection().getMetaData().getDatabaseProductName());

  }

}
