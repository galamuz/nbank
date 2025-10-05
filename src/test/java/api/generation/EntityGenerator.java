package api.generation;

import com.mifmif.common.regex.Generex;

import java.lang.reflect.Field;
import java.math.BigDecimal;
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
                Class<?> type = field.getType();


                if (field.isAnnotationPresent(GeneratingRules.class)) {
                    String regex = field.getAnnotation(GeneratingRules.class).regexp();
                    Generex generex = new Generex(regex);
                    String value = generex.random();

                    if (type.equals(String.class)) {
                        field.set(instance, value);
                    } else if (type.equals(int.class) || type.equals(Integer.class)) {
                        field.set(instance, Integer.parseInt(value));
                    } else if (type.equals(long.class) || type.equals(Long.class)) {
                        field.set(instance, Long.parseLong(value));
                    } else if (type.equals(BigDecimal.class)) {
                        field.set(instance, new BigDecimal(value));
                    } else if (type.equals(double.class) || type.equals(Double.class)) {
                        field.set(instance, Double.parseDouble(value));
                    } else if (type.equals(float.class) || type.equals(Float.class)) {
                        field.set(instance, Float.parseFloat(value));
                    } else {
                        // fallback для других типов
                        field.set(instance, value);
                    }
                    continue;
                }


                if (type.equals(String.class)) {
                    field.set(instance, "str_" + UUID.randomUUID().toString().substring(0, 6));
                } else if (type.equals(int.class) || type.equals(Integer.class)) {
                    field.set(instance, random.nextInt(100));
                } else if (type.equals(long.class) || type.equals(Long.class)) {
                    field.set(instance, random.nextLong(1000)); // ограничим для теста
                } else if (type.equals(BigDecimal.class)) {
                    field.set(instance, BigDecimal.valueOf(random.nextInt(1000)));
                } else if (type.equals(boolean.class) || type.equals(Boolean.class)) {
                    field.set(instance, random.nextBoolean());
                } else if (type.equals(LocalDate.class)) {
                    field.set(instance, LocalDate.of(
                            1970 + random.nextInt(50),
                            1 + random.nextInt(12),
                            1 + random.nextInt(28)
                    ));
                } else {

                    field.set(instance, null);
                }
            }

            return instance;

        } catch (Exception e) {
            throw new RuntimeException("Generation error for " + clazz.getSimpleName(), e);
        }
    }

}
