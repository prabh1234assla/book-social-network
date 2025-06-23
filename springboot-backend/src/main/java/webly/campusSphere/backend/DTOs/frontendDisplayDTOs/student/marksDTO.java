package webly.campusSphere.backend.DTOs.frontendDisplayDTOs.student;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class marksDTO {
    private String courseName;
    private Integer credits;
    private BigDecimal marks;
}
