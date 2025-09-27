import generation.RandomData;
import models.*;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import requests.Endpoint;
import requests.Request;
import requests.specs.RequestSpec;
import requests.specs.ResponseSpec;
import requests.steps.AdminSteps;
import requests.steps.UserSteps;
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


    @BeforeEach
    public void generateTestData() {
        createUserRequestModel = CreateUserRequestModel.builder()
                .username(RandomData.getUserName())
                .password(RandomData.getPassword())
                .role(UserRole.USER.toString())
                .build();


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
                UserSteps.login(userLoginRequestModel)
                        .header(Constants.HEADER_AUTH, Matchers.notNullValue()));
    }

    @Test
    public void userCanCreateAuthTokenTest() {

        CreateUserResponseModel createUserResponseModel = AdminSteps.createUser(createUserRequestModel);

        createUserResponseModelList.add(createUserResponseModel);

        softly.assertThat(
                UserSteps.login(userLoginRequestModel)
                        .header(Constants.HEADER_AUTH, Matchers.notNullValue()));
    }

    @Test
    public void userCanNotCreateAuthTokenWithIncorrectPasswordTest() {

        CreateUserResponseModel createUserResponseModel = AdminSteps.createUser(createUserRequestModel);

        createUserResponseModelList.add(createUserResponseModel);
        userLoginRequestModel.setPassword("");

        new Request<LoginUserRequestModel>(RequestSpec.unauthorizedSpec(), ResponseSpec.responseWasUnauthorized(), Endpoint.LOGIN)
                .post(userLoginRequestModel);
    }

    @AfterAll
    public static void deleteTestData() {
        for (CreateUserResponseModel user : createUserResponseModelList) {
            AdminSteps.deleteUser(user);
        }
    }

}
