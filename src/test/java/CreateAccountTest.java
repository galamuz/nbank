import generation.EntityGenerator;
import models.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import requests.Endpoint;
import requests.Request;
import requests.specs.RequestSpec;
import requests.specs.ResponseSpec;
import requests.steps.AdminSteps;
import requests.steps.UserSteps;

import java.util.ArrayList;
import java.util.List;

public class CreateAccountTest extends BaseTest {
    private static List<CreateUserResponseModel> createUserResponseModelList;

    @BeforeAll
    public static void setUpTest(){
        createUserResponseModelList= new ArrayList<>();
    }

    //Positive: POST -  User can create account
    @Test
     public void userCanCreateAccount(){
         CreateUserRequestModel createUserRequestModel = EntityGenerator.generate(CreateUserRequestModel.class);

        LoginUserRequestModel userLoginRequestModel = LoginUserRequestModel.builder()
                .username(createUserRequestModel.getUsername())
                .password(createUserRequestModel.getPassword()).build();

         // create user
        CreateUserResponseModel createUser = AdminSteps.createUser(createUserRequestModel);
        softly.assertThat(createUser.getId()).isNotNull().isNotNegative();

        createUserResponseModelList.add(createUser);

        // create account
        CreateAccountResponseModel accountResponseModel= UserSteps.createAccount(userLoginRequestModel);

        softly.assertThat(accountResponseModel.getAccountNumber()).isNotNull().isNotEmpty();
        softly.assertThat(accountResponseModel.getId()).isNotNull();

        // check that account was created
        UserSteps.getUserAccountTransaction(userLoginRequestModel,accountResponseModel);

    }

    //Negative: POST -  Unathorized user can not create account
    @Test
    public void userCanNotCreateAccountWithoutAuthentication(){

        // create account
         new Request(RequestSpec.unauthorizedSpec(), ResponseSpec.responseWasUnauthorized(),Endpoint.ACCOUNTS)
                .post(null);

    }
    @AfterAll
    public static void deleteTestData() {
        for(CreateUserResponseModel user : createUserResponseModelList) {
            AdminSteps.deleteUser(user);
        }

    }
}
