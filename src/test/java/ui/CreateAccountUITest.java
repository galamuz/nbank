package ui;

import api.generation.EntityGenerator;
import api.models.*;
import api.requests.steps.AdminSteps;
import api.requests.steps.UserSteps;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.pages.UserDashboardPage;
import utils.Constants;
import java.time.Duration;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static com.codeborne.selenide.Selenide.*;

public class CreateAccountUITest extends BaseUITest {
    private static CreateUserRequestModel user;
    private static LoginUserRequestModel loginUser;
    private static List<CreateUserResponseModel> createUserResponseModelList;

    @BeforeEach
    public void setTestData() {

        user = EntityGenerator.generate(CreateUserRequestModel.class);
        loginUser = LoginUserRequestModel.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .build();
    }
    @Test
    public void userCanCreateAccount(){

        // create user
        AdminSteps.createUser(user);
        //login user
        authAsUser(loginUser);
        //create account
        new UserDashboardPage().open().createAccount();

        // api - account created
        CreateAccountResponseModel createAccount = UserSteps.getAccounts(loginUser).get(0);
        assertThat(createAccount).isNotNull();

        // check alert
        new UserDashboardPage().checkAlertMessageAndAccept(UIAlerts.ACCOUNT_CREATED);
    }
    @Test
    public void userCanAddFundsToAccount() {
        // create user
        AdminSteps.createUser(user);
        //login user
        authAsUser(loginUser);

        //create account
        CreateAccountResponseModel account = UserSteps.createAccount(loginUser);

        Selenide.open(Constants.USER_UI_URL);
        $(Selectors.byText("\uD83D\uDCB0 Deposit Money")).click();

        SelenideElement select = $("select").shouldBe(Condition.visible, Duration.ofSeconds(10));
        select.selectOptionByValue(String.valueOf(account.getId()));

        $(Selectors.byAttribute("placeholder","Enter amount")).setValue("100");
        $(Selectors.byText("\uD83D\uDCB5 Deposit")).click();

        // check transaction
        CreateTransactionResponseModel transaction = UserSteps.getUserAccountTransaction(loginUser,account.getId()).get(0);;
        assertThat(transaction).isNotNull();
    }

    @Test
    public void userCanMakeTransferBetweenAccount() {
        // create user
        AdminSteps.createUser(user);

        //login user
        authAsUser(loginUser);

        //create account
        CreateAccountResponseModel account_1= UserSteps.createAccount(loginUser);
        CreateAccountResponseModel account_2= UserSteps.createAccount(loginUser);

        //Selenide.open(Constants.TRANSFER);

    }

}
