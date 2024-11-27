package com.mehmedmaljoki.readit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ReadItApplication {

  private static final Logger LOG = LoggerFactory.getLogger(ReadItApplication.class);

  public static void main(String[] args) {
    SpringApplication.run(ReadItApplication.class, args);
  }

  @Bean
  CommandLineRunner welcomeMessage() {
    return runner -> {
      String welcomeMessage =
        """
          \n
          Welcome to the ReadIt application.
          """;

      LOG.info(welcomeMessage);
    };
  }
}
