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
    public static CreateAccountResponseModel createAccount(LoginUserRequestModel userLoginRequestModel) {

        return new Request(RequestSpec.userAuthorizedSpec(userLoginRequestModel.getUsername(), userLoginRequestModel.getPassword()), ResponseSpec.entityWasCreated(), Endpoint.ACCOUNTS)
                .post(null).extract().as(CreateAccountResponseModel.class);
    }

    public static ValidatableResponse login(LoginUserRequestModel userLoginRequestModel) {
        return new Request(RequestSpec.unauthorizedSpec(), ResponseSpec.responseIsOk(), Endpoint.LOGIN)
                .post(userLoginRequestModel);

    }
    public static ValidatableResponse unauthLogin(LoginUserRequestModel userLoginRequestModel) {
     return new Request(RequestSpec.unauthorizedSpec(), ResponseSpec.responseWasUnauthorized(), Endpoint.LOGIN)
            .post(userLoginRequestModel);
    }

    public static List<CreateTransactionResponseModel> getUserAccountTransaction(LoginUserRequestModel userLoginRequestModel, long accountId){
        return new Request(
                RequestSpec.userAuthorizedSpec(userLoginRequestModel.getUsername(), userLoginRequestModel.getPassword()),
                ResponseSpec.responseIsOk(), Endpoint.ACCOUNTS_TRANSACTIONS).get(accountId).extract().as(new TypeRef<List<CreateTransactionResponseModel>>() {});
    }

    public static List<CreateAccountResponseModel> getAccounts(LoginUserRequestModel userLoginRequestModel) {

        return new Request(RequestSpec.userAuthorizedSpec(userLoginRequestModel.getUsername(), userLoginRequestModel.getPassword()), ResponseSpec.responseIsOk(), Endpoint.CUSTOMER_ACCOUNT)
                .getAll().extract().as(new TypeRef<List<CreateAccountResponseModel>>() {});
    }

    public static CreateCustomerRequestModel changeName(CreateCustomerNameRequestModel customerNameRequestModel, LoginUserRequestModel userLoginRequestModel,long id){

        return  new Request(RequestSpec.userAuthorizedSpec(userLoginRequestModel.getUsername(), userLoginRequestModel.getPassword()),
                ResponseSpec.responseIsOk(), Endpoint.CUSTOMER_PROFILE)
                .update(id,customerNameRequestModel).extract().as(CreateCustomerRequestModel.class);
    }

    public static CreateAccountResponseModel createTransaction(LoginUserRequestModel userLoginRequestModel, CreateTransactionRequestModel transactionRequestModel) {

        return new Request(RequestSpec.userAuthorizedSpec(userLoginRequestModel.getUsername(), userLoginRequestModel.getPassword()), ResponseSpec.responseIsOk(), Endpoint.ACCOUNTS_DEPOSIT)
                .post(transactionRequestModel).extract().as(CreateAccountResponseModel.class);
    }

    public static CreateTransferResponseModel createTransfer(LoginUserRequestModel userLoginRequestModel, CreateTransferRequestModel transfer) {

        return new Request(RequestSpec.userAuthorizedSpec(userLoginRequestModel.getUsername(), userLoginRequestModel.getPassword()), ResponseSpec.responseIsOk(), Endpoint.ACCOUNTS_TRANSFER)
                .post(transfer).extract().as(CreateTransferResponseModel.class);
    }



}
