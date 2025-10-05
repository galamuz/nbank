package ui;

import api.generation.EntityGenerator;
import api.models.CreateUserRequestModel;
import api.models.CreateUserResponseModel;
import api.models.LoginUserRequestModel;
import api.requests.steps.AdminSteps;
import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.pages.AdminPanelPage;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateUserUITest extends BaseUITest {
    private static CreateUserRequestModel user;
    LoginUserRequestModel loginAdminUser;
    private static List<CreateUserResponseModel> createUserResponseModelList;


    @BeforeEach
    public void setTestData() {
             loginAdminUser = LoginUserRequestModel.loginAdmin();
        user = EntityGenerator.generate(CreateUserRequestModel.class);
    }


    @Test
    public void adminCanCreateUserWithCorrectDataTest() {
        // login admin
        authAsUser(loginAdminUser);
        // create user
        new AdminPanelPage().open().createUser(user.getUsername(),user.getPassword())
                .checkAlertMessageAndAccept(UIAlerts.USER_CREATED_SUCCESSFULLY)
                .getAllUsers().findBy(Condition.exactText(user.getUsername()+"\nUSER")).shouldBe(Condition.visible);

        // api - user created
        CreateUserResponseModel createUser = AdminSteps.getAllUsers().stream()
                .filter(userResponse -> userResponse.getUsername().equals(user.getUsername()))
                .findFirst()
                .orElse(null);

        assertThat(createUser).isNotNull();
    }
    @Test
    public void adminCanNotCreateUserWithInvalideDataTest() {
        // login admin
        authAsUser(loginAdminUser);

        user.setUsername("we");
        // create user
        new AdminPanelPage().open().createUser(user.getUsername(),user.getPassword())
                .checkAlertMessageAndAccept(UIAlerts.USER_CREATED_UNSUCCESSFULLY);

        // api - user created
        CreateUserResponseModel createUser = AdminSteps.getAllUsers().stream()
                .filter(userResponse -> userResponse.getUsername().equals(user.getUsername()))
                .findFirst()
                .orElse(null);

        assertThat(createUser).isNotNull();

    }

}
