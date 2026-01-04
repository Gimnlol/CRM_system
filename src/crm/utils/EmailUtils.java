package crm.utils;

public class EmailUtils {
    public String normalizeEmail(String email) {
        if (email == null) {
            return "";
        }

        return email.trim().toLowerCase();
    }

    public String extractDomain(String email) {
        if (email == null || email.isEmpty()) {
            return "";
        }

        int k = -1;
        String formattedEmail = normalizeEmail(email);
        for(int i = 0; i < formattedEmail.length(); i++) {
            char c = formattedEmail.charAt(i);
            if(c == '@') {
                k = i;
                break;
            }
        }

        if(k == -1) {
                return "";
            }

        String domen = formattedEmail.substring(k+1);

        return domen;
    }
}