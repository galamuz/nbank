package ui;

import api.generation.EntityGenerator;
import api.models.CreateUserRequestModel;
import api.requests.steps.AdminSteps;
import com.codeborne.selenide.Condition;
import common.annotation.Browsers;
import org.junit.jupiter.api.Test;
import ui.pages.AdminPanelPage;
import ui.pages.LoginPage;
import ui.pages.UserDashboardPage;

public class LoginUserUITest extends BaseUITest {

    @Test
    @Browsers({"chrome"})
    public void adminCanLoginWithCorrectDataTest() {
        CreateUserRequestModel user = CreateUserRequestModel.getAdmin();

        new LoginPage().open().login(user.getUsername(), user.getPassword())
                .getPage(AdminPanelPage.class).getAdminPanelText().shouldBe(Condition.visible);
    }

    @Test
    public void userCanLoginWithCorrectDataTest() {
        CreateUserRequestModel user = EntityGenerator.generate(CreateUserRequestModel.class);
        AdminSteps.createUser(user);

        new LoginPage().open().login(user.getUsername(), user.getPassword())
                .getPage(UserDashboardPage.class).getUserDashboardText().shouldBe(Condition.visible);
    }
}
