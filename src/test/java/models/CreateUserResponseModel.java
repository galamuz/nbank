package models;

import java.util.List;

import lombok.*;


@AllArgsConstructor
@Builder
@Data
@NonNull
@NoArgsConstructor
public class CreateUserResponseModel extends BaseModel {

    private long id;
    private String username;
    private String password;
    private String name;
    private String role;
    private List<CreateAccountResponseModel> accounts;
}
