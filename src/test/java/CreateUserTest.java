import generation.RandomData;
import models.BaseModel;
import models.CreateUserRequestModel;
import models.CreateUserResponseModel;
import models.UserRole;
import models.comparator.ModelAssertions;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import requests.Endpoint;
import requests.Request;
import requests.specs.RequestSpec;
import requests.specs.ResponseSpec;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import requests.steps.AdminSteps;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class CreateUserTest extends BaseTest {
    private CreateUserRequestModel createUserRequestModel;
    private static List<CreateUserResponseModel> createUserResponseModelList;

    @BeforeAll
    public static void setUpTest() {
        createUserResponseModelList = new ArrayList<>();
    }

    @BeforeEach
    public void setTestData() {
        createUserRequestModel = CreateUserRequestModel.builder()
                .username(RandomData.getUserName())
                .password(RandomData.getPassword())
                .role(UserRole.USER.toString())
                .build();
    }

    @Test
    public void adminCanCreateUserWithCorrectDataTest() {

        CreateUserResponseModel userResponse = AdminSteps.createUser(createUserRequestModel);
        createUserResponseModelList.add(userResponse);
        ModelAssertions.assertThatModelsMatch(softly, createUserRequestModel, userResponse);
    }

    static Stream<Arguments> invalidUserProvider() {
        return Stream.of(
//                Arguments.of("username","Username cannot be blank",
//                        new CreateUserRequestModel("    ", RandomData.getPassword(), UserRole.USER.toString())),
                Arguments.of("username", "Username must be between 3 and 15 characters",
                        new CreateUserRequestModel(RandomData.getShortUserName(), RandomData.getPassword(), UserRole.USER.toString())),
                Arguments.of("username", "Username must be between 3 and 15 characters",
                        new CreateUserRequestModel(RandomData.getLongUserName(), RandomData.getPassword(), UserRole.USER.toString())),
                Arguments.of("username", "Username must contain only letters, digits, dashes, underscores, and dots",
                        new CreateUserRequestModel(RandomData.getUserName() + "#", RandomData.getPassword(), UserRole.USER.toString())),
                // Arguments.of("password", "Password cannot be blank",
                //         new CreateUserRequestModel(RandomData.getUserName(), "   ",  UserRole.USER.toString())),
                Arguments.of("password", "Password must contain at least one digit, one lower case, one upper case, one special character, no spaces, and be at least 8 characters long",
                        new CreateUserRequestModel(RandomData.getUserName(), "aW1@123", UserRole.USER.toString())),
                Arguments.of("role", "Role must be either 'ADMIN' or 'USER'",
                        new CreateUserRequestModel(RandomData.getUserName(), RandomData.getPassword(), "MANAGER"))
        );
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("invalidUserProvider")
    public void adminCannotCreateUserWithInvalidData(String caseName, String caseErrorMessage, CreateUserRequestModel invalidUser) {
        new Request<CreateUserRequestModel>(
                RequestSpec.adminAuthorizedSpec(),
                ResponseSpec.responseReturnedBadRequest(caseName, caseErrorMessage), Endpoint.USER).post(invalidUser);

        List<CreateUserResponseModel> allUsers = new Request<CreateUserRequestModel>(
                RequestSpec.adminAuthorizedSpec(), ResponseSpec.responseIsOk(), Endpoint.USER)
                .getAll().extract().jsonPath().getList("", CreateUserResponseModel.class);

        softly.assertThat(allUsers).extracting(CreateUserResponseModel::getUsername)
                .doesNotContain(invalidUser.getUsername());

    }

    @AfterAll
    public static void deleteTestData() {
        for (CreateUserResponseModel user : createUserResponseModelList) {
            AdminSteps.deleteUser(user);
        }
    }
}


