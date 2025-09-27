import generation.RandomData;
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
         CreateUserRequestModel createUserRequestModel = CreateUserRequestModel.builder()
                 .username(RandomData.getUserName())
                 .password(RandomData.getPassword())
                 .role(UserRole.USER.toString())
                 .build();

        LoginUserRequestModel userLoginRequestModel = LoginUserRequestModel.builder()
                .username(createUserRequestModel.getUsername())
                .password(createUserRequestModel.getPassword()).build();

         // create user
        CreateUserResponseModel createUser = AdminSteps.createUser(createUserRequestModel);
        softly.assertThat(createUser.getId()).isNotNull().isNotNegative();

        createUserResponseModelList.add(createUser);

        // create account
        CreateAccountResponseModel accountResponceModel= UserSteps.createAccount(userLoginRequestModel);

        softly.assertThat(accountResponceModel.getAccountNumber()).isNotNull().isNotEmpty();
        softly.assertThat(accountResponceModel.getId()).isNotNull();

        // check that account was created
          new Request<BaseModel>(
            RequestSpec.userAuthorizedSpec(userLoginRequestModel.getUsername(), userLoginRequestModel.getPassword()),
            ResponseSpec.responseIsOk(),Endpoint.ACCOUNTS_TRANSACTIONS).get(accountResponceModel.getId());

    }
    //Negative: POST -  Unathorized user can not create account
    @Test
    public void userCanNotCreateAccountWithoutAuthentication(){

        // create account
         new Request<BaseModel>(RequestSpec.unauthorizedSpec(), ResponseSpec.responseWasUnauthorized(),Endpoint.ACCOUNTS)
                .post(null);

    }
    @AfterAll
    public static void deleteTestData() {
        for(CreateUserResponseModel user : createUserResponseModelList) {
            AdminSteps.deleteUser(user);
        }

    }
}
