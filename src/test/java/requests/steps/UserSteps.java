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


}
