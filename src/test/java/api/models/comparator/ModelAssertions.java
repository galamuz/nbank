package api.models.comparator;

import org.assertj.core.api.SoftAssertions;
import api.models.BaseModel;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ModelAssertions {
    private static final Map<String, MappingConfig> mappings = new HashMap<>();

    static {
        loadMappings();
    }

    private static void loadMappings() {
        try (InputStream input = ModelAssertions.class
                .getClassLoader()
                .getResourceAsStream("mapping.properties")) {

            if (input == null) {
                throw new IllegalStateException("mapping.properties not found in resources");
            }

            Properties props = new Properties();
            props.load(input);

            for (String key : props.stringPropertyNames()) {
                String value = props.getProperty(key);
                String[] parts = value.split(":");
                if (parts.length != 2) {
                    throw new IllegalArgumentException("Invalid mapping format for: " + key);
                }

                String responseClass = parts[0].trim();
                String[] mappingsArr = parts[1].split(",");

                Map<String, String> fieldMap = new HashMap<>();
                for (String m : mappingsArr) {
                    String[] kv = m.split("=");
                    if (kv.length != 2) continue;
                    fieldMap.put(kv[0].trim(), kv[1].trim());
                }

                mappings.put(key.trim(), new MappingConfig(responseClass, fieldMap));
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load mapping.properties", e);
        }
    }

    public static <REQ extends BaseModel, RESP extends BaseModel> void assertThatModelsMatch(
            SoftAssertions softly,
            REQ expected,
            RESP actual
    ) {
        if (expected == null || actual == null) {
            softly.assertThat(actual).as("Actual model").isNotNull();
            return;
        }

        String requestClassName = expected.getClass().getSimpleName();
        MappingConfig config = mappings.get(requestClassName);

        if (config == null) {
            throw new IllegalArgumentException("No mapping found for: " + requestClassName);
        }

        if (!actual.getClass().getSimpleName().equals(config.responseClass)) {
            throw new IllegalArgumentException(
                    "Expected response of type " + config.responseClass +
                            " but got " + actual.getClass().getSimpleName()
            );
        }

        config.fieldMap.forEach((reqField, respField) -> {
            try {
                Field reqF = expected.getClass().getDeclaredField(reqField);
                Field respF = actual.getClass().getDeclaredField(respField);

                reqF.setAccessible(true);
                respF.setAccessible(true);

                Object expectedValue = reqF.get(expected);
                Object actualValue = respF.get(actual);

                if (expectedValue == null && actualValue == null) return;

                // Если вложенный объект BaseModel, вызываем рекурсивно
                if (expectedValue instanceof BaseModel && actualValue instanceof BaseModel) {
                    assertThatModelsMatch(softly, (BaseModel) expectedValue, (BaseModel) actualValue);
                } else {
                    softly.assertThat(actualValue)
                          .as("Field " + reqField + " vs " + respField)
                          .isEqualTo(expectedValue);
                }
            } catch (NoSuchFieldException e) {
                softly.fail("Field not found: request=" + reqField + ", response=" + respField);
            } catch (IllegalAccessException e) {
                softly.fail("Cannot access field: request=" + reqField + ", response=" + respField);
            }
        });
    }

    private static class MappingConfig {
        String responseClass;
        Map<String, String> fieldMap;

        MappingConfig(String responseClass, Map<String, String> fieldMap) {
            this.responseClass = responseClass;
            this.fieldMap = fieldMap;
        }
    }
}
