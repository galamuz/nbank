package api.requests;

import api.models.BaseModel;
import api.requests.interfaces.CrudEndpointInterface;
import api.requests.interfaces.GetAllInterface;
import common.helpers.StepLogger;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.given;

public class Request implements CrudEndpointInterface<BaseModel>, GetAllInterface {
    private final RequestSpecification requestSpec;
    private final ResponseSpecification responseSpec;
    private final Endpoint endpoint;

    public Request(RequestSpecification requestSpec, ResponseSpecification responseSpec, Endpoint endpoint) {
        this.requestSpec = requestSpec;
        this.responseSpec = responseSpec;
        this.endpoint = endpoint;
    }

    @Override
    public ValidatableResponse post(BaseModel model) {
        return StepLogger.log("POST request " + endpoint.getUrl(), () -> {
            return given().spec(requestSpec)
                    .body(model == null ? "" : model)
                    .post(endpoint.getUrl())
                    .then().assertThat()
                    .spec(responseSpec);
        });
    }

    @Override
    public ValidatableResponse delete(long id) {
        return StepLogger.log("DELETE request " + endpoint.getUrl(), () -> {
            return given().spec(requestSpec)
                    .delete(endpoint.getUrl() + "/" + id)
                    .then().assertThat()
                    .spec(responseSpec);
        });
    }

    @Override
    public ValidatableResponse get(long id) {
        return StepLogger.log("GET request " + endpoint.getUrl(), () -> {
            return given().spec(requestSpec)
                    .get(String.format(endpoint.getUrl(), id))
                    .then().assertThat()
                    .spec(responseSpec);
        });
    }

    @Override
    public ValidatableResponse update(long id, BaseModel model) {
        return StepLogger.log("PUT request  " + endpoint.getUrl(), () -> {
            return given().spec(requestSpec)
                    .body(model == null ? "" : model)
                    .put(String.format(endpoint.getUrl(), id))
                    .then().assertThat()
                    .spec(responseSpec);
        });
    }

    @Override
    public ValidatableResponse getAll() {
        return StepLogger.log("GET all request  " + endpoint.getUrl(), () -> {
            return given().spec(requestSpec)
                    .get(endpoint.getUrl())
                    .then().assertThat()
                    .spec(responseSpec);
        });
    }
}
