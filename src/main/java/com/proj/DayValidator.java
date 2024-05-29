package com.proj;

public class DayValidator {
    public static boolean validate(int days){
        if (days < 0) {
            return false;
        }
        return true;
    }
}
