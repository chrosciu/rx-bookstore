package pl.com.sages.rxbookstore;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Primary
public class BookServiceWithRepository implements BookService {
    private final BookRepository bookRepository;

    @Override
    public Flux<Book> getBooks() {
        return bookRepository.findAll();
    }

    @Transactional
    @Override
    public Mono<Book> createBook(Book book) {
        return bookRepository.save(book);
    }
}
