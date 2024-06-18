package webly.bookstore.backend.Book;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import lombok.Getter;

@Getter
@Component
public class BookModel {
    private String title;
    private String description;
    private Category category;
    private BigDecimal price;
}