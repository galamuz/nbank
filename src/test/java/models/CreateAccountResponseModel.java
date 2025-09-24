package models;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@Builder
@Data
@NonNull
@NoArgsConstructor
public class CreateAccountResponseModel extends BaseModel{

    private long id;
    private String accountNumber;
    private BigDecimal balance;
    private List<String> transactions;
}
