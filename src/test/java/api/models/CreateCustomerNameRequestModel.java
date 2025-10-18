package api.models;

import api.generation.GeneratingRules;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CreateCustomerNameRequestModel extends BaseModel{
    @GeneratingRules(regexp = "[A-Za-z]+ [A-Za-z]+")
    private String name;

}
