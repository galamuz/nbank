package api;

import api.generation.EntityGenerator;
import api.models.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import api.requests.Endpoint;
import api.requests.Request;
import api.requests.specs.RequestSpec;
import api.requests.specs.ResponseSpec;
import api.requests.steps.AdminSteps;
import api.requests.steps.UserSteps;
import utils.BaseTest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CreateTransferTest extends BaseTest {
    private static List<CreateUserResponseModel> createUserResponseModelList;

    @BeforeAll
    public static void setUpTest() {
        createUserResponseModelList = new ArrayList<>();
    }

    @Test
    public void userCanMakeTransferBetweenAccount() {
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
        CreateAccountResponseModel account_1= userSteps.createAccount();

        softly.assertThat(account_1.getAccountNumber()).isNotNull().isNotEmpty();
        softly.assertThat(account_1.getId()).isNotNull();

        // check that account was created
        CreateAccountResponseModel createdAccount = userSteps.getAccounts().get(0);
        softly.assertThat(createdAccount).isNotNull();

        CreateTransactionRequestModel transaction = EntityGenerator.generate(CreateTransactionRequestModel.class);
        transaction.setId(createdAccount.getId());
        userSteps.createTransaction(transaction);

        // create second account

        CreateAccountResponseModel account_2= userSteps.createAccount();

        softly.assertThat(account_2.getAccountNumber()).isNotNull().isNotEmpty();
        softly.assertThat(account_2.getId()).isNotNull();

        // check that account was created
        CreateAccountResponseModel createdAccount_2 = userSteps.getAccounts().get(1);
        softly.assertThat(createdAccount_2).isNotNull();

        CreateTransferRequestModel transfer = EntityGenerator.generate(CreateTransferRequestModel.class);
        transfer.setSenderAccountId(account_1.getId());
        transfer.setReceiverAccountId(account_2.getId());
        transfer.setAmount(transaction.getBalance());

        CreateTransferResponseModel createTransfer =  userSteps.createTransfer(transfer);
        softly.assertThat(createTransfer).isNotNull();
        softly.assertThat(createTransfer.getMessage()).isEqualTo("Transfer successful");
        // chek that second account has amount transaction

        softly.assertThat(
                userSteps.getUserAccountTransaction(account_2.getId())
                        .stream()
                        .map(CreateTransactionResponseModel::getAmount)
                        .collect(Collectors.toList())
        ).contains(transfer.getAmount());
   }

    @Test
    public void userCanMakeTransferAccount() {
        CreateUserRequestModel firstUser = EntityGenerator.generate(CreateUserRequestModel.class);

        LoginUserRequestModel firstUserLogin = LoginUserRequestModel.builder()
                .username(firstUser.getUsername())
                .password(firstUser.getPassword()).build();

        // create user
        CreateUserResponseModel createUser = AdminSteps.createUser(firstUser);
        softly.assertThat(createUser.getId()).isNotNull().isNotNegative();

        createUserResponseModelList.add(createUser);

        UserSteps userFirstSteps = new UserSteps(firstUserLogin);
        // create account
        CreateAccountResponseModel account_1= userFirstSteps.createAccount();

        softly.assertThat(account_1.getAccountNumber()).isNotNull().isNotEmpty();
        softly.assertThat(account_1.getId()).isNotNull();

        // check that account was created
        CreateAccountResponseModel createdAccount = userFirstSteps.getAccounts().get(0);
        softly.assertThat(createdAccount).isNotNull();

        CreateTransactionRequestModel transaction = EntityGenerator.generate(CreateTransactionRequestModel.class);
        transaction.setId(createdAccount.getId());
        userFirstSteps.createTransaction(transaction);

        // create second account
        CreateUserRequestModel secondUser = EntityGenerator.generate(CreateUserRequestModel.class);

        LoginUserRequestModel secondUserLogin = LoginUserRequestModel.builder()
                .username(secondUser.getUsername())
                .password(secondUser.getPassword()).build();


        CreateUserResponseModel createdSecondUser = AdminSteps.createUser(secondUser);
        softly.assertThat(createdSecondUser.getId()).isNotNull().isNotNegative();
        UserSteps userSecondSteps = new UserSteps(secondUserLogin);
        createUserResponseModelList.add(createdSecondUser);
        // create second account
        CreateAccountResponseModel account_2= userSecondSteps.createAccount();

        softly.assertThat(account_2.getAccountNumber()).isNotNull().isNotEmpty();
        softly.assertThat(account_2.getId()).isNotNull();

        // check that account was created
        CreateAccountResponseModel createdSecondAccount = userSecondSteps.getAccounts().get(0);
        softly.assertThat(createdSecondAccount).isNotNull();

        CreateTransferRequestModel transfer = EntityGenerator.generate(CreateTransferRequestModel.class);
        transfer.setSenderAccountId(account_1.getId());
        transfer.setReceiverAccountId(account_2.getId());
        transfer.setAmount(transaction.getBalance());

        CreateTransferResponseModel createTransfer =  userFirstSteps.createTransfer(transfer);
        softly.assertThat(createTransfer).isNotNull();
        softly.assertThat(createTransfer.getMessage()).isEqualTo("Transfer successful");
        // chek that second account has amount transaction

        softly.assertThat(userSecondSteps.getAccounts().get(0).getBalance()).isEqualTo(transfer.getAmount());
    }
    @Test
    public void userCanNotMakeTransferWithIncorrectData() {
        CreateUserRequestModel createUserRequestModel = EntityGenerator.generate(CreateUserRequestModel.class);

        LoginUserRequestModel userLoginRequestModel = LoginUserRequestModel.builder()
                .username(createUserRequestModel.getUsername())
                .password(createUserRequestModel.getPassword()).build();

        // create user
        CreateUserResponseModel createUser = AdminSteps.createUser(createUserRequestModel);
        softly.assertThat(createUser.getId()).isNotNull().isNotNegative();

        createUserResponseModelList.add(createUser);

        // create account
        UserSteps userFirstSteps = new UserSteps(userLoginRequestModel);
        CreateAccountResponseModel account_1= userFirstSteps.createAccount();

        softly.assertThat(account_1.getAccountNumber()).isNotNull().isNotEmpty();
        softly.assertThat(account_1.getId()).isNotNull();

        // check that account was created
        CreateAccountResponseModel createdAccount = userFirstSteps.getAccounts().get(0);
        softly.assertThat(createdAccount).isNotNull();

        CreateTransactionRequestModel transaction = EntityGenerator.generate(CreateTransactionRequestModel.class);
        transaction.setId(createdAccount.getId());
        userFirstSteps.createTransaction(transaction);

        // create second account

        CreateAccountResponseModel account_2= userFirstSteps.createAccount();

        softly.assertThat(account_2.getAccountNumber()).isNotNull().isNotEmpty();
        softly.assertThat(account_2.getId()).isNotNull();

        // check that account was created
        CreateAccountResponseModel createdAccount_2 = userFirstSteps.getAccounts().get(1);
        softly.assertThat(createdAccount_2).isNotNull();

        CreateTransferRequestModel transfer = EntityGenerator.generate(CreateTransferRequestModel.class);
        transfer.setSenderAccountId(account_1.getId());
        transfer.setReceiverAccountId(account_2.getId());
        transfer.setAmount(-1*transaction.getBalance());


        new Request(
                RequestSpec.userAuthorizedSpec(userLoginRequestModel.getUsername(), userLoginRequestModel.getPassword()),
                ResponseSpec.responseReturnedBadRequest("Invalid transfer: insufficient funds or invalid accounts"), Endpoint.ACCOUNTS_TRANSFER)
                .post(transfer);

    }
    @AfterAll
    public static void deleteTestData() {
        for (CreateUserResponseModel user : createUserResponseModelList) {
           // AdminSteps.deleteUser(user);
        }
    }

}
