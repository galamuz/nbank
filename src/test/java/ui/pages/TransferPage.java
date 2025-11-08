package ui.pages;

import com.codeborne.selenide.*;
import ui.elemements.TransferElementList;
import ui.elemements.UserListElement;
import utils.Constants;

import java.time.Duration;
import java.util.List;

import static com.codeborne.selenide.Selenide.$;

public class TransferPage extends BasePage<TransferPage> {
    private final SelenideElement accountSelector =  $("select").shouldBe(Condition.visible, Duration.ofSeconds(10));
    private final SelenideElement recipientNameInput = $(Selectors.byAttribute("placeholder","Enter recipient name"));
    private final SelenideElement recipientAccountInput = $(Selectors.byAttribute("placeholder","Enter recipient account number"));
    private final SelenideElement amountInput = $(Selectors.byAttribute("placeholder","Enter amount"));
    private final SelenideElement button =  $(Selectors.byText("\uD83D\uDE80 Send Transfer"));
    private final SelenideElement checkbox =  $(Selectors.byClassName("form-check-input"));
    private final SelenideElement repeatTransfer =$(Selectors.byText("\uD83D\uDD01 Transfer Again"));

    @Override
    public String url() {
        return Constants.TRANSFER_UI_URL;
    }

    public TransferPage makeDeposit(long accountId, int amount, String recipientName,String recipientAccount){
        accountSelector.selectOptionByValue(String.valueOf(accountId));
        recipientNameInput.setValue(recipientName);
        recipientAccountInput.setValue(recipientAccount);
        amountInput.setValue(String.valueOf(amount));
        checkbox.setSelected(true);
        button.click();
        return this;
    }
    public TransferPage repeatDeposit(){
        repeatTransfer.click();
        return this;
    }
    public List<TransferElementList> getAllTransfers(){

        ElementsCollection elementsCollection = $(Selectors.byCssSelector("ul")).findAll("li");
        return  generatePageElement(elementsCollection, TransferElementList::new);
    }

}
