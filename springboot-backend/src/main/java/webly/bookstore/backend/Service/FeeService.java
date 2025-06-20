package webly.bookstore.backend.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.tomcat.util.openssl.pem_password_cb;
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
import webly.bookstore.backend.DTOs.UserResponseDTO;
import webly.bookstore.backend.Models.Fee;
import webly.bookstore.backend.Models.BaseModel.FeeModel;
import webly.bookstore.backend.Models.User;
import webly.bookstore.backend.Repository.FeeRepository;
import webly.bookstore.backend.Repository.UserRepository;

@Service
public class FeeService {
    private final FeeRepository feeRepository;
    private final UserRepository userRepository;
    // private final ObjectMapper objectMapper;

    public FeeService(FeeRepository feeRepository, UserRepository userRepository, ObjectMapper objectMapper) {
        this.feeRepository = feeRepository;
        this.userRepository = userRepository;
        // this.objectMapper = objectMapper;
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

    public FeeModel findById(long id) {
        Fee fee = feeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Fee not found with id: " + id));

        FeeModel dto = new FeeModel();

        dto.setId(fee.getId());
        dto.setStudentId(fee.getStudent().getId());
        dto.setAmount(fee.getAmount());
        dto.setSemester(fee.getSemester());
        dto.setIsPaid(fee.isPaid());

        return dto;
    }

    public List<FeeModel> findAll() {

        System.out.println("ndndnmd,m dm dsm dsm");

        return feeRepository.findAll().stream()
                .sorted(Comparator.comparing(Fee::getId))
                .map(fee -> {
                    FeeModel dto = new FeeModel();

                    dto.setId(fee.getId());
                    dto.setStudentId(fee.getStudent().getId());
                    dto.setAmount(fee.getAmount());
                    dto.setSemester(fee.getSemester());
                    dto.setIsPaid(fee.isPaid());

                    return dto;
                })
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
    public FeeModel patchOne(long id, JsonPatch patch) throws JsonPatchException {
        Fee fee = feeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Fee not found with id: " + id));
        
        System.out.println("nahi aaapagay");
        Fee feePatched = applyPatchToFee(patch, fee);
        System.out.println(feePatched.getAmount());

        FeeModel dto = new FeeModel();

        dto.setId(fee.getId());
        dto.setStudentId(fee.getStudent().getId());
        dto.setAmount(fee.getAmount());
        dto.setSemester(fee.getSemester());
        dto.setIsPaid(fee.isPaid());
        feeRepository.save(feePatched);

        return dto;
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

    public Fee applyPatchToFee(JsonPatch patch, Fee fee) {
        System.out.println("3218328192319123");
        try {
            System.out.println(patch);
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode patched = patch.apply(objectMapper.convertValue(fee, JsonNode.class));
            System.out.println("bsnsdnsdnbsdnb");
            return objectMapper.treeToValue(patched, Fee.class);
        } catch (JsonPatchException | JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to apply JSON patch: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("Failed to apply JSON patch: " + e.getMessage(), e);
        }
    }
}