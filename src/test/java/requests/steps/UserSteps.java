package requests.steps;

import io.restassured.response.ValidatableResponse;
import models.*;
import requests.Endpoint;
import requests.Request;
import requests.specs.RequestSpec;
import requests.specs.ResponseSpec;

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

    public static ValidatableResponse getUserAccountTransaction(LoginUserRequestModel userLoginRequestModel, CreateAccountResponseModel accountResponseModel){
        return new Request(
                RequestSpec.userAuthorizedSpec(userLoginRequestModel.getUsername(), userLoginRequestModel.getPassword()),
                ResponseSpec.responseIsOk(),Endpoint.ACCOUNTS_TRANSACTIONS).get(accountResponseModel.getId());
    }

}
