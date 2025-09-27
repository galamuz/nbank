package requests;


import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import models.BaseModel;
import static io.restassured.RestAssured.given;

public class Request<T extends BaseModel> {
    private final RequestSpecification requestSpec;
    private final ResponseSpecification responseSpec;
    private final Endpoint endpoint;

    public Request(RequestSpecification requestSpec, ResponseSpecification responseSpec, Endpoint endpoint) {
        this.requestSpec = requestSpec;
        this.responseSpec = responseSpec;
        this.endpoint = endpoint;
    }
    public ValidatableResponse post(T model ){
      return  given().spec(requestSpec)
              .body(model == null ? "" : model)
              .post(endpoint.getUrl())
              .then().assertThat()
              .spec(responseSpec);
    }

    public ValidatableResponse delete(long id){
        return  given().spec(requestSpec)
                .delete(endpoint.getUrl()+"/"+id)
                .then().assertThat()
                .spec(responseSpec);
    }
    public ValidatableResponse get(long id){
        return  given().spec(requestSpec)
                .get(String.format(endpoint.getUrl(), id))
                .then().assertThat()
                .spec(responseSpec);
    }
    public ValidatableResponse getAll(){
        return  given().spec(requestSpec)
                .get(endpoint.getUrl())
                .then().assertThat()
                .spec(responseSpec);
    }

}
