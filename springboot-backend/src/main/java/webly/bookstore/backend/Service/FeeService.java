package webly.bookstore.backend.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;

import jakarta.persistence.EntityNotFoundException;
import webly.bookstore.backend.Models.Fee;
import webly.bookstore.backend.Models.BaseModel.FeeModel;
import webly.bookstore.backend.Models.User;
import webly.bookstore.backend.Repository.FeeRepository;
import webly.bookstore.backend.Repository.UserRepository;

@Service
public class FeeService {
    private final FeeRepository feeRepository;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    public FeeService(FeeRepository feeRepository, UserRepository userRepository, ObjectMapper objectMapper) {
        this.feeRepository = feeRepository;
        this.userRepository = userRepository;
        this.objectMapper = objectMapper;
    }

    @Transactional
    public Fee create(FeeModel feeModel) {
        User student = userRepository.findById(feeModel.getStudentId())
                .orElseThrow(() -> new EntityNotFoundException("Student not found with id: " + feeModel.getStudentId()));

        Fee feeToSave = Fee.builder()
                .semester(feeModel.getSemester())
                .feeType(feeModel.getFeeType())
                .amount(feeModel.getAmount())
                .isPaid(feeModel.getIsPaid() != null ? feeModel.getIsPaid() : false)
                .student(student)
                .build();

        return feeRepository.save(feeToSave);
    }

    public Fee findById(long id) {
        return feeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Fee not found with id: " + id));
    }

    public List<Fee> findAll() {
        return feeRepository.findAll().stream()
                .sorted(Comparator.comparing(Fee::getId))
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateFeeById(long id, FeeModel feeModel) {
        Fee existingFee = feeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Fee not found with id: " + id));

        User student = userRepository.findById(feeModel.getStudentId())
                .orElseThrow(() -> new EntityNotFoundException("Student not found with id: " + feeModel.getStudentId()));

        existingFee.setSemester(feeModel.getSemester());
        existingFee.setFeeType(feeModel.getFeeType());
        existingFee.setAmount(feeModel.getAmount());
        existingFee.setPaid(feeModel.getIsPaid() != null ? feeModel.getIsPaid() : existingFee.isPaid());
        existingFee.setStudent(student);

        feeRepository.save(existingFee);
    }

    @Transactional
    public Fee patchOne(long id, JsonPatch patch) {
        Fee fee = feeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Fee not found with id: " + id));

        Fee feePatched = applyPatchToFee(patch, fee);
        return feeRepository.save(feePatched);
    }

    @Transactional
    public void deleteById(long id) {
        if (!feeRepository.existsById(id)) {
            throw new EntityNotFoundException("Fee not found with id: " + id);
        }
        feeRepository.deleteById(id);
    }

    @Transactional
    public void deleteAll() {
        feeRepository.deleteAll();
    }

    public void deleteAllByStudentId(Long studentId){
        feeRepository.deleteAllByStudentId(studentId);
    };

    private Fee applyPatchToFee(JsonPatch patch, Fee fee) {
        try {
            JsonNode patched = patch.apply(objectMapper.convertValue(fee, JsonNode.class));
            return objectMapper.treeToValue(patched, Fee.class);
        } catch (JsonPatchException | JsonProcessingException e) {
            throw new RuntimeException("Failed to apply JSON patch: " + e.getMessage(), e);
        }
    }
}