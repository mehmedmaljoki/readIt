package com.mehmedmaljoki.readit.book.managment;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;


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
    var bookSynchronization = new BookSynchronization("1234567891234");
    when(bookRepository.findByIsbn("1234567891234")).thenReturn(new Book());

    cut.consumeBookUpdates(bookSynchronization);
    verifyNoInteractions(openLibraryApiClient);
    verify(bookRepository, times(0)).save(any());
  }

  @Test
  void shouldThrowExceptionWhenProcessingFails() {

  }

  @Test
  void shouldStoreBookWhenNewAndCorrectIsbn() {

  }

}
