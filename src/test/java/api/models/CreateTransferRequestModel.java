package api.models;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CreateTransferRequestModel extends BaseModel {

    private long senderAccountId;
    private long receiverAccountId;
    private int amount;
}
