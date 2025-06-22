package webly.bookstore.backend.Models;

import java.math.BigDecimal;
import java.util.Set;

import javax.validation.constraints.NotNull;

import jakarta.persistence.*;
import lombok.*;
import webly.bookstore.backend.Models.Utils.BaseEntity;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "course")
public class Course extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column(name = "courseName", nullable = false)
    private String courseName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "faculty_id", nullable = false)
    private User faculty;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "studentEnrollment_id", nullable = false)
    private StudentEnrollment studentEnrollment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "marks_id", nullable = false)
    private Marks marks;

    @ManyToMany
    @JoinTable(
        name = "course_students",
        joinColumns = @JoinColumn(name = "course_id"),
        inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private Set<User> students;
}
