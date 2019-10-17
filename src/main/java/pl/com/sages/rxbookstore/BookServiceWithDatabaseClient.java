package pl.com.sages.rxbookstore;

import io.r2dbc.spi.Row;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Service
//@Primary
@RequiredArgsConstructor
@Slf4j
public class BookServiceWithDatabaseClient implements BookService {
    private final DatabaseClient databaseClient;

    @Override
    public Flux<Book> getBooks() {
        return databaseClient.select().from(Book.class).fetch().all();
    }

    @Override
    public Mono<Book> createBook(Book book) {
        return databaseClient.insert().into(Book.class).using(book).map(new Function<Row, Book>() {
            @Override
            public Book apply(Row row) {
                Book book = new Book();
                book.setId((Integer)row.get("id"));
                book.setTitle((String)row.get("title"));
                return book;
            }
        }).one();
    }
}
