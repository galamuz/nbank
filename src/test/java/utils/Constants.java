package utils;

import api.configs.Configs;

public class Constants {
    public static final String BASE_URL = Configs.getProperty("serverUrl") + Configs.getProperty("apiVersion");
    public static final String ADMIN_USER = "admin";
    public static final String ADMIN_PASSWORD = "admin";
    public static final String LOGIN_URL = "auth/login";
    public static final String USERS_URL = "admin/users";
    public static final String ACCOUNT_URL = "accounts";
    public static final String HEADER_AUTH = "Authorization";
    public static final String TRANSACTION_URL = "/transactions";
    public static final String CUSTOMER_URL = "customer";
    public static final String PROFILE_URL = "profile";
    public static final String DEPOSIT_URL = "deposit";
    public static final String TRANSFER_URL = "transfer";
    //UI
    public static final String REMOTE_UI_URL = Configs.getProperty("uiRemote");
    public static final String BASE_UI_URL = Configs.getProperty("uiServerUrl");
    public static final String LOGIN_UI_URL = "/login";
    public static final String USER_UI_URL = "/dashboard";
    public static final String TRANSFER_UI_URL = "/transfer";
    public static final String ADMIN_UI_URL = "/admin";
    public static final String DEPOSIT_UI_URL = "/deposit";
    public static final String PROFILE_UI_URL = "/edit-profile";
    public static final int MAX_ATTEMPTS = 10;
    public static final int NEXT_ATTEMPT_DELAY = 500;
    //CHROME
    public static final String CHROME_WINDOWS = Configs.getProperty("chromeOptionsWindows");
    public static final String CHROME_DISABLE = Configs.getProperty("chromeOptionsDisable");
    public static final String CHROME_SANBOX = Configs.getProperty("chromeOptionsSanbox");
}
