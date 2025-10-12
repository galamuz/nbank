package utils;

import configs.Configs;

public class Constants {
    public static final String BASE_URL = Configs.getProperty("serverUrl")+Configs.getProperty("apiVersion");
    public static final String ADMIN_USER = "admin";
    public static final String ADMIN_PASSWORD = "admin";
    public static final String LOGIN_URL = "auth/login";
    public static final String USERS_URL = "admin/users";
    public static final String ACCOUNT_URL = "accounts";
    public static final String HEADER_AUTH ="Authorization";
    public static final String TRANSACTION_URL ="/transactions";
}
