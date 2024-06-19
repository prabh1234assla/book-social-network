package webly.bookstore.backend.Repository;

import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import webly.bookstore.backend.Book.Book;
import webly.bookstore.backend.Book.BookModel;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Modifying
    @Transactional
    @Query(value = "UPDATE book SET title = ?1, description = ?2, category = ?3, price = ?4 WHERE id = ?5", nativeQuery = true)
    void updateById(@PathVariable("id") int id, @RequestBody BookModel book);

}
