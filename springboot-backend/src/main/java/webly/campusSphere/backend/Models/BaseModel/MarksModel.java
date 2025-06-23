package webly.campusSphere.backend.Models.BaseModel;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MarksModel {
    private long id;
    private long studentId;
    private long courseId;
    private BigDecimal marks;
}

