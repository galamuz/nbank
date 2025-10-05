package api.models;

import lombok.*;
import utils.Constants;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class LoginUserRequestModel extends BaseModel{
    private String username;
    private String password;

    public static LoginUserRequestModel loginAdmin(){
        return LoginUserRequestModel.builder()
                .username(Constants.ADMIN_USER)
                .password(Constants.ADMIN_PASSWORD)
                .build();
    }
}
