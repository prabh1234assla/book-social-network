package webly.bookstore.backend.Service;

import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;

import jakarta.persistence.EntityNotFoundException;
import webly.bookstore.backend.Book.Book;
import webly.bookstore.backend.Book.BookModel;
import webly.bookstore.backend.Repository.BookRepository;

@Service
public class BookService {
    private final BookRepository repository;

    public BookService(BookRepository repository){
        this.repository = repository;
    }

    public Book create(BookModel book){
        var bookToSave = Book.builder()
                            .category(book.getCategory())
                            .description(book.getDescription())
                            .title(book.getTitle())
                            .price(book.getPrice())
                            .build();
        
        return repository.save(bookToSave);
    }

    public Book findById(long id){
        return repository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public List<Book> findAll(){
        return repository.findAll().stream().sorted(Comparator.comparing(Book::getId)).toList();
    }

    public void updateOne(long id, BookModel book){
        if(repository.findById(id).isEmpty())
            throw new EntityNotFoundException();

        Book bookToUpdate = repository.getReferenceById(id);
        bookToUpdate.setTitle(book.getTitle());
        bookToUpdate.setDescription(book.getDescription());
        bookToUpdate.setPrice(book.getPrice());
        bookToUpdate.setPrice(book.getPrice());

        repository.save(bookToUpdate);
    }

    public Book patchOne(long id, JsonPatch patch){
        var book = repository.findById(id).orElseThrow(EntityNotFoundException::new);

        var bookPatched = applyPatchToBook(patch, book);

        return repository.save(bookPatched);
    }

    public void deleteById(long id){
        repository.deleteById(id);
    }

    public void deleteAll(){
        repository.deleteAll();
    }

    private Book applyPatchToBook(JsonPatch patch, Book book){
        try{
            var objectMapper = new ObjectMapper();
            JsonNode patched = patch.apply(objectMapper.convertValue(book, JsonNode.class));
            return objectMapper.treeToValue(patched, Book.class);
        }catch( JsonPatchException | JsonProcessingException e){
            throw new RuntimeException(e);
        }
    }
}
