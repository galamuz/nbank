package ui;

import lombok.Getter;

@Getter
public enum UIAlerts {
    USER_CREATED_SUCCESSFULLY("User created successfully!"),
    USER_CREATED_UNSUCCESSFULLY("username: Username must be between 3 and 15 characters"),
    ACCOUNT_CREATED("New Account Created! Account Number:");

    private final String message;

    UIAlerts(String message) {
        this.message = message;
    }
}
