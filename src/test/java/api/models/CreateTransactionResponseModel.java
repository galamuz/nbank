package api.models;

import lombok.*;

@AllArgsConstructor
@Builder
@Data
@NonNull
@NoArgsConstructor
public class CreateTransactionResponseModel extends BaseModel{
    private long id;
    private int amount;
    private String timestamp;
    private long relatedAccountId;
    private String type;
}
