package utils;

import common.extention.TimingExtension;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(TimingExtension.class)
public class BaseTest {
    protected SoftAssertions softly;

    @BeforeEach
    public void setUp() {
        softly = new SoftAssertions();
    }

    @AfterEach
    public void tearDown() {
        softly.assertAll();
    }
}
