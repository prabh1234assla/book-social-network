package webly.bookstore.backend.Repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository; 

import webly.bookstore.backend.Book.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    
}
