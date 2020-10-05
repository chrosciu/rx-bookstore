package pl.com.sages.rxbookstore;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface BookRepository extends ReactiveCrudRepository<Book, Integer> {
}
