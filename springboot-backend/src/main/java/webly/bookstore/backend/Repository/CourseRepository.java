package webly.bookstore.backend.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.transaction.Transactional;
import webly.bookstore.backend.Models.Course;
import webly.bookstore.backend.Models.BaseModel.CourseModel;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    @Modifying
    @Transactional
    @Query(value = "UPDATE course SET faculty_id = ?1, course_name = ?2 WHERE id = ?3", nativeQuery = true)
    void updateCourseById(@PathVariable("id") Long id, @RequestBody CourseModel model);

    @Modifying
    @Transactional
    @Query(value = "SELECT * FROM course WHERE faculty_id = :id", nativeQuery = true)
    List<Course> listCoursesTaughtByGivenFaculty(@PathVariable("id") Long id);

    @Transactional
    Optional<Course> findById(Long id);
}
