package models;

import generation.GeneratingRules;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CreateUserRequestModel extends BaseModel{
    @GeneratingRules(regexp = "[A-Za-z0-9]{3,15}")
    private String username;
    @GeneratingRules(regexp = "[A-Z]{3}[a-z]{4}[0-9]{3}[$%&*]{2}")
    private String password;
    @GeneratingRules(regexp ="USER")
    private String role;
}
