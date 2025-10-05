package ui.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;
import lombok.Getter;
import utils.Constants;

import static com.codeborne.selenide.Selenide.$;
@Getter
public class AdminPanelPage extends BasePage<AdminPanelPage>{
    private SelenideElement usernameInput =  $(Selectors.byAttribute("placeholder","Username"));
    private SelenideElement passwordInput = $(Selectors.byAttribute("placeholder","Password"));
    private SelenideElement button =  $(Selectors.byText("Add User"));

    private SelenideElement adminPanelText = $(Selectors.byText("Admin Panel"));
    @Override
    public String url() {
        return Constants.ADMIN_UI_URL;
    }

    public AdminPanelPage createUser(String username, String password){
        usernameInput.setValue(username);
        passwordInput.setValue(password);
        button.click();
        return this;
    }
    public ElementsCollection getAllUsers(){
        return  $(Selectors.byText("All Users")).parent().findAll("li");
     }

}
