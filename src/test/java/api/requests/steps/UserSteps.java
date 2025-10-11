package api.requests.steps;

import io.restassured.common.mapper.TypeRef;
import io.restassured.response.ValidatableResponse;
import api.models.*;
import api.requests.Endpoint;
import api.requests.Request;
import api.requests.specs.RequestSpec;
import api.requests.specs.ResponseSpec;

import java.util.List;

public class UserSteps {
    private final LoginUserRequestModel userLoginRequest;

    public UserSteps(String username, String password){

        userLoginRequest =  LoginUserRequestModel.builder()
                .username(username)
                .password(password)
                .build();
    }
    public UserSteps(LoginUserRequestModel login){
        userLoginRequest =  login;
    }

    public  CreateAccountResponseModel createAccount() {

        return new Request(RequestSpec.userAuthorizedSpec(userLoginRequest.getUsername(), userLoginRequest.getPassword()), ResponseSpec.entityWasCreated(), Endpoint.ACCOUNTS)
                .post(null).extract().as(CreateAccountResponseModel.class);
    }

    public  ValidatableResponse login() {
        return new Request(RequestSpec.unauthorizedSpec(), ResponseSpec.responseIsOk(), Endpoint.LOGIN)
                .post(userLoginRequest);

    }
    public  ValidatableResponse unauthenticLogin() {
        return new Request(RequestSpec.unauthorizedSpec(), ResponseSpec.responseWasUnauthorized(), Endpoint.LOGIN)
                .post(null);
    }

    public  List<CreateTransactionResponseModel> getUserAccountTransaction(long accountId){
        return new Request(
                RequestSpec.userAuthorizedSpec(userLoginRequest.getUsername(), userLoginRequest.getPassword()),
                ResponseSpec.responseIsOk(), Endpoint.ACCOUNTS_TRANSACTIONS).get(accountId).extract().as(new TypeRef<List<CreateTransactionResponseModel>>() {});
    }

    public  List<CreateAccountResponseModel> getAccounts() {

        return new Request(RequestSpec.userAuthorizedSpec(userLoginRequest.getUsername(), userLoginRequest.getPassword()), ResponseSpec.responseIsOk(), Endpoint.CUSTOMER_ACCOUNT)
                .getAll().extract().as(new TypeRef<List<CreateAccountResponseModel>>() {});
    }

    public  CreateCustomerRequestModel changeName(CreateCustomerNameRequestModel customerNameRequestModel, long id){

        return  new Request(RequestSpec.userAuthorizedSpec(userLoginRequest.getUsername(), userLoginRequest.getPassword()),
                ResponseSpec.responseIsOk(), Endpoint.CUSTOMER_PROFILE)
                .update(id,customerNameRequestModel).extract().as(CreateCustomerRequestModel.class);
    }

    public  CreateAccountResponseModel createTransaction( CreateTransactionRequestModel transactionRequestModel) {

        return new Request(RequestSpec.userAuthorizedSpec(userLoginRequest.getUsername(), userLoginRequest.getPassword()), ResponseSpec.responseIsOk(), Endpoint.ACCOUNTS_DEPOSIT)
                .post(transactionRequestModel).extract().as(CreateAccountResponseModel.class);
    }

    public  CreateTransferResponseModel createTransfer( CreateTransferRequestModel transfer) {

        return new Request(RequestSpec.userAuthorizedSpec(userLoginRequest.getUsername(), userLoginRequest.getPassword()), ResponseSpec.responseIsOk(), Endpoint.ACCOUNTS_TRANSFER)
                .post(transfer).extract().as(CreateTransferResponseModel.class);
    }

}
