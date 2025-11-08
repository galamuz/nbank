package ui;

import api.generation.EntityGenerator;
import api.models.CreateCustomerNameRequestModel;
import common.annotation.UserSession;
import common.storage.SessionStorage;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import ui.pages.ProfilePage;
import ui.pages.UserDashboardPage;

import static org.assertj.core.api.Assertions.assertThat;

public class ChangeCustomerNameUITest extends BaseUITest {
    @Test
    @UserSession
    public void customerCanChangeName() {

        CreateCustomerNameRequestModel customerNameRequestModel = EntityGenerator.generate(CreateCustomerNameRequestModel.class);

        new UserDashboardPage().open().changeProfile().getPage(ProfilePage.class)
                .changeUserName(customerNameRequestModel.getName()).checkAlertMessageAndAccept(UIAlerts.NAME_UPDATED);

        assertThat(SessionStorage.getSteps().getProfile().getName())
                .isEqualTo(customerNameRequestModel.getName());

    }

    @Test
    @UserSession
    public void customerCanNotChangeNameForToShort() {
        String newShortName = RandomStringUtils.secure().nextAlphabetic(2).toLowerCase();
//        new UserDashboardPage().open().changeProfile().getPage(ProfilePage.class)
//                .changeUserName(newShortName).checkAlertMessageAndAccept(UIAlerts.NAME_NOT_UPDATED);
//
//        assertThat(SessionStorage.getSteps().getProfile().getName())
//                .isNotEqualTo(newShortName);

    }

}
