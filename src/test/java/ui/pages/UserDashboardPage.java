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
    private final SelenideElement createTransferButton =$(Selectors.byText("\uD83D\uDD04 Make a Transfer"));
    private final SelenideElement createDepositButton =$(Selectors.byText("\uD83D\uDCB0 Deposit Money"));

    @Override
    public String url() {
        return Constants.USER_UI_URL;
    }
    public UserDashboardPage createAccount(){
        createAccountButton.click();
        return this;
    }
    public UserDashboardPage createTransfer(){
        createTransferButton.click();
        return this;
    }
    public UserDashboardPage createDeposit(){
        createDepositButton.click();
        return this;
    }
}