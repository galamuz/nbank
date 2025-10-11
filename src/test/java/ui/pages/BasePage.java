package ui.pages;

import api.models.LoginUserRequestModel;
import api.requests.steps.UserSteps;
import com.codeborne.selenide.Selenide;
import org.openqa.selenium.Alert;
import ui.UIAlerts;
import utils.BaseTest;
import utils.Constants;

import static com.codeborne.selenide.Selenide.executeJavaScript;
import static com.codeborne.selenide.Selenide.switchTo;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public abstract class BasePage<T extends BasePage> extends BaseTest {
    public abstract String url();
    public T open() {
        return Selenide.open(url(),(Class<T>) this.getClass());
    }
    public <T extends BasePage>T getPage(Class<T> pageClass){
        return Selenide.page(pageClass);
    }
    public T checkAlertMessageAndAccept(UIAlerts uiAlerts){
        Alert alert = switchTo().alert();
        assertThat(alert.getText()).contains(uiAlerts.getMessage());
        alert.accept();
        return (T) this;
    }
    public static void authAsUser(LoginUserRequestModel loginUser) {
        String adminAuthToken = new UserSteps(loginUser).login().extract().header(Constants.HEADER_AUTH);

        Selenide.open("/");
        executeJavaScript("localStorage.setItem('authToken',arguments[0])",adminAuthToken);
    }
}
