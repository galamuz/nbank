package api;

import api.generation.EntityGenerator;
import api.models.*;
import api.models.comparator.ModelAssertions;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import api.requests.steps.AdminSteps;
import api.requests.steps.UserSteps;
import utils.BaseTest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class ChangeCustomerNameTest extends BaseTest {
    private static List<CreateUserResponseModel> createUserResponseModelList;

    @BeforeAll
    public static void setUpTest(){
        createUserResponseModelList= new ArrayList<>();
    }

    @Test
    public void customerCanChangeName(){
        CreateUserRequestModel createUserRequestModel = EntityGenerator.generate(CreateUserRequestModel.class);

        LoginUserRequestModel userLoginRequestModel = LoginUserRequestModel.builder()
                .username(createUserRequestModel.getUsername())
                .password(createUserRequestModel.getPassword()).build();

        CreateCustomerNameRequestModel customerNameRequestModel = EntityGenerator.generate(CreateCustomerNameRequestModel.class);

        // create user
        CreateUserResponseModel createUser = AdminSteps.createUser(createUserRequestModel);
        softly.assertThat(createUser.getId()).isNotNull().isNotNegative();

        createUserResponseModelList.add(createUser);

        createUserRequestModel.setUsername(customerNameRequestModel.getName());
        createUser.setName(customerNameRequestModel.getName());
        UserSteps userSteps = new UserSteps(userLoginRequestModel);

        CreateCustomerRequestModel updateUser = userSteps.changeName(customerNameRequestModel, createUser.getId());

        ModelAssertions.assertThatModelsMatch(softly, createUser, updateUser.getCustomer());
        softly.assertThat(updateUser.getMessage()).isEqualTo("Profile updated successfully");
    }


    static Stream<Arguments> invalidCustomerNameProvider() {
        return Stream.of(
                Arguments.of("Too long", "Name must be between 0 and 150 characters", RandomStringUtils.secure().nextAlphabetic(256))
        );
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("invalidCustomerNameProvider")
    public void customerCanNotCreateInvalidName(String caseName, String caseErrorMessage, String invalidName) {

        CreateUserRequestModel createUserRequestModel = EntityGenerator.generate(CreateUserRequestModel.class);


        CreateUserResponseModel createUser = AdminSteps.createUser(createUserRequestModel);
        softly.assertThat(createUser.getId()).isNotNull().isNotNegative();
        createUserResponseModelList.add(createUser);


        CreateCustomerNameRequestModel invalidNameRequestModel = new CreateCustomerNameRequestModel();
        invalidNameRequestModel.setName(invalidName);

//        new Request(
//               RequestSpec.userAuthorizedSpec(createUserRequestModel.getUsername(), createUserRequestModel.getPassword()),
//                ResponseSpec.responseReturnedBadRequest("name", caseErrorMessage),
//                Endpoint.CUSTOMER_PROFILE
//        ).update(createUser.getId(),invalidNameRequestModel);
    }

    @Test
    public void customerCanGetAccounts(){
        CreateUserRequestModel createUserRequestModel = EntityGenerator.generate(CreateUserRequestModel.class);

        LoginUserRequestModel userLoginRequestModel = LoginUserRequestModel.builder()
                .username(createUserRequestModel.getUsername())
                .password(createUserRequestModel.getPassword()).build();

        // create user
        CreateUserResponseModel createUser = AdminSteps.createUser(createUserRequestModel);
        softly.assertThat(createUser.getId()).isNotNull().isNotNegative();

        createUserResponseModelList.add(createUser);
        //create accounts
        UserSteps userStepsFirstUser = new UserSteps(userLoginRequestModel);

        CreateAccountResponseModel account_1= userStepsFirstUser.createAccount();

        softly.assertThat(account_1.getAccountNumber()).isNotNull().isNotEmpty();
        softly.assertThat(account_1.getId()).isNotNull();

        CreateAccountResponseModel account_2= userStepsFirstUser.createAccount();

        softly.assertThat(account_2.getAccountNumber()).isNotNull().isNotEmpty();
        softly.assertThat(account_2.getId()).isNotNull();

        // check that account was created
        softly.assertThat(userStepsFirstUser.getAccounts().size()).isEqualTo(2);

    }


    @AfterAll
    public static void deleteTestData() {
        for (CreateUserResponseModel user : createUserResponseModelList) {
            AdminSteps.deleteUser(user);
        }
    }
}
