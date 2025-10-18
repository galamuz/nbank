package api.models;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@Builder
@Data
@NonNull
@NoArgsConstructor
public class CreateAccountResponseModel extends BaseModel{

    private long id;
    private String accountNumber;
    private int balance;
    private List<CreateTransactionResponseModel> transactions;
}
