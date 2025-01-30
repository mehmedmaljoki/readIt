package com.mehmedmaljoki.readit.book.managment;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class BookSynchronizationListenerTest {

  private final static String VALID_ISBN = "1234567891234";

  @Mock
  private BookRepository bookRepository;

  @Mock
  private OpenLibraryApiClient openLibraryApiClient;

  @InjectMocks
  private BookSynchronizationListener cut; // class under test

  @Captor
  private ArgumentCaptor<Book> bookCaptor;

  @Test
  void shouldRejectBookWhenIsbnIsMalformed() {

    var bookSynchronization = new BookSynchronization("42");
    cut.consumeBookUpdates(bookSynchronization);

    verifyNoInteractions(openLibraryApiClient, bookRepository);
  }

  @Test
  void shouldNotOverrideWhenBookAlreadyExists() {
    var bookSynchronization = new BookSynchronization(VALID_ISBN);
    when(bookRepository.findByIsbn(VALID_ISBN)).thenReturn(new Book());

    cut.consumeBookUpdates(bookSynchronization);
    verifyNoInteractions(openLibraryApiClient);
    verify(bookRepository, times(0)).save(any());
  }

  @Test
  void shouldThrowExceptionWhenProcessingFails() {
    var bookSynchronization = new BookSynchronization(VALID_ISBN);
    when(bookRepository.findByIsbn(VALID_ISBN)).thenReturn(null);

    when(openLibraryApiClient.fetchMetadataForBook(VALID_ISBN))
      .thenThrow(new RuntimeException("Network timeout"));

    assertThrows(RuntimeException.class, () -> cut.consumeBookUpdates(bookSynchronization));
  }

  @Test
  void shouldStoreBookWhenNewAndCorrectIsbn() {
    var bookSynchronization = new BookSynchronization(VALID_ISBN);
    when(bookRepository.findByIsbn(VALID_ISBN)).thenReturn(null);

    var requestedBook = new Book();
    requestedBook.setTitle("Java book");
    requestedBook.setIsbn(VALID_ISBN);

    when(openLibraryApiClient.fetchMetadataForBook(VALID_ISBN)).thenReturn(requestedBook);
    when(bookRepository.save(any())).then(invocation -> {
      var book = invocation.getArgument(0, Book.class);
      book.setId(1L);
      return book;
    });

    cut.consumeBookUpdates(bookSynchronization);
  }

  @Test
  void shouldStoreBookWhenNewAndCorrectIsbnAnotherApproach() {
    // helpful for void methods to ensure that they are called
    var bookSynchronization = new BookSynchronization(VALID_ISBN);
    when(bookRepository.findByIsbn(VALID_ISBN)).thenReturn(null);

    var requestedBook = new Book();
    requestedBook.setTitle("Java book");
    requestedBook.setIsbn(VALID_ISBN);

    when(openLibraryApiClient.fetchMetadataForBook(VALID_ISBN)).thenReturn(requestedBook);
    when(bookRepository.save(any())).then(invocation -> {
      var book = invocation.getArgument(0, Book.class);
      book.setId(1L);
      return book;
    });

    cut.consumeBookUpdates(bookSynchronization);

    verify(bookRepository).save(bookCaptor.capture());

    var methodArgument = bookCaptor.getValue();
    assertEquals("Java book", methodArgument.getTitle());
    assertEquals(VALID_ISBN, methodArgument.getIsbn());
  }

}
