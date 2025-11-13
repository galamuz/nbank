package api.requests.steps;

import api.models.*;
import api.requests.Endpoint;
import api.requests.Request;
import api.requests.specs.RequestSpec;
import api.requests.specs.ResponseSpec;
import common.helpers.StepLogger;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.ValidatableResponse;

import java.util.List;

public class UserSteps {
    private final LoginUserRequestModel userLoginRequest;

    public UserSteps(String username, String password) {

        userLoginRequest = LoginUserRequestModel.builder()
                .username(username)
                .password(password)
                .build();
    }

    public UserSteps(LoginUserRequestModel login) {
        userLoginRequest = login;
    }

    public CreateAccountResponseModel createAccount() {
        return StepLogger.log("User create account", () -> {
            return new Request(RequestSpec.userAuthorizedSpec(userLoginRequest.getUsername(), userLoginRequest.getPassword()), ResponseSpec.entityWasCreated(), Endpoint.ACCOUNTS)
                    .post(null).extract().as(CreateAccountResponseModel.class);
        });
    }

    public ValidatableResponse login() {
        return StepLogger.log("User login", () -> {
            return new Request(RequestSpec.unauthorizedSpec(), ResponseSpec.responseIsOk(), Endpoint.LOGIN)
                    .post(userLoginRequest);
        });
    }

    public ValidatableResponse unauthenticLogin() {
        return StepLogger.log("User unauth login", () -> {

            return new Request(RequestSpec.unauthorizedSpec(), ResponseSpec.responseWasUnauthorized(), Endpoint.LOGIN)
                    .post(null);
        });
    }

    public List<CreateTransactionResponseModel> getUserAccountTransaction(long accountId) {
        return StepLogger.log("User create transaction in account " + accountId, () -> {

            return new Request(
                    RequestSpec.userAuthorizedSpec(userLoginRequest.getUsername(), userLoginRequest.getPassword()),
                    ResponseSpec.responseIsOk(), Endpoint.ACCOUNTS_TRANSACTIONS)
                    .get(accountId).extract().as(new TypeRef<List<CreateTransactionResponseModel>>() {
                    });
        });
    }

    public List<CreateAccountResponseModel> getAccounts() {
        return StepLogger.log("User get account", () -> {
            return new Request(RequestSpec.userAuthorizedSpec(userLoginRequest.getUsername(), userLoginRequest.getPassword()), ResponseSpec.responseIsOk(), Endpoint.CUSTOMER_ACCOUNT)
                    .getAll().extract().as(new TypeRef<List<CreateAccountResponseModel>>() {
                    });
        });
    }

    public CreateUserResponseModel getProfile() {
        return StepLogger.log("User get profile", () -> {
            return new Request(RequestSpec.userAuthorizedSpec(userLoginRequest.getUsername(), userLoginRequest.getPassword()),
                    ResponseSpec.responseIsOk(), Endpoint.CUSTOMER_PROFILE)
                    .get(0).extract().as(CreateUserResponseModel.class);
        });
    }

    public CreateCustomerRequestModel changeName(CreateCustomerNameRequestModel customerNameRequestModel, long id) {
        return StepLogger.log("User change name", () -> {
            return new Request(RequestSpec.userAuthorizedSpec(userLoginRequest.getUsername(), userLoginRequest.getPassword()),
                    ResponseSpec.responseIsOk(), Endpoint.CUSTOMER_PROFILE)
                    .update(id, customerNameRequestModel).extract().as(CreateCustomerRequestModel.class);
        });
    }

    public CreateAccountResponseModel createTransaction(CreateTransactionRequestModel transactionRequestModel) {
        return StepLogger.log("User create transaction", () -> {
            return new Request(RequestSpec.userAuthorizedSpec(userLoginRequest.getUsername(), userLoginRequest.getPassword()), ResponseSpec.responseIsOk(), Endpoint.ACCOUNTS_DEPOSIT)
                    .post(transactionRequestModel).extract().as(CreateAccountResponseModel.class);
        });
    }

    public CreateTransferResponseModel createTransfer(CreateTransferRequestModel transfer) {
        return StepLogger.log("User create  transfer", () -> {
            return new Request(RequestSpec.userAuthorizedSpec(userLoginRequest.getUsername(), userLoginRequest.getPassword()), ResponseSpec.responseIsOk(), Endpoint.ACCOUNTS_TRANSFER)
                    .post(transfer).extract().as(CreateTransferResponseModel.class);
        });
    }

}
