package webly.bookstore.backend.Models.BaseModel;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MarksModel {
    private long id;
    private long studentId;
    private long courseId;
    private Integer semester;
    private BigDecimal marks;
}

