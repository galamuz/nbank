package ui;

import api.generation.EntityGenerator;
import api.models.CreateUserRequestModel;
import api.models.CreateUserResponseModel;
import api.requests.steps.AdminSteps;
import common.annotation.AdminSession;
import common.extention.AdminSessionExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import ui.pages.AdminPanelPage;
import utils.Constants;
import utils.RetryUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(AdminSessionExtension.class)
public class CreateUserUITest extends BaseUITest {
    private static CreateUserRequestModel user;

    @Test
    @AdminSession
    public void adminCanCreateUserWithCorrectDataTest() {
        user = EntityGenerator.generate(CreateUserRequestModel.class);
        // create user
        assertTrue(
                RetryUtils.retry(
                        () -> new AdminPanelPage()
                                .open()
                                .createUser(user.getUsername(), user.getPassword())
                                .checkAlertMessageAndAccept(UIAlerts.USER_CREATED_SUCCESSFULLY)
                                .getAllUsers()
                                .stream()
                                .anyMatch(u -> u.getUserName().equals(user.getUsername())),
                        result -> result, // условие выхода — пользователь найден (true)
                        Constants.MAX_ATTEMPTS,
                        Constants.NEXT_ATTEMPT_DELAY
                ),
                "User was not found in the user list after retries"
        );
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

        assertTrue(new AdminPanelPage().open().createUser(user.getUsername(), user.getPassword())
                .checkAlertMessageAndAccept(UIAlerts.USER_CREATED_UNSUCCESSFULLY)
                .getAllUsers().stream().noneMatch(userListElement -> userListElement.getUserName().equals(user.getUsername())));

        // api - user created
        CreateUserResponseModel createUser = AdminSteps.getAllUsers().stream()
                .filter(userResponse -> userResponse.getUsername().equals(user.getUsername()))
                .findFirst()
                .orElse(null);

        assertThat(createUser).isNull();
    }

}
