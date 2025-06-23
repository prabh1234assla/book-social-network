package webly.campusSphere.backend.DTOs.frontendDisplayDTOs.student;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import webly.campusSphere.backend.Models.Fee;

@Builder
@Setter
@Getter
public class feesDTO {
    private String feeType;
    private BigDecimal amount;
    private Boolean isPaid;

    public static feesDTO generaDto(Fee fee){
        return feesDTO.builder()
                        .feeType(fee.getFeeType().toString())
                        .amount(fee.getAmount())
                        .isPaid(fee.isPaid())
                        .build();
    }
}
