package ui.pages;

import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;
import utils.Constants;

import static com.codeborne.selenide.Selenide.$;

public class ProfilePage  extends BasePage<ProfilePage> {
    private SelenideElement userNameInput = $(Selectors.byAttribute("placeholder","Enter new name"));
    private SelenideElement button = $(Selectors.byText("\uD83D\uDCBE Save Changes"));

    @Override
    public String url() {
        return Constants.PROFILE_UI_URL;
    }

    public ProfilePage changeUserName(String newUserName){
        userNameInput.setValue(newUserName);
        button.click();
        return this;
    }
}
