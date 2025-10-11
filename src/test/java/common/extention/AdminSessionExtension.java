package common.extention;


import api.models.LoginUserRequestModel;
import common.annotation.AdminSession;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import ui.pages.BasePage;

public class AdminSessionExtension implements BeforeEachCallback {
    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        //chek that method has extension
        AdminSession annotation = context.getRequiredTestMethod().getAnnotation(AdminSession.class);
        if(annotation != null){
            BasePage.authAsUser(LoginUserRequestModel.loginAdmin());
        }
    }
}
