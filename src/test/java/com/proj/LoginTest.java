package com.proj;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class LoginTest {
    @Test
    void validate() {
        assertEquals(true, AgeValidator.validate(20));
        assertEquals(false, AgeValidator.validate(10));
    }
}
