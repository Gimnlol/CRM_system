package crm.utils;

public class PhoneUtils {
    public String normalizePhone(String phone) {
        if (phone == null || phone.isEmpty()) {
            return "";
        }

        String newNumber = "";
        for(int i = 0; i < phone.length(); i++) {
            char c = phone.charAt(i);
            if((c >= '0' && c <= '9') || c == '+') {
                newNumber = newNumber + c;
            }
        }

        return newNumber;
    }

    public String formatPhone(String phone) {
        if (phone == null || phone.isEmpty()) {
            return "";
        }

        String number = "";
        String newNumber = normalizePhone(phone);
        for(int i = 0; i < newNumber.length(); i++) {
            char c = newNumber.charAt(i);

            number = number + c;
            if(i == 1 || i == 4 || i == 7 || i == 9) {
                number = number + '-';
            }
        }

        return number;
    }
}