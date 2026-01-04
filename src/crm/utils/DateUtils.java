package crm.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateUtils {
    
    public boolean isValidDateTime(String datetime) {
        try {
            LocalDateTime.parse(datetime, 
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    public String getCurrentDateTime() {
        return LocalDateTime.now().format(
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
}