package requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import models.*;
import utils.Constants;

@AllArgsConstructor
@Getter

public enum Endpoint {
    USER(
            Constants.USERS_URL,
            CreateUserRequestModel.class,
            CreateUserResponseModel.class
    ),
    ACCOUNTS(
            Constants.ACCOUNT_URL,
            BaseModel.class,
            CreateAccountResponseModel.class
    ),
    LOGIN(
            Constants.LOGIN_URL,
            LoginUserRequestModel.class,
            LoginUserResponseModal.class
    ),
    ACCOUNTS_TRANSACTIONS(
        Constants.ACCOUNT_URL + "/%s"+Constants.TRANSACTION_URL,
        BaseModel.class,
        BaseModel.class
);

    private final String url;
    private final Class<? extends BaseModel> requestModel;
    private final Class<? extends BaseModel> responceModel;
    
}
