package common.extention;


import api.generation.EntityGenerator;
import api.models.CreateUserRequestModel;
import api.models.LoginUserRequestModel;
import api.requests.steps.AdminSteps;
import common.annotation.AdminSession;
import common.annotation.UserSession;
import common.storage.SessionStorage;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import ui.pages.BasePage;

public class UserSessionExtension implements BeforeEachCallback {
    @Override
    public void beforeEach(ExtensionContext context) throws Exception {

        UserSession annotation = context.getRequiredTestMethod().getAnnotation(UserSession.class);
        if(annotation != null){
            SessionStorage.clear();
            CreateUserRequestModel  user = EntityGenerator.generate(CreateUserRequestModel.class);
            SessionStorage.addUser(user);

            AdminSteps.createUser(user);

            LoginUserRequestModel userLogin =  LoginUserRequestModel.builder()
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .build();

            BasePage.authAsUser(userLogin);
        }
    }
}
