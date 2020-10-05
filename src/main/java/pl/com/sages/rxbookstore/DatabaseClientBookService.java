package pl.com.sages.rxbookstore;

import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@ConditionalOnMissingBean(RepositoryBookService.class)
@Slf4j
public class DatabaseClientBookService implements BookService {
    private final DatabaseClient databaseClient;

    @Override
    public Flux<Book> getBooks() {
        return databaseClient.select().from(Book.class).fetch().all();
    }

    @Override
    @Transactional
    public Mono<Book> createBook(Book book) {
        return databaseClient.insert().into(Book.class).using(book)
                .map(this::mapRow2Book)
                .one();
    }

    private Book mapRow2Book(Row row, RowMetadata rowMetadata) {
        //WARNING: only id column and no others will be returned here!
        return Book.builder().id((Integer)row.get("id")).build();
    }
}
