package webly.bookstore.backend.Models.BaseModel;

import java.util.Set;

import org.springframework.stereotype.Component;

import lombok.Getter;
import webly.bookstore.backend.Models.Utils.UserRole;

@Getter
@Component
public class UserModel {

    private long id;
    private UserRole role;
    private String username;
    private String email;
    private String password;
    private Set<CourseModel> coursesTaught;
    private Set<StudentEnrollmentModel> enrollments;
    private Set<FeeModel> fees;
    private Set<MarksModel> marks;
}