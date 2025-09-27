package models;

import lombok.*;

@AllArgsConstructor
@Builder
@Data
@NonNull
public class CreateUserRequestModel extends BaseModel{
    private String username;
    private String password;
    private String role;
}
