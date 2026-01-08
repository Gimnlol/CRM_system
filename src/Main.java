import crm.model.*;
import crm.service.*;
import crm.utils.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Шаг 3: Использование коллекций ===\n");
        
        // Создаем экземпляры сервисов
        CrmService crmService = new CrmService();
        CrmStatistics statistics = new CrmStatistics();
        
        // 1. Добавляем 5-7 контактов
        System.out.println("=== Adding Contacts ===");
        Contact[] testContacts = {
            new Contact(0, "Ivan Petrov", "ivan@example.com", "+7-900-111-11-11"),
            new Contact(0, "Maria Smirnova", "maria@example.com", "+7-901-222-22-22"),
            new Contact(0, "Dmitri Orlov", "dmitri@example.com", "+7-902-333-33-33"),
            new Contact(0, "Anna Volkova", "anna@example.com", "+7-903-444-44-44"),
            new Contact(0, "Sergei Kuznetsov", "sergei@example.com", "+7-904-555-55-55"),
            new Contact(0, "Elena Petrova", "elena@example.com", "+7-905-666-66-66"),
            new Contact(0, "Alexei Sokolov", "alexei@example.com", "+7-906-777-77-77")
        };
        
        for (Contact contact : testContacts) {
            crmService.addContact(contact);
            System.out.println("Added: " + contact.getName());
        }
        
        // 2. Добавляем 10-15 лидов с разными статусами и источниками
        System.out.println("\n=== Adding Leads ===");
        
        // Лиды для разных контактов (несколько лидов на контакт)
        Lead[] testLeads = {
            // Контакт 1 (Ivan Petrov) - 3 лида
            new Lead(0, 1, Status.NEW, "Email", "2025-01-15 10:00"),
            new Lead(0, 1, Status.IN_PROGRESS, "Phone", "2025-01-15 11:00"),
            new Lead(0, 1, Status.CLOSED, "Website", "2025-01-14 09:30"),
            
            // Контакт 2 (Maria Smirnova) - 5 лидов (самый активный)
            new Lead(0, 2, Status.NEW, "Phone", "2025-01-16 13:00"),
            new Lead(0, 2, Status.IN_PROGRESS, "Email", "2025-01-16 14:30"),
            new Lead(0, 2, Status.IN_PROGRESS, "Website", "2025-01-16 15:45"),
            new Lead(0, 2, Status.CLOSED, "Email", "2025-01-17 09:15"),
            new Lead(0, 2, Status.CLOSED, "Phone", "2025-01-17 10:30"),
            
            // Контакт 3 (Dmitri Orlov) - 1 лид (наименее активный)
            new Lead(0, 3, Status.NEW, "Website", "2025-01-18 11:00"),
            
            // Контакт 4 (Anna Volkova) - 2 лида
            new Lead(0, 4, Status.IN_PROGRESS, "Email", "2025-01-19 12:00"),
            new Lead(0, 4, Status.CLOSED, "Website", "2025-01-19 13:30"),
            
            // Контакт 5 (Sergei Kuznetsov) - 2 лида
            new Lead(0, 5, Status.NEW, "Phone", "2025-01-20 14:00"),
            new Lead(0, 5, Status.CLOSED, "Email", "2025-01-20 15:45"),
            
            // Контакт 6 (Elena Petrova) - 2 лида
            new Lead(0, 6, Status.IN_PROGRESS, "Website", "2025-01-21 16:00"),
            new Lead(0, 6, Status.CLOSED, "Phone", "2025-01-21 17:30")
            // Контакт 7 (Alexei Sokolov) - 0 лидов
        };
        
        for (Lead lead : testLeads) {
            crmService.addLead(lead);
        }
        System.out.println("Added " + testLeads.length + " leads");
        
        // 3. Выводим все контакты
        System.out.println("\n=== All Contacts (" + crmService.getTotalContacts() + ") ===");
        List<Contact> allContacts = crmService.getAllContacts();
        for (Contact contact : allContacts) {
            System.out.println(contact);
        }
        
        // 4. Выводим все лиды
        System.out.println("\n=== All Leads (" + crmService.getTotalLeads() + ") ===");
        List<Lead> allLeads = crmService.getAllLeads();
        for (Lead lead : allLeads) {
            System.out.println(lead);
        }
        
        // 5. Лиды конкретного контакта (Ivan Petrov, ID=1)
        System.out.println("\n=== Leads for Ivan Petrov (ID=1) ===");
        List<Lead> ivanLeads = crmService.getLeadsByContact(1);
        for (Lead lead : ivanLeads) {
            System.out.println(lead);
        }
        System.out.println("Total: " + ivanLeads.size() + " leads");
        
        // 6. Лиды по статусу NEW
        System.out.println("\n=== Leads with status 'NEW' ===");
        List<Lead> newLeads = crmService.getLeadsByStatus(Status.NEW);
        for (Lead lead : newLeads) {
            System.out.println(lead);
        }
        System.out.println("Total: " + newLeads.size() + " leads");
        
        // 7. Поиск контакта по email
        System.out.println("\n=== Search Contact by Email ===");
        String searchEmail = "maria@example.com";
        Contact foundByEmail = crmService.searchByEmail(searchEmail);
        if (foundByEmail != null) {
            System.out.println("Found: " + foundByEmail);
        } else {
            System.out.println("Contact with email '" + searchEmail + "' not found");
        }
        
        // 8. Поиск контактов по ключевому слову в имени
        System.out.println("\n=== Search Contacts by Name 'Petrov' ===");
        List<Contact> foundByName = crmService.searchByName("Petrov");
        System.out.println("Found " + foundByName.size() + " contacts:");
        for (Contact contact : foundByName) {
            System.out.println(contact);
        }
        
        // 9. Полная статистика
        System.out.println("\n=== Statistics ===");
        System.out.println(statistics.getFullStatisticsReport(crmService));
        
        // 10. Демонстрация удаления
        System.out.println("=== Demonstration of Delete Operations ===");
        
        // Удаляем контакт (должны удалиться и его лиды)
        System.out.println("\nDeleting contact with ID=5...");
        boolean contactDeleted = crmService.removeContact(5);
        System.out.println("Contact deleted: " + contactDeleted);
        System.out.println("Total contacts after deletion: " + crmService.getTotalContacts());
        System.out.println("Total leads after deletion: " + crmService.getTotalLeads());
        
        // Удаляем лид
        System.out.println("\nDeleting lead with ID=3...");
        boolean leadDeleted = crmService.removeLead(3);
        System.out.println("Lead deleted: " + leadDeleted);
        System.out.println("Total leads after deletion: " + crmService.getTotalLeads());
        
        // 11. Обновляем статус лида
        System.out.println("\n=== Updating Lead Status ===");
        System.out.println("Original status of lead 1: " + crmService.getLead(1).getStatus());
        crmService.updateLeadStatus(1, Status.IN_PROGRESS);
        System.out.println("Updated status of lead 1: " + crmService.getLead(1).getStatus());
        
        // 12. Обновленная статистика
        System.out.println("\n=== Updated Statistics ===");
        System.out.println(statistics.getFullStatisticsReport(crmService));
        
        // 13. Дополнительные примеры использования
        System.out.println("=== Additional Examples ===");
        
        // Получаем контакт по ID лида
        System.out.println("\nContact for lead ID=2:");
        Contact contactForLead2 = crmService.getContactByLeadId(2);
        System.out.println(contactForLead2 != null ? contactForLead2.getName() : "Not found");
        
        // Проверяем существование контакта
        System.out.println("\nDoes contact with ID=10 exist? " + crmService.contactExists(10));
        System.out.println("Does contact with ID=2 exist? " + crmService.contactExists(2));
        
        // Лиды по другим статусам
        System.out.println("\nLeads with status 'IN_PROGRESS': " + 
                          crmService.getLeadsByStatus(Status.IN_PROGRESS).size());
        System.out.println("Leads with status 'CLOSED': " + 
                          crmService.getLeadsByStatus(Status.CLOSED).size());
        
        System.out.println("\n=== Шаг 3 завершен успешно ===");
    }
}