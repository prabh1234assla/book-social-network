package webly.bookstore.backend.Models;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.*;
import webly.bookstore.backend.Models.Utils.BaseEntity;
import webly.bookstore.backend.Models.Utils.FeeType;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "fee")
public class Fee extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    @JsonIgnoreProperties("fees")
    private User student;

    @Column(name = "semester", nullable = false)
    @NotNull(message = "Semester must be specified.")
    private Integer semester;

    @Enumerated(EnumType.STRING)
    private FeeType feeType;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "isPaid", nullable = false)
    private boolean isPaid;
}
