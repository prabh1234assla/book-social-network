package webly.campusSphere.backend.Models.BaseModel;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;
import webly.campusSphere.backend.Models.Marks;

@Getter
@Setter
public class CourseModel {
    private long id;
    private long facultyId;
    private String courseName;
    private Integer credits;
    private Set<Long> studentsId;
    private Set<Marks> marks;
}