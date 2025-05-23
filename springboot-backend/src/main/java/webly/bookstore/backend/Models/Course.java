package webly.bookstore.backend.Models;

import java.util.Set;

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

    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
    private Set<StudentEnrollment> enrollments;

    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
    private Set<Marks> marks;
}
