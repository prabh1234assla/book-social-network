package webly.bookstore.backend.Repository;

import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.transaction.Transactional;
import webly.bookstore.backend.Models.Book;
import webly.bookstore.backend.Models.BookModel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Modifying
    @Transactional
    @Query(value = "UPDATE book SET author = ?1, country = ?2, imageLink = ?3, language = ?4, link = ?5, pages = ?6, title = ?7, year = ?8, genre = ?9, labels = ?10, information = ?11, isBorrowed = ?12, borrowedAt = ?13, dueDate = ?14, returnDate = ?15 WHERE id = ?16", nativeQuery = true)
    void updateById(@PathVariable("id") int id, @RequestBody BookModel book);

}
