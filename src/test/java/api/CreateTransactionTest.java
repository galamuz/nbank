package api;

import api.generation.EntityGenerator;
import api.models.*;
import api.models.comparator.ModelAssertions;
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

public class CreateTransactionTest extends BaseTest {
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

    @Test
    public void userCanMakeTransaction() {
        CreateUserRequestModel createUserRequestModel = EntityGenerator.generate(CreateUserRequestModel.class);

        LoginUserRequestModel userLoginRequestModel = LoginUserRequestModel.builder()
                .username(createUserRequestModel.getUsername())
                .password(createUserRequestModel.getPassword()).build();

        // create user
        CreateUserResponseModel createUser = AdminSteps.createUser(createUserRequestModel);
        softly.assertThat(createUser.getId()).isNotNull().isNotNegative();

        createUserResponseModelList.add(createUser);
        UserSteps userSteps = new UserSteps(userLoginRequestModel);
        // create account
        CreateAccountResponseModel accountResponseModel = userSteps.createAccount();

        softly.assertThat(accountResponseModel.getAccountNumber()).isNotNull().isNotEmpty();
        softly.assertThat(accountResponseModel.getId()).isNotNull();

        // check that account was created
        CreateAccountResponseModel createdAccount = userSteps.getAccounts().get(0);
        softly.assertThat(createdAccount).isNotNull();

        //create transaction
        CreateTransactionRequestModel transaction = EntityGenerator.generate(CreateTransactionRequestModel.class);
        transaction.setId(createdAccount.getId());
        CreateAccountResponseModel accountWithNewTransaction = userSteps.createTransaction(transaction);
        ModelAssertions.assertThatModelsMatch(softly, transaction, accountWithNewTransaction.getTransactions().get(0));

    }

    @Test
    public void userCanNotMakeTransactionWithInvalidData() {
        CreateUserRequestModel createUserRequestModel = EntityGenerator.generate(CreateUserRequestModel.class);

        LoginUserRequestModel userLoginRequestModel = LoginUserRequestModel.builder()
                .username(createUserRequestModel.getUsername())
                .password(createUserRequestModel.getPassword()).build();

        // create user
        CreateUserResponseModel createUser = AdminSteps.createUser(createUserRequestModel);
        softly.assertThat(createUser.getId()).isNotNull().isNotNegative();

        createUserResponseModelList.add(createUser);

        UserSteps userSteps = new UserSteps(userLoginRequestModel);
        // create account
        CreateAccountResponseModel accountResponseModel = userSteps.createAccount();

        softly.assertThat(accountResponseModel.getAccountNumber()).isNotNull().isNotEmpty();
        softly.assertThat(accountResponseModel.getId()).isNotNull();

        // check that account was created
        CreateAccountResponseModel createdAccount = userSteps.getAccounts().get(0);
        softly.assertThat(createdAccount).isNotNull();

        //create transaction
        CreateTransactionRequestModel transaction = EntityGenerator.generate(CreateTransactionRequestModel.class);
        transaction.setBalance(-1 * transaction.getBalance());
        transaction.setId(createdAccount.getId());

        new Request(
                RequestSpec.userAuthorizedSpec(userLoginRequestModel.getUsername(), userLoginRequestModel.getPassword()),
                ResponseSpec.responseReturnedBadRequest("Invalid account or amount"), Endpoint.ACCOUNTS_DEPOSIT)
                .post(transaction);
    }
}

