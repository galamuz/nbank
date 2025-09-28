package requests.steps;
import io.restassured.response.ValidatableResponse;
import models.CreateUserRequestModel;
import models.CreateUserResponseModel;
import requests.Endpoint;
import requests.Request;
import requests.specs.RequestSpec;
import requests.specs.ResponseSpec;

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
}
