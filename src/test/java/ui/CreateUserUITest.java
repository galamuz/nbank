package ui;

import api.generation.EntityGenerator;
import api.models.CreateUserRequestModel;
import api.models.CreateUserResponseModel;
import api.requests.steps.AdminSteps;
import com.codeborne.selenide.Condition;
import common.annotation.AdminSession;
import common.extention.AdminSessionExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import ui.pages.AdminPanelPage;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(AdminSessionExtension.class)
public class CreateUserUITest extends BaseUITest {
    private static CreateUserRequestModel user;

    @Test
    @AdminSession
    public void adminCanCreateUserWithCorrectDataTest() {
        user = EntityGenerator.generate(CreateUserRequestModel.class);
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
    @AdminSession
    public void adminCanNotCreateUserWithInvalideDataTest() {
        user = EntityGenerator.generate(CreateUserRequestModel.class);
        user.setUsername("we");
        // create user
        new AdminPanelPage().open().createUser(user.getUsername(),user.getPassword())
                .checkAlertMessageAndAccept(UIAlerts.USER_CREATED_UNSUCCESSFULLY);

        // api - user created
        CreateUserResponseModel createUser = AdminSteps.getAllUsers().stream()
                .filter(userResponse -> userResponse.getUsername().equals(user.getUsername()))
                .findFirst()
                .orElse(null);

       assertThat(createUser).isNull();
    }

}
