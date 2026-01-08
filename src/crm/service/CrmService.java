package crm.service;

import crm.model.Contact;
import crm.model.Lead;
import crm.model.Status;
import java.util.ArrayList;
import java.util.List;

public class CrmService {
    private List<Contact> contacts;
    private List<Lead> leads;
    private int nextContactId;
    private int nextLeadId;
    
    public CrmService() {
        this.contacts = new ArrayList<>();
        this.leads = new ArrayList<>();
        this.nextContactId = 1;
        this.nextLeadId = 1;
    }
    
    // === Методы для работы с контактами ===
    
    /**
     * Добавляет контакт с автоматическим ID
     */
    public void addContact(Contact contact) {
        Contact newContact = new Contact(
            nextContactId,
            contact.getName(),
            contact.getEmail(),
            contact.getPhone()
        );
        contacts.add(newContact);
        nextContactId++;
    }
    
    /**
     * Добавляет контакт с явным указанием ID (для загрузки из файла)
     */
    public void addContactWithId(Contact contact) {
        contacts.add(contact);
        if (contact.getId() >= nextContactId) {
            nextContactId = contact.getId() + 1;
        }
    }
    
    /**
     * Получает контакт по ID
     */
    public Contact getContact(int id) {
        for (Contact contact : contacts) {
            if (contact.getId() == id) {
                return contact;
            }
        }
        return null;
    }
    
    /**
     * Возвращает всех контактов
     */
    public List<Contact> getAllContacts() {
        return new ArrayList<>(contacts); // Возвращаем копию
    }
    
    /**
     * Поиск контакта по email (точное совпадение)
     */
    public Contact searchByEmail(String email) {
        for (Contact contact : contacts) {
            if (contact.getEmail().equalsIgnoreCase(email)) {
                return contact;
            }
        }
        return null;
    }
    
    /**
     * Поиск контактов по имени (содержит подстроку, без учета регистра)
     */
    public List<Contact> searchByName(String keyword) {
        List<Contact> result = new ArrayList<>();
        String lowerKeyword = keyword.toLowerCase();
        
        for (Contact contact : contacts) {
            if (contact.getName().toLowerCase().contains(lowerKeyword)) {
                result.add(contact);
            }
        }
        return result;
    }
    
    /**
     * Удаляет контакт по ID
     * @return true если контакт был удален, false если не найден
     */
    public boolean removeContact(int id) {
        for (int i = 0; i < contacts.size(); i++) {
            if (contacts.get(i).getId() == id) {
                contacts.remove(i);
                
                // Удаляем все лиды этого контакта
                removeLeadsByContactId(id);
                
                return true;
            }
        }
        return false;
    }
    
    /**
     * Количество контактов
     */
    public int getTotalContacts() {
        return contacts.size();
    }
    
    // === Методы для работы с лидами ===
    
    /**
     * Добавляет лид с автоматическим ID
     */
    public void addLead(Lead lead) {
        Lead newLead = new Lead(
            nextLeadId,
            lead.getContactId(),
            lead.getStatus(),
            lead.getSource(),
            lead.getCreatedAt()
        );
        leads.add(newLead);
        nextLeadId++;
    }
    
    /**
     * Добавляет лид с явным указанием ID (для загрузки из файла)
     */
    public void addLeadWithId(Lead lead) {
        leads.add(lead);
        if (lead.getId() >= nextLeadId) {
            nextLeadId = lead.getId() + 1;
        }
    }
    
    /**
     * Получает лид по ID
     */
    public Lead getLead(int id) {
        for (Lead lead : leads) {
            if (lead.getId() == id) {
                return lead;
            }
        }
        return null;
    }
    
    /**
     * Возвращает всех лидов
     */
    public List<Lead> getAllLeads() {
        return new ArrayList<>(leads); // Возвращаем копию
    }
    
    /**
     * Получает все лиды контакта
     */
    public List<Lead> getLeadsByContact(int contactId) {
        List<Lead> result = new ArrayList<>();
        
        for (Lead lead : leads) {
            if (lead.getContactId() == contactId) {
                result.add(lead);
            }
        }
        return result;
    }
    
    /**
     * Получает лиды по статусу
     */
    public List<Lead> getLeadsByStatus(Status status) {
        List<Lead> result = new ArrayList<>();
        
        for (Lead lead : leads) {
            if (lead.getStatus() == status) {
                result.add(lead);
            }
        }
        return result;
    }
    
    /**
     * Удаляет лид по ID
     * @return true если лид был удален, false если не найден
     */
    public boolean removeLead(int id) {
        for (int i = 0; i < leads.size(); i++) {
            if (leads.get(i).getId() == id) {
                leads.remove(i);
                return true;
            }
        }
        return false;
    }
    
    /**
     * Удаляет все лиды контакта
     */
    private void removeLeadsByContactId(int contactId) {
        List<Lead> leadsToRemove = new ArrayList<>();
        
        for (Lead lead : leads) {
            if (lead.getContactId() == contactId) {
                leadsToRemove.add(lead);
            }
        }
        
        leads.removeAll(leadsToRemove);
    }
    
    /**
     * Количество лидов
     */
    public int getTotalLeads() {
        return leads.size();
    }
    
    /**
     * Получает контакт по ID лида
     */
    public Contact getContactByLeadId(int leadId) {
        Lead lead = getLead(leadId);
        if (lead != null) {
            return getContact(lead.getContactId());
        }
        return null;
    }
    
    /**
     * Обновляет статус лида
     */
    public boolean updateLeadStatus(int leadId, Status newStatus) {
        Lead lead = getLead(leadId);
        if (lead != null) {
            lead.setStatus(newStatus);
            return true;
        }
        return false;
    }
    
    /**
     * Проверяет, существует ли контакт с таким ID
     */
    public boolean contactExists(int contactId) {
        return getContact(contactId) != null;
    }
}