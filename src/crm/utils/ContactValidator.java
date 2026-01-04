package crm.utils;

public class ContactValidator {
    public boolean isValidEmail(String email) {
        if(email == null || email.isEmpty()) {
            return false;
        }
        
        int it = 0;
        int ik = -1;
        int k = 0;
        int t = 0;
        for(int i = 0; i < email.length(); i++) {
            char c = email.charAt(i);
            if(c == '.') {
                it = i;
            }
            if(c == '@') {
                k += 1;
                ik = i;
            }
            if(c == '.' && k == 1 && i > ik) {
                t += 1;
            }
        }
        if(k != 1) {
            return false;
        }
        
        if(t < 1) {
            return false;
        }

        return true;
    }

    public boolean isValidPhone(String phone) {
        if(phone == null || phone.isEmpty()) {
            return false;
        }

        for(int i = 0; i < phone.length(); i++) {
            char c = phone.charAt(i);
            if(!(c >= '0' && c <= '9') && c != '+' && c != '-' && c != ' ' && c != '(' && c != ')') {
                return false;
            }
        }

        int k = 0;
        for(int i = 0; i < phone.length(); i++) {
            char c = phone.charAt(i);
            if(c != '+' && c != '-') {
                k += 1;
            }
        }
        if(!(k >= 10 && k <= 20)) {
                return false;
        }

        return true;

    }

    public boolean isValidContactName(String name) {
        if (name == null || name.isEmpty()) {
            return false;
        }

        if(!(name.length() >= 2 && name.length() <= 50)) {
            return false;
        }

        for (char c : name.toCharArray()) {
            if (!(Character.isLetter(c) || c == ' ' || c == '-')) {
                return false;
            }
        }

        return true;
    }
}