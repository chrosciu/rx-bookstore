package pl.com.sages.rxbookstore;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DatabaseInitializer implements CommandLineRunner {
    private final DatabaseClient databaseClient;

    @Override
    public void run(String... args) throws Exception {
        databaseClient.execute("CREATE TABLE book" +
                "(id INT AUTO_INCREMENT PRIMARY KEY," +
                "title VARCHAR(255))")
                .fetch()
                .rowsUpdated()
                .subscribe(r -> log.info("Table book created", r));

        databaseClient.insert()
                .into(Book.class)
                .using(Book.builder().title("Dziady").build())
                .then()
                .subscribe(r -> log.info("Inserted {} record sinto table book", r));

        databaseClient.select()
                .from(Book.class)
                .fetch()
                .first()
                .doOnSubscribe(s -> log.info("Books listing:"))
                .doAfterTerminate(() -> log.info("End of books listing"))
                .subscribe(r -> log.info("{}", r));

    }
}
