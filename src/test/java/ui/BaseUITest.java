package ui;

import api.models.LoginUserRequestModel;
import api.requests.steps.UserSteps;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.chrome.ChromeOptions;
import utils.BaseTest;
import utils.Constants;

import java.util.HashMap;
import java.util.Map;

import static com.codeborne.selenide.Selenide.executeJavaScript;

public class BaseUITest {

    @BeforeAll
    public static void setupSelenide(){

        Configuration.baseUrl= Constants.BASE_UI_URL;
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments(Constants.CHROME_SANBOX, Constants.CHROME_SANBOX, Constants.CHROME_WINDOWS);

        Map<String, Object> selenoidOptions = new HashMap<>();
        selenoidOptions.put("enableVNC", true);

        chromeOptions.setCapability("selenoid:options", selenoidOptions);

        Configuration.browserCapabilities = chromeOptions;
    }

    public void authAsUser(LoginUserRequestModel loginUser) {
        String adminAuthToken = UserSteps.login(loginUser).extract().header(Constants.HEADER_AUTH);

        Selenide.open("/");
        executeJavaScript("localStorage.setItem('authToken',arguments[0])",adminAuthToken);
    }

}
