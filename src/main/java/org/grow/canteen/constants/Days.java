package org.grow.canteen.constants;

import org.grow.canteen.commons.exceptions.RestException;

import java.util.Arrays;

public enum Days {
    SUNDAY,
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
    SATURDAY;

    public static String getValues() {
        return Arrays.toString(Days.values());
    }

  public static Days getDaysValue(String input){
        if (isValidDay(input)) {
           return  Days.valueOf(input);
        } else {
           throw new RestException("D001","Days not found");
        }
    }

    private static boolean isValidDay(String userInput) {
        for (Days day : Days.values()) {
            if (day.name().equals(userInput)) {
                return true; // User-provided value matches an enum constant
            }
        }
        return false; // User-provided value does not match any enum constant
    }

}
