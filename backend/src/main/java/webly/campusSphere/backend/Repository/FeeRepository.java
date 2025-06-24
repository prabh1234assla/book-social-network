package webly.campusSphere.backend.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.transaction.Transactional;
import webly.campusSphere.backend.Models.Fee;
import webly.campusSphere.backend.Models.BaseModel.FeeModel;

@Repository
public interface FeeRepository extends JpaRepository<Fee, Long> {

    @Modifying
    @Transactional
    @Query(value = "UPDATE fee SET student_id = ?1, semester = ?2, fee_type = ?3, amount = ?4, is_paid = ?5 WHERE id = ?6", nativeQuery = true)
    void updateFeeById(@PathVariable("id") Long id, @RequestBody FeeModel feeModel);

    @Transactional
    @Modifying
    @Query("DELETE FROM Fee f WHERE f.student.id = :studentId")
    void deleteAllByStudentId(Long studentId);
    
    @Transactional
    Optional<Fee> findById(Long id);
}

