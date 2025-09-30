package generation;

import com.mifmif.common.regex.Generex;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.Random;
import java.util.UUID;

public class EntityGenerator {
    private static final Random random = new Random();

    public static <T> T generate(Class<T> clazz) {
        try {
            T instance = clazz.getDeclaredConstructor().newInstance();

            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);

                // Если аннотация есть – генерим по regex
                if (field.isAnnotationPresent(GeneratingRules.class)) {
                    String regex = field.getAnnotation(GeneratingRules.class).regexp();
                    Generex generex = new Generex(regex);
                    field.set(instance, generex.random());
                    continue;
                }

                Class<?> type = field.getType();

                if (type.equals(String.class)) {
                    field.set(instance, "str_" + UUID.randomUUID().toString().substring(0, 6));
                } else if (type.equals(int.class) || type.equals(Integer.class)) {
                    field.set(instance, random.nextInt(100));
                } else if (type.equals(boolean.class) || type.equals(Boolean.class)) {
                    field.set(instance, random.nextBoolean());
                } else if (type.equals(LocalDate.class)) {
                    field.set(instance, LocalDate.of(
                            1970 + random.nextInt(50),
                            1 + random.nextInt(12),
                            1 + random.nextInt(28))
                    );
                }

            }
            return instance;
        } catch (Exception e) {
            throw new RuntimeException("Generation error " + clazz.getSimpleName(), e);
        }
    }

}
