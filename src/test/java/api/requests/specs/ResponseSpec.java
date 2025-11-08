package api.requests.specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;

public class ResponseSpec {
    private static ResponseSpecBuilder defaultResponseSpecBuilder(){
        return new ResponseSpecBuilder();
    }

    public static ResponseSpecification responseIsOk(){
        return defaultResponseSpecBuilder().expectStatusCode(HttpStatus.SC_OK).build();
    }

    public static ResponseSpecification responseWasUnauthorized(){
        return defaultResponseSpecBuilder().expectStatusCode( HttpStatus.SC_UNAUTHORIZED).build();
    }

    public static ResponseSpecification entityWasCreated(){
        return defaultResponseSpecBuilder().expectStatusCode( HttpStatus.SC_CREATED).build();
    }

    public static ResponseSpecification responseReturnedBadRequest(String errorKey, String errorValue){
        return defaultResponseSpecBuilder().expectStatusCode(HttpStatus.SC_BAD_REQUEST)
                .expectBody(errorKey, Matchers.hasItem(errorValue))
                .build();
    }
    public static ResponseSpecification responseReturnedBadRequest( String errorValue){
        return defaultResponseSpecBuilder().expectStatusCode(HttpStatus.SC_BAD_REQUEST)
                .expectBody( Matchers.equalTo(errorValue))
                .build();
    }
    public static ResponseSpecification entityWasDeleted(){
        return defaultResponseSpecBuilder().expectStatusCode( HttpStatus.SC_OK).build();
    }


}
