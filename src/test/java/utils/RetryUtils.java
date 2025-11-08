package utils;

import com.codeborne.selenide.Selenide;

import java.util.function.Predicate;
import java.util.function.Supplier;

public class RetryUtils {
    public static <T> T retry(Supplier<T> action, Predicate<T> condition, int maxAttempt,
                              long delay) {
        T result = null;
        int attempt = 0;

        for (int i = 0; i < maxAttempt; i++) {
            result = action.get();

            if (condition.test(result)) {
                return result;
            }

            Selenide.sleep(delay);
        }
        throw new RuntimeException("Retry failed after " + maxAttempt + "attempts");
    }
}
