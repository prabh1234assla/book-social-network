package webly.bookstore.backend.Models;

import java.util.Set;

import org.springframework.stereotype.Component;

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
    private Set<User> users;

}