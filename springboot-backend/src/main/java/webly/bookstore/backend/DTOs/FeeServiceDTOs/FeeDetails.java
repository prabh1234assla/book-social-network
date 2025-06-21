package webly.bookstore.backend.DTOs.FeeServiceDTOs;

import java.math.BigDecimal;

import lombok.Setter;
import webly.bookstore.backend.Models.Fee;

public class FeeDetails {
    private @Setter long id;
    private @Setter long studentId;
    private @Setter String feeType;
    private @Setter BigDecimal amount;
    private @Setter Boolean isPaid;

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
