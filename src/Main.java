import crm.model.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Шаг 1: Базовая модель CRM ====\n");

        // Создание контактов
        Contact contact1 = new Contact(1, "Ivan Petrov", "ivan@example.com", "+7-900-123-45-67");
        Contact contact2 = new Contact(2, "Maria Smirnova", "maria@example.com", "+7-901-234-56-78");
        Contact contact3 = new Contact(3, "Dmitri Orlov", "dmitri@example.com", "+7-902-345-67-89");

        // Создание лидов
        Lead lead1 = new Lead(1, 1, Status.NEW, "Email", "2025-01-15 10:00");
        Lead lead2 = new Lead(2, 2, Status.IN_PROGRESS, "Phone", "2025-01-15 11:30");
        Lead lead3 = new Lead(3, 3, Status.CLOSED, "Website", "2025-01-14 15:45");

        // Вывод контактов
        System.out.println("Contacts:");
        System.out.println(contact1);
        System.out.println(contact2);
        System.out.println(contact3);

        // Вывод лидов
        System.out.println("\nLeads:");
        System.out.println(lead1);
        System.out.println(lead2);
        System.out.println(lead3);

        // Демонстрация работы геттеров и сеттеров
        System.out.println("\nAvailable Statuses:");
        for (Status s : Status.values()) {
            System.out.println(s);
        }

        // Изменение статуса лида
        System.out.println("\nStatus Change Demo:");
        System.out.println("Original status: " + lead1.getStatus());
        lead1.setStatus(Status.IN_PROGRESS);
        System.out.println("Changed to: " + lead1.getStatus());
    }
}
