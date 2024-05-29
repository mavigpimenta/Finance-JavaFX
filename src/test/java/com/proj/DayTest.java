package com.proj;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class DayTest {
    @Test
    void validate() {
        assertEquals(true, DayValidator.validate(20));
        assertEquals(false, DayValidator.validate(-12));
    }
}
