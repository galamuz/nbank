package ui.pages;

import com.codeborne.selenide.Selenide;
import org.openqa.selenium.Alert;
import ui.UIAlerts;
import utils.BaseTest;

import static com.codeborne.selenide.Selenide.switchTo;

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
        softly.assertThat(alert.getText()).contains(uiAlerts.getMessage());
        alert.accept();
        return (T) this;
    }
}
