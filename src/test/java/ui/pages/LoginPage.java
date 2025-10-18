package ui.pages;

import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;
import utils.Constants;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage extends BasePage<LoginPage>{
    private final SelenideElement usernameInput =  $(Selectors.byAttribute("placeholder","Username"));
    private final SelenideElement passwordInput = $(Selectors.byAttribute("placeholder","Password"));
    private final SelenideElement button =  $("button");

    @Override
    public String url() {
        return Constants.LOGIN_UI_URL;
    }
    public LoginPage login(String username, String password){
        usernameInput.setValue(username);
        passwordInput.setValue(password);
        button.click();
        return this;
    }


}
