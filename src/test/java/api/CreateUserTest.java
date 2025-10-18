package api;

import api.generation.EntityGenerator;
import api.models.CreateUserRequestModel;
import api.models.CreateUserResponseModel;
import api.models.comparator.ModelAssertions;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import api.requests.Endpoint;
import api.requests.Request;
import api.requests.specs.RequestSpec;
import api.requests.specs.ResponseSpec;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import api.requests.steps.AdminSteps;
import utils.BaseTest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class CreateUserTest extends BaseTest {
    private static CreateUserRequestModel createUserRequestModel;
    private static List<CreateUserResponseModel> createUserResponseModelList;

    @BeforeAll
    public static void setUpTest() {
        createUserResponseModelList = new ArrayList<>();
    }

    @BeforeEach
    public void setTestData() {
        createUserRequestModel = EntityGenerator.generate(CreateUserRequestModel.class);
    }

    @Test
    public void adminCanCreateUserWithCorrectDataTest() {

        CreateUserResponseModel userResponse = AdminSteps.createUser(createUserRequestModel);
        createUserResponseModelList.add(userResponse);
        ModelAssertions.assertThatModelsMatch(softly, createUserRequestModel, userResponse);
    }

    private static CreateUserRequestModel withInvalidField(String fieldName, String invalidValue) {
        CreateUserRequestModel base = EntityGenerator.generate(CreateUserRequestModel.class);

        switch (fieldName) {
            case "username":
                base.setUsername(invalidValue);
                return base;
            case "password":
                base.setPassword(invalidValue);
                return base;
            case "role":
                base.setRole(invalidValue);
                return base;
            default:
                throw new IllegalArgumentException("Unknown field: " + fieldName);
        }
    }

    static Stream<Arguments> invalidUserProvider() {
        return Stream.of(
                Arguments.of("username", "Username cannot be blank",
                        withInvalidField("username", "    ")),
                Arguments.of("username", "Username must be between 3 and 15 characters",
                        withInvalidField("username", RandomStringUtils.secure().nextAlphabetic(2).toLowerCase())),
                Arguments.of("username", "Username must be between 3 and 15 characters",
                        withInvalidField("username",  RandomStringUtils.secure().nextAlphabetic(16).toLowerCase())),
                Arguments.of("username", "Username must contain only letters, digits, dashes, underscores, and dots",
                        withInvalidField("username",  "useruser#")),
                Arguments.of("password", "Password cannot be blank",
                        withInvalidField("password", "   ")),
                Arguments.of("password", "Password must contain at least one digit, one lower case, one upper case, one special character, no spaces, and be at least 8 characters long",
                        withInvalidField("password", "aW1@123")),
                Arguments.of("role", "Role must be either 'ADMIN' or 'USER'",
                        withInvalidField("role", "MANAGER"))
        );
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("invalidUserProvider")
    public void adminCannotCreateUserWithInvalidData(String caseName, String caseErrorMessage, CreateUserRequestModel invalidUser) {
        new Request(
                RequestSpec.adminAuthorizedSpec(),
                ResponseSpec.responseReturnedBadRequest(caseName, caseErrorMessage), Endpoint.USER).post(invalidUser);

        List<CreateUserResponseModel> allUsers = AdminSteps.getAllUsers();

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


