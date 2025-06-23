package webly.campusSphere.backend.Models;

import java.math.BigDecimal;
import java.util.Set;

import javax.validation.constraints.NotNull;

import jakarta.persistence.*;
import lombok.*;
import webly.campusSphere.backend.Models.Utils.BaseEntity;

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

    @Column(name = "credits", nullable = false)
    private Integer credits;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "faculty_id", nullable = false)
    private User faculty;

    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
    private Set<StudentEnrollment> studentEnrollment;

    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
    private Set<Marks> marks;

    @ManyToMany
    @JoinTable(
        name = "course_students",
        joinColumns = @JoinColumn(name = "course_id"),
        inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private Set<User> students;
}
