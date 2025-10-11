package ui;

import com.codeborne.selenide.Configuration;
import common.extention.AdminSessionExtension;
import common.extention.UserSessionExtension;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.chrome.ChromeOptions;
import utils.Constants;

import java.util.HashMap;
import java.util.Map;

@ExtendWith(AdminSessionExtension.class)
@ExtendWith(UserSessionExtension.class)
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

}
