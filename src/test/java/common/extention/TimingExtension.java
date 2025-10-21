package common.extention;

import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class TimingExtension implements BeforeEachCallback, AfterEachCallback {
    private final Map<String, Long> startTime = new HashMap<>();
    private final Path logFile = Paths.get("test-timings-" + LocalDate.now() + ".log");

    @Override
    public void beforeEach(ExtensionContext context) {
        String testName = context.getRequiredTestClass().getName() + "." + context.getRequiredTestMethod().getName();
        startTime.put(testName, System.currentTimeMillis());
        writeToFile(Thread.currentThread().getName() + " START: " + testName);
    }

    @Override
    public void afterEach(ExtensionContext context) {
        String testName = context.getRequiredTestClass().getName() + "." + context.getRequiredTestMethod().getName();
        long duration = System.currentTimeMillis() - startTime.get(testName);
        writeToFile(Thread.currentThread().getName() + " END: " + testName +
                " duration: " + duration + " ms");
    }

    private synchronized void writeToFile(String text) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFile.toFile(), true))) {
            writer.write(text);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
