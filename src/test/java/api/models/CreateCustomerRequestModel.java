package api.models;

import lombok.*;

@AllArgsConstructor
@Builder
@Data
@NonNull
@NoArgsConstructor
public class CreateCustomerRequestModel extends BaseModel{
    private String message;
    private CreateUserResponseModel customer;
}
