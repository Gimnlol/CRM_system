import crm.model.*;
import crm.utils.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Шаг 2: Работа со строками и валидация ===\n");
        
        // Создаем экземпляры утилит
        NameFormatter nameFormatter = new NameFormatter();
        ContactValidator validator = new ContactValidator();
        EmailUtils emailUtils = new EmailUtils();
        PhoneUtils phoneUtils = new PhoneUtils();
        DateUtils dateUtils = new DateUtils();
        
        // 1. Демонстрация форматирования имен
        System.out.println("=== Name Formatting ===");
        String rawName = "  ivan petrov  ";
        String formattedName = nameFormatter.formatName(rawName);
        String initials = nameFormatter.getInitials(rawName);
        
        System.out.println("Raw: \"" + rawName + "\"");
        System.out.println("Formatted: \"" + formattedName + "\"");
        System.out.println("Initials: \"" + initials + "\"");
        
        // Пример с несколькими словами
        System.out.println("\nExample with multiple words:");
        System.out.println("Raw: \"  ANNA maria ivanova-smirnova  \"");
        System.out.println("Formatted: \"" + nameFormatter.formatName("  ANNA maria ivanova-smirnova  ") + "\"");
        System.out.println("Initials: \"" + nameFormatter.getInitials("  ANNA maria ivanova-smirnova  ") + "\"");
        
        // 2. Демонстрация валидации email
        System.out.println("\n=== Email Validation ===");
        String[] testEmails = {
            "user@example.com",
            "user@example",
            "userexample.com",
            "user.name@domain.co.uk",
            "@example.com",
            "user@.com",
            "user@domain.",
            null,
            ""
        };
        
        for (String email : testEmails) {
            boolean isValid = validator.isValidEmail(email);
            System.out.println("\"" + email + "\" is valid: " + isValid + 
                             (email != null && !isValid ? " (" + getEmailValidationReason(email) + ")" : ""));
        }
        
        // 3. Демонстрация нормализации email
        System.out.println("\n=== Email Normalization ===");
        String rawEmail = "  USER@EXAMPLE.COM  ";
        String normalizedEmail = emailUtils.normalizeEmail(rawEmail);
        String domain = emailUtils.extractDomain(rawEmail);
        
        System.out.println("Raw: \"" + rawEmail + "\"");
        System.out.println("Normalized: \"" + normalizedEmail + "\"");
        System.out.println("Domain: \"" + domain + "\"");
        
        // 4. Демонстрация валидации телефона
        System.out.println("\n=== Phone Validation ===");
        String[] testPhones = {
            "+7-900-123-45-67",
            "+7-900-12-34",
            "8 (900) 123-45-67",
            "abc-def-ghij",
            "123",
            "123456789012345678901", // 21 цифра
            null,
            ""
        };
        
        for (String phone : testPhones) {
            boolean isValid = validator.isValidPhone(phone);
            System.out.println("\"" + phone + "\" is valid: " + isValid);
        }
        
        // 5. Демонстрация нормализации телефона
        System.out.println("\n=== Phone Normalization ===");
        String rawPhone = "+7-900-123-45-67";
        String normalizedPhone = phoneUtils.normalizePhone(rawPhone);
        String formattedPhone = phoneUtils.formatPhone(rawPhone);
        
        System.out.println("Raw: \"" + rawPhone + "\"");
        System.out.println("Normalized: \"" + normalizedPhone + "\"");
        System.out.println("Formatted: \"" + formattedPhone + "\"");
        
        // Другие примеры
        System.out.println("\nOther examples:");
        System.out.println("8(900)1234567 → " + phoneUtils.normalizePhone("8(900)1234567"));
        System.out.println("79001234567 → " + phoneUtils.formatPhone("79001234567"));
        
        // 6. Демонстрация валидации имени контакта
        System.out.println("\n=== Contact Validation ===");
        String[] testNames = {
            "Ivan Petrov",
            "I",
            "Ivan@123",
            "Анна-Мария", // с дефисом
            "John O'Connor", // с апострофом (не пройдет валидацию)
            "A".repeat(51), // 51 символ
            null,
            ""
        };
        
        for (String name : testNames) {
            boolean isValid = validator.isValidContactName(name);
            System.out.println("\"" + (name != null && name.length() > 20 ? name.substring(0, 20) + "..." : name) + 
                             "\" is valid: " + isValid);
        }
        
        // 7. Демонстрация работы с датами
        System.out.println("\n=== Date Validation ===");
        String[] testDates = {
            "2025-01-15 10:00",
            "2025-13-15 10:00", // неверный месяц
            "2025-01-32 10:00", // неверный день
            "2025-01-15 25:00", // неверный час
            "2025-01-15",
            "15-01-2025 10:00",
            null,
            ""
        };
        
        for (String date : testDates) {
            boolean isValid = dateUtils.isValidDateTime(date);
            System.out.println("\"" + date + "\" is valid: " + isValid);
        }
        
        // 8. Демонстрация создания объектов с валидацией
        System.out.println("\n=== Creating Objects with Validation ===");
        
        try {
            // Правильные данные
            Contact goodContact = new Contact(1, "Ivan Petrov", "ivan@example.com", "+7-900-123-45-67");
            System.out.println("Successfully created: " + goodContact);
            
            Lead goodLead = new Lead(1, 1, Status.NEW, "Email", "2025-01-15 10:00");
            System.out.println("Successfully created: " + goodLead);
            
            // Неправильные данные (будут исключения)
            System.out.println("\nTrying to create with invalid data...");
            
            try {
                Contact badContact = new Contact(2, "I", "invalid-email", "123");
                System.out.println("Created (should not happen): " + badContact);
            } catch (IllegalArgumentException e) {
                System.out.println("Expected error: " + e.getMessage());
            }
            
            try {
                Lead badLead = new Lead(2, 2, Status.IN_PROGRESS, "Phone", "invalid-date");
                System.out.println("Created (should not happen): " + badLead);
            } catch (IllegalArgumentException e) {
                System.out.println("Expected error: " + e.getMessage());
            }
            
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }
        
        // 9. Демонстрация сеттеров с валидацией
        System.out.println("\n=== Setters with Validation ===");
        Contact contact = new Contact(3, "Maria Ivanova", "maria@example.com", "+7-901-234-56-78");
        
        try {
            contact.setEmail("new-email@domain.com");
            System.out.println("Email updated successfully");
            
            contact.setEmail("invalid-email"); // Это вызовет исключение
        } catch (IllegalArgumentException e) {
            System.out.println("Cannot set invalid email: " + e.getMessage());
        }
        
        System.out.println("\n=== Step 2 Completed Successfully ===");
    }
    
    private static String getEmailValidationReason(String email) {
        if (email == null) return "null";
        if (email.isEmpty()) return "empty";
        
        if (!email.contains("@")) return "no @ symbol";
        
        int atIndex = email.indexOf('@');
        if (atIndex == 0) return "@ at beginning";
        if (atIndex == email.length() - 1) return "@ at end";
        
        if (!email.substring(atIndex).contains(".")) return "no dot after @";
        
        int dotIndex = email.indexOf('.', atIndex);
        if (dotIndex == atIndex + 1) return "dot immediately after @";
        if (dotIndex == email.length() - 1) return "dot at end";
        
        return "unknown reason";
    }
}