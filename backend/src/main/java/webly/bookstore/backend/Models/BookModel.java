package webly.bookstore.backend.Models;

import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Component;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

@Getter
@Component
public class BookModel {

    private long id;
    private String author;
    private String country;
    private String imageLink;
    private String language;
    private String link;
    private Integer pages;
    private String title;
    private Integer year;

}