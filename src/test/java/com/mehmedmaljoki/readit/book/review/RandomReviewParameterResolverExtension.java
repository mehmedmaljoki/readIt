package com.mehmedmaljoki.readit.book.review;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.junit.runners.Parameterized;
import org.w3c.dom.stylesheets.LinkStyle;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

public class RandomReviewParameterResolverExtension implements ParameterResolver {

  private static final List<String> badReviews = List.of(
    "This book is absolute shit. I couldn't get past the first chapter.",
    "The plot was shit, and the characters were even worse.",
    "I wasted my money on this shit book. Don't bother reading it.",
    "The writing style is shit, and the story makes no sense.",
    "This book is a piece of shit. I regret buying it."
  );

  @Retention(RUNTIME)
  @Target(PARAMETER)
  public @interface RandomReview {

  }

  @Override
  public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
    return parameterContext.isAnnotated(RandomReview.class);
  }

  @Override
  public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
    return badReviews.get(ThreadLocalRandom.current().nextInt(0, badReviews.size()));
  }
}
