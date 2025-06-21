package webly.bookstore.backend.Models.BaseModel;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseModel {
    private long id;
    private long facultyId;
    private String courseName;
    private BigDecimal marks;
}