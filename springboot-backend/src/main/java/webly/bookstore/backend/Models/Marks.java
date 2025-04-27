package webly.bookstore.backend.Models;
import webly.bookstore.backend.Models.Utils.BaseEntity;

import java.math.BigDecimal;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "marks")
public class Marks extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private User student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    @Column(name = "semester", nullable = false)
    private Integer semester;

    @Column(name = "marks", nullable = false)
    private BigDecimal marks;
}
