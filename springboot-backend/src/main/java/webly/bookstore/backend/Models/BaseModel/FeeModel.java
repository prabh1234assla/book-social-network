package webly.bookstore.backend.Models.BaseModel;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import webly.bookstore.backend.Models.Utils.FeeType;

@Getter
@Setter
public class FeeModel {
    private long id;
    private long studentId;
    private Integer semester;
    private FeeType feeType;
    private BigDecimal amount;
    private Boolean isPaid;
}
