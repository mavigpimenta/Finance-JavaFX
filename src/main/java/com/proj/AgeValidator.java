package com.proj;

public class AgeValidator {
    public static Boolean validate(int age) {
        if (age < 18) {
            return false;
        } else {
            return true;
        }
    }
}
