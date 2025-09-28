package requests.interfaces;

import io.restassured.response.ValidatableResponse;
import models.BaseModel;

import static io.restassured.RestAssured.given;

public interface CrudEndpointInterface <T extends BaseModel> {
     ValidatableResponse post(T model );
     ValidatableResponse delete(long id);
     ValidatableResponse get(long id);
     ValidatableResponse update(long id, BaseModel model);
}
