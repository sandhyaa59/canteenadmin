package org.grow.canteen.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Helper {

   public static LocalDateTime parseDateTime(String dateTimeString) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");
        return LocalDateTime.parse(dateTimeString, formatter);
    }
}
