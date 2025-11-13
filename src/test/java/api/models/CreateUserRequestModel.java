package api.models;

import api.generation.GeneratingRules;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import utils.Constants;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CreateUserRequestModel extends BaseModel {
    @GeneratingRules(regexp = "[A-Za-z0-9]{7,15}")
    private String username;
    @GeneratingRules(regexp = "[A-Z]{3}[a-z]{4}[0-9]{3}[$%&]{2}")
    private String password;
    @GeneratingRules(regexp = "USER")
    private String role;

    public static CreateUserRequestModel getAdmin() {
        return CreateUserRequestModel.builder()
                .username(Constants.ADMIN_USER)
                .password(Constants.ADMIN_PASSWORD)
                .build();
    }
}
