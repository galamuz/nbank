package api;

import api.generation.EntityGenerator;
import api.models.CreateUserRequestModel;
import api.models.CreateUserResponseModel;
import api.models.LoginUserRequestModel;
import api.requests.steps.AdminSteps;
import api.requests.steps.UserSteps;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.BaseTest;
import utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class LoginUserTest extends BaseTest {
    private static LoginUserRequestModel userLoginRequestModel;
    private static CreateUserRequestModel createUserRequestModel;
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

    @BeforeEach
    public void generateTestData() {
        createUserRequestModel = EntityGenerator.generate(CreateUserRequestModel.class);
        userLoginRequestModel = LoginUserRequestModel.builder()
                .username(createUserRequestModel.getUsername())
                .password(createUserRequestModel.getPassword())
                .build();
    }

    @Test
    public void adminCanCreateAuthTokenTest() {
        userLoginRequestModel.setUsername(Constants.ADMIN_USER);
        userLoginRequestModel.setPassword(Constants.ADMIN_PASSWORD);

        softly.assertThat(
                new UserSteps(userLoginRequestModel).login()
                        .header(Constants.HEADER_AUTH, Matchers.notNullValue()));
    }

    @Test
    public void userCanCreateAuthTokenTest() {

        CreateUserResponseModel createUserResponseModel = AdminSteps.createUser(createUserRequestModel);

        createUserResponseModelList.add(createUserResponseModel);

        softly.assertThat(new UserSteps(userLoginRequestModel).login()
                .header(Constants.HEADER_AUTH, Matchers.notNullValue()));
    }

    @Test
    public void userCanNotCreateAuthTokenWithIncorrectPasswordTest() {

        CreateUserResponseModel createUserResponseModel = AdminSteps.createUser(createUserRequestModel);

        createUserResponseModelList.add(createUserResponseModel);
        userLoginRequestModel.setPassword("");

        new UserSteps(userLoginRequestModel).unauthenticLogin();
    }

}
