package webly.bookstore.backend.Models.BaseModel;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseModel {
    private long id;
    private long facultyId;
    private String courseName;

    private Set<StudentEnrollmentModel> enrollments;
    private Set<MarksModel> marks;
}