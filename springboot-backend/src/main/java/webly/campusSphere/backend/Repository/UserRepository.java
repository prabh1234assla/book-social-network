package webly.campusSphere.backend.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.transaction.Transactional;
import webly.campusSphere.backend.Models.Course;
import webly.campusSphere.backend.Models.User;
import webly.campusSphere.backend.Models.BaseModel.UserModel;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    @Modifying
    @Transactional
    @Query(value = "UPDATE coursesTaught SET role = ?1, username = ?2, email = ?3, password = ?4, enrollments = ?5, fees = ?6, marks = ?7 WHERE id = ?8", nativeQuery = true)
    void updateCoursesTaughtById(@PathVariable("id") Long id, @RequestBody UserModel user);


    @Modifying
    @Transactional
    @Query(value = "UPDATE enrollments SET role = ?1, username = ?2, email = ?3, password = ?4, coursesTaught = ?5, fees = ?6, marks = ?7 WHERE id = ?8", nativeQuery = true)
    void updateEnrollmentsById(@PathVariable("id") Long id, @RequestBody UserModel user);


    @Modifying
    @Transactional
    @Query(value = "UPDATE fees SET role = ?1, username = ?2, email = ?3, password = ?4, coursesTaught = ?5, enrollments = ?6, marks = ?7 WHERE id = ?8", nativeQuery = true)
    void updateFeesById(@PathVariable("id") Long id, @RequestBody UserModel user);


    @Modifying
    @Transactional
    @Query(value = "UPDATE marks SET role = ?1, username = ?2, email = ?3, password = ?4, coursesTaught = ?5, enrollments = ?6, fees = ?7 WHERE id = ?8", nativeQuery = true)
    void updateMarksById(@PathVariable("id") Long id, @RequestBody UserModel user);


    @Transactional
    Optional<User> findByUsername(String email);

    @Modifying
    @Transactional
    @Query(value = "SELECT  FROM _user WHERE id = :id", nativeQuery = true)
    List<Course> listCoursesStudiedByStudents(@PathVariable("id") Long id);
}
