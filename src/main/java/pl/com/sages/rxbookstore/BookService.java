package pl.com.sages.rxbookstore;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BookService {
    Flux<Book> getBooks();
    Mono<Book> createBook(Book book);
}
