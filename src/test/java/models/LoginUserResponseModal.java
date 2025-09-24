package models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@AllArgsConstructor
@Builder
@Data
@NonNull

public class LoginUserResponseModal extends BaseModel{
    private String username;
    private String role;
}
