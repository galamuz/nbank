package ui.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;
import utils.Constants;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;

public class DepositPage extends BasePage<DepositPage> {
    private final SelenideElement accountSelector =  $("select").shouldBe(Condition.visible, Duration.ofSeconds(10));
    private final SelenideElement amountInput = $(Selectors.byAttribute("placeholder","Enter amount"));
    private final SelenideElement button =  $(Selectors.byText("\uD83D\uDCB5 Deposit"));


    @Override
    public String url() {
        return Constants.DEPOSIT_UI_URL;
    }

    public DepositPage makeDeposit(long accountId, int amount){
        accountSelector.selectOptionByValue(String.valueOf(accountId));
        amountInput.setValue(String.valueOf(amount));
        button.click();
        return this;
    }
}