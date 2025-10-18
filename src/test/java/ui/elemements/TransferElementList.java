package ui.elemements;

import com.codeborne.selenide.SelenideElement;

public class TransferElementList extends BaseElement{
    private final String transferCode;
    private final String transferAmmount;

    public TransferElementList(SelenideElement element) {
        super(element);
        transferCode = element.getText().split("\n")[0];
        transferAmmount = element.getText().split("\n")[2];
    }
}
