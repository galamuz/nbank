package requests.steps;
import io.restassured.response.ValidatableResponse;
import models.BaseModel;
import models.CreateUserRequestModel;
import models.CreateUserResponseModel;
import requests.Endpoint;
import requests.Request;
import requests.specs.RequestSpec;
import requests.specs.ResponseSpec;

import java.util.List;

public class AdminSteps {
    public static CreateUserResponseModel createUser(CreateUserRequestModel createUserRequestModel){

               return new Request(RequestSpec.adminAuthorizedSpec(), ResponseSpec.entityWasCreated(), Endpoint.USER)
                .post(createUserRequestModel).extract().as(CreateUserResponseModel.class);
    }

    public static ValidatableResponse deleteUser(CreateUserResponseModel responseUser){

        return  new Request(
                RequestSpec.adminAuthorizedSpec(),
                ResponseSpec.entityWasDeleted(), Endpoint.USER).delete(responseUser.getId());
    }

    public static List<CreateUserResponseModel> getAllUsers(){
        return  new Request(
                RequestSpec.adminAuthorizedSpec(), ResponseSpec.responseIsOk(), Endpoint.USER)
                .getAll().extract().jsonPath().getList("", CreateUserResponseModel.class);
    }
}
