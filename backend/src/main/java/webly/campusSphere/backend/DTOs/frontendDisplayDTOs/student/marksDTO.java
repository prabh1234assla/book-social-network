package webly.campusSphere.backend.DTOs.frontendDisplayDTOs.student;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import webly.campusSphere.backend.Models.Marks;

@Builder
@Getter
@Setter
public class marksDTO {
    private String courseName;
    private Integer credits;
    private BigDecimal marks;

    public static marksDTO generateDto(Marks marks){
        return marksDTO.builder()
                        .courseName(marks.getCourse().getCourseName())
                        .credits(marks.getCourse().getCredits())
                        .marks(marks.getMarks())
                        .build();
    }
}
