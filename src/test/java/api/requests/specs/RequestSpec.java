package api.requests.specs;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import api.models.LoginUserRequestModel;
import api.requests.Endpoint;
import api.requests.Request;
import utils.Constants;

import java.util.List;

public class RequestSpec {
    private static RequestSpecBuilder defaultRequestSpecBuilder(){
        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .addFilters(List.of(new ResponseLoggingFilter(), new RequestLoggingFilter()))
                .setBaseUri(Constants.BASE_URL);

    }
    public static RequestSpecification unauthorizedSpec(){
        return defaultRequestSpecBuilder().build();
    }
    public static RequestSpecification adminAuthorizedSpec(){

        return userAuthorizedSpec(Constants.ADMIN_USER, Constants.ADMIN_PASSWORD);
    }

    public static RequestSpecification userAuthorizedSpec(String userName, String password){

        LoginUserRequestModel userLoginRequestModel = LoginUserRequestModel.builder().username(userName).password(password).build();

        String userAuthHeader = new Request(RequestSpec.unauthorizedSpec(), ResponseSpec.responseIsOk(), Endpoint.LOGIN)
                .post(userLoginRequestModel).extract().header(Constants.HEADER_AUTH);

        return defaultRequestSpecBuilder().addHeader(Constants.HEADER_AUTH,userAuthHeader).build();
    }
}
