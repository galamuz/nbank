package api;

import api.generation.EntityGenerator;
import api.models.CreateAccountResponseModel;
import api.models.CreateUserRequestModel;
import api.models.CreateUserResponseModel;
import api.models.LoginUserRequestModel;
import api.requests.Endpoint;
import api.requests.Request;
import api.requests.specs.RequestSpec;
import api.requests.specs.ResponseSpec;
import api.requests.steps.AdminSteps;
import api.requests.steps.UserSteps;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import utils.BaseTest;

import java.util.ArrayList;
import java.util.List;

public class CreateAccountTest extends BaseTest {
    private static List<CreateUserResponseModel> createUserResponseModelList;

    @BeforeAll
    public static void setUpTest() {
        createUserResponseModelList = new ArrayList<>();
    }

    @AfterAll
    public static void deleteTestData() {
        for (CreateUserResponseModel user : createUserResponseModelList) {
            AdminSteps.deleteUser(user);
        }
    }

    //Positive: POST -  User can create account
    @Test
    public void userCanCreateAccount() {
        CreateUserRequestModel createUserRequestModel = EntityGenerator.generate(CreateUserRequestModel.class);

        LoginUserRequestModel userLoginRequestModel = LoginUserRequestModel.builder()
                .username(createUserRequestModel.getUsername())
                .password(createUserRequestModel.getPassword()).build();

        // create user
        CreateUserResponseModel createUser = AdminSteps.createUser(createUserRequestModel);
        softly.assertThat(createUser.getId()).isNotNull().isNotNegative();

        createUserResponseModelList.add(createUser);

        // create account
        UserSteps userSteps = new UserSteps(userLoginRequestModel);
        CreateAccountResponseModel accountResponseModel = userSteps.createAccount();

        softly.assertThat(accountResponseModel.getAccountNumber()).isNotNull().isNotEmpty();
        softly.assertThat(accountResponseModel.getId()).isNotNull();

        // check that account was created
        softly.assertThat(userSteps.getAccounts().get(0)).isNotNull();

    }

    //Negative: POST -  Unathorized user can not create account
    @Test
    public void userCanNotCreateAccountWithoutAuthentication() {

        // create account
        new Request(RequestSpec.unauthorizedSpec(), ResponseSpec.responseWasUnauthorized(), Endpoint.ACCOUNTS)
                .post(null);

    }
}
