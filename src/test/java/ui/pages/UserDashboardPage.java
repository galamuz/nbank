package ui.pages;

import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;
import lombok.Getter;
import utils.Constants;

import static com.codeborne.selenide.Selenide.$;
@Getter
public class UserDashboardPage extends BasePage<UserDashboardPage> {
    private final SelenideElement userDashboardText = $(Selectors.byText("User Dashboard"));
    private final SelenideElement createAccountButton = $(Selectors.byText("➕ Create New Account"));

    @Override
    public String url() {
        return Constants.USER_UI_URL;
    }
    public UserDashboardPage createAccount(){
        createAccountButton.click();
        return this;
    }
}