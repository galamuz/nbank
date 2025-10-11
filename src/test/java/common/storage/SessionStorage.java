package common.storage;

import api.models.CreateUserRequestModel;
import api.requests.steps.UserSteps;

import java.util.ArrayList;
import java.util.LinkedHashMap;


public class SessionStorage {
    private static final SessionStorage INSTANCE = new SessionStorage();

    private SessionStorage(){

    }

    private static SessionStorage getInstance(){
        return INSTANCE;
    }

    private final LinkedHashMap<CreateUserRequestModel, UserSteps> userStepsMap  = new LinkedHashMap<>();

    public static void addUser(CreateUserRequestModel user){
        INSTANCE.userStepsMap.put(user, new UserSteps(user.getUsername(), user.getPassword()));
    }

    public static CreateUserRequestModel getUser(){
        return new ArrayList<>(INSTANCE.userStepsMap.keySet()).get(0);
    }

    public static CreateUserRequestModel getUser(int number){
        return new ArrayList<>(INSTANCE.userStepsMap.keySet()).get(number);
    }

    public static UserSteps getSteps(){
        return new ArrayList<>(INSTANCE.userStepsMap.values()).get(0);
    }

    public static void clear(){
       INSTANCE.userStepsMap.clear();

    }




}
