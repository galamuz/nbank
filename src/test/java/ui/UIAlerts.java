package ui;

import lombok.Getter;

@Getter
public enum UIAlerts {
    USER_CREATED_SUCCESSFULLY("User created successfully!"),
    USER_CREATED_UNSUCCESSFULLY("username: Username must be between 3 and 15 characters"),
    ACCOUNT_CREATED("New Account Created! Account Number:"),
    TRANSFER_CREATAD("Successfully transferred"),
    NAME_UPDATED("Name updated successfully!"),
    NAME_NOT_UPDATED("Name must contain two words with letters only"),
    DEPOSIT_CREATED("Successfully deposited");
    private final String message;

    UIAlerts(String message) {
        this.message = message;
    }
}
