package api.models;

import api.generation.GeneratingRules;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CreateTransactionRequestModel extends BaseModel{
    @GeneratingRules(regexp = "[1-9]{2}")
    private long id;
    @GeneratingRules(regexp = "[1-9]{3}")
    private int balance;
}
