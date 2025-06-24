package webly.campusSphere.backend.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.transaction.Transactional;
import webly.campusSphere.backend.Models.Marks;
import webly.campusSphere.backend.Models.BaseModel.MarksModel;

@Repository
public interface MarksRepository extends JpaRepository<Marks, Long> {

    @Modifying
    @Transactional
    @Query(value = "UPDATE marks SET student_id = ?1, course_id = ?2, semester = ?3, marks = ?4 WHERE id = ?5", nativeQuery = true)
    void updateMarksById(@PathVariable("id") Long id, @RequestBody MarksModel marksModel);

    @Transactional
    Optional<Marks> findById(Long id);
}

