package pl.com.sages.rxbookstore;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class RxBookstoreApplicationTests {
	@Autowired
	private BookService bookService;

	@Test
	void getBooksTest() {
		StepVerifier.create(bookService.getBooks())
				.assertNext(book -> assertEquals("Dziady", book.getTitle()))
				.verifyComplete();
	}

}
