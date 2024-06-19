package webly.bookstore.backend.Book;

import webly.bookstore.backend.Utils.BaseEntity;

import java.math.BigDecimal;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

enum Category{
    NA,
    FICTION,
    HORROR,
    POETRY,
    SCI_FI,
    FANTASY,
}

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Book")
public class Book extends BaseEntity {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column(name = "title", nullable = false)
    @NotNull(message = "Title must be specified.")
    private String title;

    @Column(name = "description")
    private String description = "NA";

    @Column(name = "category")
    private Category category = Category.NA;

    @Min(0)
    @Column(name = "price", columnDefinition = "decimal (10, 2)")
    private BigDecimal price;
    
}
