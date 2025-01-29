package com.mehmedmaljoki.readit.book.managment;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verifyNoInteractions;


@ExtendWith(MockitoExtension.class)
class BookSynchronizationListenerTest {


  @Mock
  private BookRepository bookRepository;

  @Mock
  private OpenLibraryApiClient openLibraryApiClient;

  @InjectMocks
  private BookSynchronizationListener cut; // class under test

  @Test
  void shouldRejectBookWhenIsbnIsMalformed() {

    var bookSynchronization = new BookSynchronization("42");
    cut.consumeBookUpdates(bookSynchronization);

    verifyNoInteractions(openLibraryApiClient, bookRepository);
  }

  @Test
  void shouldNotOverrideWhenBookAlreadyExists() {

  }

  @Test
  void shouldThrowExceptionWhenProcessingFails() {

  }

  @Test
  void shouldStoreBookWhenNewAndCorrectIsbn() {

  }

}
