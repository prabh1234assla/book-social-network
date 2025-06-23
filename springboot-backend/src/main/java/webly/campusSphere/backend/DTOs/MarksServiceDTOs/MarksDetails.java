package webly.campusSphere.backend.DTOs.MarksServiceDTOs;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import webly.campusSphere.backend.Models.Marks;

public class MarksDetails {
    private @Getter @Setter long id;
    private @Getter @Setter long studentId;
    private @Getter @Setter long courseId;
    private @Getter @Setter BigDecimal marks;

    public static MarksDetails generateDTO(Marks marks){
        MarksDetails dto = new MarksDetails();
        
        dto.setId(marks.getId());
        dto.setCourseId(marks.getCourse().getId());
        dto.setStudentId(marks.getCourse().getId());
        dto.setMarks(marks.getMarks());

        return dto;
    }
}

