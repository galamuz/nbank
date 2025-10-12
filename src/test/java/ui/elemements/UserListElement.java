package ui.elemements;

import com.codeborne.selenide.SelenideElement;
import lombok.Getter;

@Getter
public class UserListElement extends BaseElement{
    private final String userName;
    private final String role;

    public UserListElement(SelenideElement element) {
        super(element);
        userName = element.getText().split("\n")[0];
        role = element.getText().split("\n")[1];
    }
}
