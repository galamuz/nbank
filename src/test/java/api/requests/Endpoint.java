package api.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import api.models.*;
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
    ),
    CUSTOMER_ACCOUNT(
            Constants.CUSTOMER_URL+ "/"+Constants.ACCOUNT_URL,
            CreateUserRequestModel.class,
            CreateUserResponseModel.class
    ),
    CUSTOMER_PROFILE(
            Constants.CUSTOMER_URL+ "/"+Constants.PROFILE_URL,
            CreateCustomerNameRequestModel.class,
            CreateUserResponseModel.class
    ),
    ACCOUNTS_DEPOSIT(
            Constants.ACCOUNT_URL + "/"+Constants.DEPOSIT_URL,
            CreateTransactionRequestModel.class,
            CreateAccountResponseModel.class
    ),
    ACCOUNTS_TRANSFER(
            Constants.ACCOUNT_URL + "/"+Constants.TRANSFER_URL,
            CreateTransferRequestModel.class,
            CreateTransferResponseModel.class
    );

    private final String url;
    private final Class<? extends BaseModel> requestModel;
    private final Class<? extends BaseModel> responceModel;

}
