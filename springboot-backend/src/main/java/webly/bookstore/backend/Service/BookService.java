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
import webly.bookstore.backend.Models.Book;
import webly.bookstore.backend.Models.BookModel;
import webly.bookstore.backend.Repository.BookRepository;

@Service
public class BookService {
    private final BookRepository repository;

    public BookService(BookRepository repository){
        this.repository = repository;
    }

    public Book create(BookModel book){
        Book bookToSave = Book.builder()
                            .author(book.getAuthor())
                            .country(book.getCountry())
                            .imageLink(book.getImageLink())
                            .language(book.getLanguage())
                            .link(book.getLink())
                            .pages(book.getPages())
                            .title(book.getTitle())
                            .year(book.getYear())
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
        
        bookToUpdate.setAuthor(book.getAuthor());
        bookToUpdate.setCountry(book.getCountry());
        bookToUpdate.setImageLink(book.getImageLink());
        bookToUpdate.setLanguage(book.getLanguage());
        bookToUpdate.setLink(book.getLink());
        bookToUpdate.setPages(book.getPages());
        bookToUpdate.setTitle(book.getTitle());
        bookToUpdate.setYear(book.getYear());

        repository.save(bookToUpdate);
    }

    public Book patchOne(long id, JsonPatch patch){
        Book book = repository.findById(id).orElseThrow(EntityNotFoundException::new);

        Book bookPatched = applyPatchToBook(patch, book);

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
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode patched = patch.apply(objectMapper.convertValue(book, JsonNode.class));
            return objectMapper.treeToValue(patched, Book.class);
        }catch( JsonPatchException | JsonProcessingException e){
            throw new RuntimeException(e);
        }
    }
}
