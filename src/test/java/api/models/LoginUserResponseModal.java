package api.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import utils.Constants;

@AllArgsConstructor
@Builder
@Data
@NonNull

public class LoginUserResponseModal extends BaseModel{
    private String username;
    private String role;

}
