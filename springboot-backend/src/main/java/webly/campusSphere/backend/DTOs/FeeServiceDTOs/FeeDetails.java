package webly.campusSphere.backend.DTOs.FeeServiceDTOs;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import webly.campusSphere.backend.Models.Fee;

public class FeeDetails {
    private @Getter @Setter long id;
    private @Getter @Setter long studentId;
    private @Getter @Setter String feeType;
    private @Getter @Setter BigDecimal amount;
    private @Getter @Setter Boolean isPaid;

    public static FeeDetails generateDTO(Fee fee){
        FeeDetails dto = new FeeDetails();

        dto.setId(fee.getId());
        dto.setStudentId(fee.getStudent().getId());
        dto.setFeeType(fee.getFeeType().toString());
        dto.setAmount(fee.getAmount());
        dto.setIsPaid(fee.isPaid());

        return dto;
    }
}
