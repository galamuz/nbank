package api.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CreateTransferResponseModel extends BaseModel{
    private long senderAccountId;
    private long receiverAccountId;
    private int amount;
    private String message;
}
