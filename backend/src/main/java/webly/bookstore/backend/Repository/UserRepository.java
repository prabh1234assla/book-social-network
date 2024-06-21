package webly.bookstore.backend.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.transaction.Transactional;
import webly.bookstore.backend.Models.User;
import webly.bookstore.backend.Models.UserModel;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    @Modifying
    @Transactional
    @Query(value = "UPDATE book SET role = ?1, username = ?2, email = ?3, password = ?4 WHERE id = ?5", nativeQuery = true)
    void updateById(@PathVariable("id") int id, @RequestBody UserModel user);

    Optional<User> findByEmail(String email);
}
