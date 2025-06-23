package webly.campusSphere.backend.Models;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.*;
import webly.campusSphere.backend.Models.Utils.BaseEntity;
import webly.campusSphere.backend.Models.Utils.FeeType;

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

    @Enumerated(EnumType.STRING)
    @Column(name = "feeType", nullable = false)
    private FeeType feeType;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "isPaid", nullable = false)
    private boolean isPaid;
}
