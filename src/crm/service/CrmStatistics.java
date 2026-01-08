package crm.service;

import crm.model.Contact;
import crm.model.Lead;
import crm.model.Status;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CrmStatistics {
    
    /**
     * Количество контактов
     */
    public int getTotalContacts(CrmService service) {
        return service.getTotalContacts();
    }
    
    /**
     * Количество лидов
     */
    public int getTotalLeads(CrmService service) {
        return service.getTotalLeads();
    }
    
    /**
     * Количество лидов по статусу
     */
    public int getLeadCountByStatus(CrmService service, Status status) {
        return service.getLeadsByStatus(status).size();
    }
    
    /**
     * Распределение лидов по статусам
     */
    public Map<Status, Integer> getLeadDistributionByStatus(CrmService service) {
        Map<Status, Integer> distribution = new HashMap<>();
        
        for (Status status : Status.values()) {
            int count = getLeadCountByStatus(service, status);
            distribution.put(status, count);
        }
        
        return distribution;
    }
    
    /**
     * Контакт с наибольшим количеством лидов
     */
    public Contact getMostActiveContact(CrmService service) {
        List<Contact> contacts = service.getAllContacts();
        
        if (contacts.isEmpty()) {
            return null;
        }
        
        Contact mostActive = null;
        int maxLeads = -1;
        
        for (Contact contact : contacts) {
            int leadCount = service.getLeadsByContact(contact.getId()).size();
            
            if (leadCount > maxLeads) {
                maxLeads = leadCount;
                mostActive = contact;
            }
        }
        
        return mostActive;
    }
    
    /**
     * Контакт с наименьшим количеством лидов
     */
    public Contact getLeastActiveContact(CrmService service) {
        List<Contact> contacts = service.getAllContacts();
        
        if (contacts.isEmpty()) {
            return null;
        }
        
        Contact leastActive = null;
        int minLeads = Integer.MAX_VALUE;
        
        for (Contact contact : contacts) {
            int leadCount = service.getLeadsByContact(contact.getId()).size();
            
            if (leadCount < minLeads) {
                minLeads = leadCount;
                leastActive = contact;
            }
        }
        
        return leastActive;
    }
    
    /**
     * Самый частый источник лидов
     */
    public String getMostUsedSource(CrmService service) {
        List<Lead> leads = service.getAllLeads();
        
        if (leads.isEmpty()) {
            return "No leads";
        }
        
        Map<String, Integer> sourceCount = new HashMap<>();
        
        // Считаем количество лидов по источникам
        for (Lead lead : leads) {
            String source = lead.getSource();
            sourceCount.put(source, sourceCount.getOrDefault(source, 0) + 1);
        }
        
        // Находим источник с максимальным количеством
        String mostUsedSource = null;
        int maxCount = -1;
        
        for (Map.Entry<String, Integer> entry : sourceCount.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                mostUsedSource = entry.getKey();
            }
        }
        
        return mostUsedSource;
    }
    
    /**
     * Количество лидов у самого активного контакта
     */
    public int getLeadCountForMostActiveContact(CrmService service) {
        Contact mostActive = getMostActiveContact(service);
        if (mostActive != null) {
            return service.getLeadsByContact(mostActive.getId()).size();
        }
        return 0;
    }
    
    /**
     * Количество лидов у наименее активного контакта
     */
    public int getLeadCountForLeastActiveContact(CrmService service) {
        Contact leastActive = getLeastActiveContact(service);
        if (leastActive != null) {
            return service.getLeadsByContact(leastActive.getId()).size();
        }
        return 0;
    }
    
    /**
     * Количество использований самого частого источника
     */
    public int getUsageCountForMostUsedSource(CrmService service) {
        List<Lead> leads = service.getAllLeads();
        
        if (leads.isEmpty()) {
            return 0;
        }
        
        Map<String, Integer> sourceCount = new HashMap<>();
        
        for (Lead lead : leads) {
            String source = lead.getSource();
            sourceCount.put(source, sourceCount.getOrDefault(source, 0) + 1);
        }
        
        int maxCount = -1;
        for (int count : sourceCount.values()) {
            if (count > maxCount) {
                maxCount = count;
            }
        }
        
        return maxCount;
    }
    
    /**
     * Полная статистика в виде строки
     */
    public String getFullStatisticsReport(CrmService service) {
        StringBuilder report = new StringBuilder();
        report.append("=== CRM Statistics ===\n");
        
        report.append("Total contacts: ").append(getTotalContacts(service)).append("\n");
        report.append("Total leads: ").append(getTotalLeads(service)).append("\n");
        
        report.append("Leads by status:\n");
        Map<Status, Integer> distribution = getLeadDistributionByStatus(service);
        for (Map.Entry<Status, Integer> entry : distribution.entrySet()) {
            report.append("    ").append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
        
        Contact mostActive = getMostActiveContact(service);
        if (mostActive != null) {
            int mostActiveCount = getLeadCountForMostActiveContact(service);
            report.append("Most active contact: ").append(mostActive.getName())
                  .append(" (").append(mostActiveCount).append(" leads)\n");
        }
        
        Contact leastActive = getLeastActiveContact(service);
        if (leastActive != null) {
            int leastActiveCount = getLeadCountForLeastActiveContact(service);
            report.append("Least active contact: ").append(leastActive.getName())
                  .append(" (").append(leastActiveCount).append(" leads)\n");
        }
        
        String mostUsedSource = getMostUsedSource(service);
        int mostUsedCount = getUsageCountForMostUsedSource(service);
        report.append("Most used source: ").append(mostUsedSource)
              .append(" (").append(mostUsedCount).append(" occurrences)\n");
        
        return report.toString();
    }
}