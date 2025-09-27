package models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;


@AllArgsConstructor
@Builder
@Data
@NonNull

public class LoginUserRequestModel extends BaseModel{

    private String username;
    private String password;


}
