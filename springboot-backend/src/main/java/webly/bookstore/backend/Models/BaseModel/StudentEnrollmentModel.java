package webly.bookstore.backend.Models.BaseModel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentEnrollmentModel {
    private long id;
    private long studentId;
    private long courseId;
    private Boolean isRegistered;
}
