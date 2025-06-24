package webly.campusSphere.backend.Models;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.*;
import webly.campusSphere.backend.Models.Utils.BaseEntity;

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
    @JsonIgnoreProperties("marks")
    private User candidate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    @JsonIgnoreProperties("marks")
    private Course course;

    @Column(name = "marks", nullable = false)
    private BigDecimal marks;
}
