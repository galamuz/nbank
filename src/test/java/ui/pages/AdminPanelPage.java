package ui.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import lombok.Getter;
import ui.elemements.UserListElement;
import utils.Constants;

import java.util.List;

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
    public List<UserListElement> getAllUsers(){
        Selenide.sleep(2000);
        ElementsCollection elementsCollection = $(Selectors.byText("All Users")).parent().findAll("li");
        return  generatePageElement(elementsCollection, UserListElement::new);
     }

}
