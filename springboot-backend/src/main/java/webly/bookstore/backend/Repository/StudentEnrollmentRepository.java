package webly.bookstore.backend.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.transaction.Transactional;
import webly.bookstore.backend.Models.StudentEnrollment;
import webly.bookstore.backend.Models.BaseModel.StudentEnrollmentModel;

@Repository
public interface StudentEnrollmentRepository extends JpaRepository<StudentEnrollment, Long> {

    @Modifying
    @Transactional
    @Query(value = "UPDATE student_enrollment SET student_id = ?1, course_id = ?2, semester = ?3, is_registered = ?4 WHERE id = ?5", nativeQuery = true)
    void updateStudentEnrollmentById(@PathVariable("id") Long id, @RequestBody StudentEnrollmentModel model);

    @Transactional
    Optional<StudentEnrollment> findById(Long id);
}
