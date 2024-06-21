package webly.bookstore.backend.Models;

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
import webly.bookstore.backend.Models.Utils.BaseEntity;

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

    @Column(name = "author", nullable = false)
    @NotNull(message = "Author must be specified.")
    private String author;

    @Column(name = "country")
    private String country;

    @Column(name = "imageLink")
    private String imageLink;

    @Column(name = "language")
    private String language;

    @Column(name = "link")
    private String link;

    @Column(name = "pages")
    private Integer pages;

    @Column(name = "title", nullable = false)
    @NotNull(message = "Title must be specified.")
    private String title;

    @Column(name = "year")
    private Integer year;
    
}
