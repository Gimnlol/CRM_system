package crm.utils;

public class NameFormatter {
    public String formatName(String name) {
        if (name == null || name.isEmpty()) {
            return name;
        }

        name = name.trim();

        while (name.contains("  ")) {
            name = name.replace("  ", " ");
        }

        String[] words = name.split(" ");
        String result = "";
        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            
            if (word.length() == 0) {
                continue;
            }

        String firstLetter = word.substring(0, 1).toUpperCase();
        
        String restOfWord = "";
        if (word.length() > 1) {
            restOfWord = word.substring(1).toLowerCase();
        }

        String formattedWord = firstLetter + restOfWord;
        result = result + formattedWord;
        
        if (i < words.length - 1) {
                result = result + " ";
            }

        }

        return result;
    }

    public String getInitials(String name) {
        if (name == null || name.isEmpty()) {
            return "";
        }

        String formattedName = formatName(name);

        String[] words = formattedName.split(" ");

        String result = "";
        for(int i = 0; i < words.length; i++) {
            String word = words[i];
            word = word.substring(0, 1);
            result = result + word + ".";
        }

        return result;
    }
}