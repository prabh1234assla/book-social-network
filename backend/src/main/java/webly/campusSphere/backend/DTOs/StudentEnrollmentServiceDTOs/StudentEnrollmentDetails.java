package webly.campusSphere.backend.DTOs.StudentEnrollmentServiceDTOs;

import lombok.Getter;
import lombok.Setter;
import webly.campusSphere.backend.Models.StudentEnrollment;

public class StudentEnrollmentDetails {
    private @Getter @Setter long id;
    private @Getter @Setter long studentId;
    private @Getter @Setter long courseId;
    private @Getter @Setter Boolean isRegistered;

    public static StudentEnrollmentDetails generateDTO(StudentEnrollment studentEnrollment){

        StudentEnrollmentDetails dto = new StudentEnrollmentDetails();

        dto.setId(studentEnrollment.getId());
        dto.setStudentId(studentEnrollment.getStudentEnrolled().getId());
        dto.setCourseId(studentEnrollment.getCourse().getId());
        dto.setIsRegistered(studentEnrollment.isRegistered());

        return dto;
    }
}
