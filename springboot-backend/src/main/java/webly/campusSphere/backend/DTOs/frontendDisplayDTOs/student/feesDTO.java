package webly.campusSphere.backend.DTOs.frontendDisplayDTOs.student;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class feesDTO {
    private String feeType;
    private BigDecimal amount;
    private Boolean isPaid;
}
