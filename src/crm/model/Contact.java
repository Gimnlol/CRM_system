package crm.model;

import crm.utils.NameFormatter;
import crm.utils.EmailUtils;
import crm.utils.ContactValidator;

public class Contact {
    private int id;
    private String name;
    private String email;
    private String phone;
    
    private static final NameFormatter nameFormatter = new NameFormatter();
    private static final EmailUtils emailUtils = new EmailUtils();
    private static final ContactValidator validator = new ContactValidator();

    public Contact(int id, String name, String email, String phone) {
        if (!validator.isValidContactName(name)) {
            throw new IllegalArgumentException("Invalid contact name: " + name);
        }
        
        if (!validator.isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email: " + email);
        }
        
        if (!validator.isValidPhone(phone)) {
            throw new IllegalArgumentException("Invalid phone: " + phone);
        }
        
        this.id = id;
        this.name = nameFormatter.formatName(name);
        this.email = emailUtils.normalizeEmail(email);
        this.phone = phone;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }

    public void setId(int id) { this.id = id; }
    
    public void setName(String name) { 
        if (!validator.isValidContactName(name)) {
            throw new IllegalArgumentException("Invalid contact name: " + name);
        }
        this.name = nameFormatter.formatName(name);
    }
    
    public void setEmail(String email) { 
        if (!validator.isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email: " + email);
        }
        this.email = emailUtils.normalizeEmail(email);
    }
    
    public void setPhone(String phone) { 
        if (!validator.isValidPhone(phone)) {
            throw new IllegalArgumentException("Invalid phone: " + phone);
        }
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Contact{id=" + id + ", name='" + name + "', email='" + email + "', phone='" + phone + "'}";
    }
}