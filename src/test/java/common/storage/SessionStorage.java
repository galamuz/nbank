package common.storage;

import api.models.CreateUserRequestModel;
import api.requests.steps.UserSteps;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Optional;


public class SessionStorage {
    private static final ThreadLocal<SessionStorage> INSTANCE = ThreadLocal.withInitial(SessionStorage::new);
    private final LinkedHashMap<CreateUserRequestModel, UserSteps> userStepsMap = new LinkedHashMap<>();

    private SessionStorage() {

    }

    private static SessionStorage getInstance() {
        return INSTANCE.get();
    }

    public static void addUser(CreateUserRequestModel user) {
        INSTANCE.get().userStepsMap.put(user, new UserSteps(user.getUsername(), user.getPassword()));
    }

    public static CreateUserRequestModel getUser() {
        return new ArrayList<>(INSTANCE.get().userStepsMap.keySet()).get(0);
    }

    public static CreateUserRequestModel getUser(int number) {
        return new ArrayList<>(INSTANCE.get().userStepsMap.keySet()).get(number);
    }

    public static UserSteps getSteps() {
        return new ArrayList<>(INSTANCE.get().userStepsMap.values()).get(0);
    }

    public static void clear() {
        INSTANCE.get().userStepsMap.clear();

    }

    public static Optional<CreateUserRequestModel> getFirstUser() {
        return INSTANCE.get().userStepsMap.keySet().stream().findFirst();
    }


}
