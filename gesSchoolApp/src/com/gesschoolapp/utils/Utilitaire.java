package com.gesschoolapp.utils;

import java.time.LocalDate;

public class Utilitaire {

    public static String capitalizeName(String name) {
        String[] words = name.split(" ");
        StringBuilder result = new StringBuilder();

        for (String word : words) {
            if (!word.isEmpty()) {
                result.append(Character.toUpperCase(word.charAt(0)));
                result.append(word.substring(1).toLowerCase());
                result.append(" ");
            }
        }

        return result.toString().trim();
    }

    public static LocalDate dateFornater(String date) {
        // Convert from format 'dd-MM-yyyy' to 'yyyy-MM-dd'
        String[] dateParts = date.split("-");
        return LocalDate.of(Integer.parseInt(dateParts[2]), Integer.parseInt(dateParts[1]), Integer.parseInt(dateParts[0]));
    }
}
