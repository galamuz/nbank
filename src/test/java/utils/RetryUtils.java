package utils;

import com.codeborne.selenide.Selenide;
import common.helpers.StepLogger;

import java.util.function.Predicate;
import java.util.function.Supplier;

public class RetryUtils {
    public static <T> T retry(Supplier<T> action, Predicate<T> condition, int maxAttempt,
                              long delay) {
        T result = null;


        for (int i = 0; i < maxAttempt; i++) {
            try {
                result = StepLogger.log("Attempt" + (i + 1), action::get);


                if (condition.test(result)) {
                    return result;
                }
            } catch (Throwable e) {
                System.out.println("Exception " + e.getMessage());
            }


            Selenide.sleep(delay);
        }
        throw new RuntimeException("Retry failed after " + maxAttempt + "attempts");
    }
}
