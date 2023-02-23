package com.gesschoolapp.utils;

import com.gesschoolapp.Exceptions.DAOException;
import com.gesschoolapp.Exceptions.Mismatch;
import com.gesschoolapp.db.DAOClassesImpl.PaiementDAOImp;
import com.gesschoolapp.models.paiement.Paiement;
import com.gesschoolapp.models.paiement.Rubrique;
import com.mysql.cj.protocol.a.authentication.Sha256PasswordPlugin;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Toolbox {

    private static final String ALGORITHM = "SHA-256";

    public static void setTimeout(Runnable runnable, int delay) {
        new Thread(() -> {
            try {
                Thread.sleep(delay);
                runnable.run();
            } catch (Exception e) {
                System.err.println(e);
            }
        }).start();
    }


    public static boolean phoneNumberFormatChecker(String numberAdressen) {
        Pattern pattern = Pattern.compile("(77|78|75|70|76)[0-9]{7}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(numberAdressen);
        return matcher.find();
    }

    public static boolean emailFormatChecker(String emailAdressen) {
        Pattern pattern = Pattern.compile("^[A-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[A-Z0-9_!#$%&'*+/=?`{|}~^-]+↵\n" +
                ")*@[A-Z0-9-]+(?:\\.[A-Z0-9-]+)*$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(emailAdressen);
        return matcher.find();
    }

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

    public static List<Rubrique> getRubriques(){
        List<Rubrique> list = new ArrayList<>();
        list.add(new Rubrique(2, "scolarite", 0));
        list.add(new Rubrique(3, "album", 0));
        list.add(new Rubrique(4, "tenue", 0));
        list.add(new Rubrique(7, "inscription", 0));

        return list;
    }

    public static String generateSecurePassword(String password) {
        byte[] salt = generateSalt();
        byte[] passwordHash = hashPassword(password, salt);
        return bytesToHex(passwordHash) + ":" + bytesToHex(salt);
    }

    public static boolean verifyPassword(String password, String storedPasswordHash) {
        String[] parts = storedPasswordHash.split(":");
        byte[] passwordHash = hashPassword(password, hexToBytes(parts[1]));
        return bytesToHex(passwordHash).equals(parts[0]);
    }

    private static byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }

    private static byte[] hashPassword(String password, byte[] salt) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(ALGORITHM);
            messageDigest.reset();
            messageDigest.update(salt);
            byte[] passwordBytes = password.getBytes("UTF-8");
            return messageDigest.digest(passwordBytes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(String.format("%02x", b));
        }
        return result.toString();
    }

    private static byte[] hexToBytes(String hex) {
        byte[] bytes = new byte[hex.length() / 2];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return bytes;
    }

    public static List<Paiement> paiementsJournalier(LocalDate date){
        PaiementDAOImp paiementDAOImp = new PaiementDAOImp();
        try {
            List<Paiement> list = paiementDAOImp.getList();
            // Verify if the paiement date is equal to the date passed as parameter
            list.removeIf(paiement -> !paiement.getDate().equals(date));
            return list;
        } catch (DAOException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }
    public static List<Paiement> paiementsHebdomadaire(LocalDate date){
        PaiementDAOImp paiementDAOImp = new PaiementDAOImp();
        try {
            List<Paiement> list = paiementDAOImp.getList();
            // Verify if the paiement date is equal to the date passed as parameter
            list.removeIf(paiement -> paiement.getDate().get(ChronoField.ALIGNED_WEEK_OF_YEAR) != date.get(ChronoField.ALIGNED_WEEK_OF_YEAR));
            return list;
        } catch (DAOException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public static List<Paiement> paiementsMensuel(String month, String year) {

        PaiementDAOImp paiementDAOImp = new PaiementDAOImp();
        try {
            List<Paiement> list = paiementDAOImp.getList();
            // Verify if the paiement date month is equal to the month passed as parameter
            list.removeIf(paiement -> !paiement.getDate().getMonth().getDisplayName(TextStyle.FULL, Locale.FRENCH).equalsIgnoreCase(month) || !Objects.equals(paiement.getDate().getYear(), Integer.parseInt(year)));
            return list;
        } catch (DAOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static List<Paiement> paiementsAnnuel(String year) {
        PaiementDAOImp paiementDAOImp = new PaiementDAOImp();
        try {
            List<Paiement> list = paiementDAOImp.getList();
            // Verify if the paiement date month is equal to the month passed as parameter
            list.removeIf(paiement -> !Objects.equals(paiement.getDate().getYear(), Integer.parseInt(year)));
            return list;
        } catch (DAOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}

//    public static List<Paiement> paiementsMensuel(LocalDate date){
//        PaiementDAOImp paiementDAOImp = new PaiementDAOImp();
//        try {
//            List<Paiement> list = paiementDAOImp.getList();
//            // Verify if the paiement date is equal to the date passed as parameter
//            list.removeIf(paiement -> paiement.getDate().getMonthValue() != date.getMonthValue());
//            return list;
//        } catch (DAOException e) {
//            System.out.println(e.getMessage());
//        }
//        return null;
//    }
//    public static List<Paiement> paiementsAnnuel(LocalDate date){
//        PaiementDAOImp paiementDAOImp = new PaiementDAOImp();
//        try {
//            List<Paiement> list = paiementDAOImp.getList();
//            // Verify if the paiement date is equal to the date passed as parameter
//            list.removeIf(paiement -> paiement.getDate().getYear() != date.getYear());
//            return list;
//        } catch (DAOException e) {
//            System.out.println(e.getMessage());
//        }
//        return null;
//    }


//    public static List<Paiement> paiementsJournalier(String date) throws Mismatch {
//        // Verify if the date is in the correct format
//        if (!date.matches("\\d{2}-\\d{2}-\\d{4}")) {
//            throw new Mismatch("La date doit être au format 'dd-MM-yyyy'");
//        }
//        // Parse the date to LocalDate
//        LocalDate localDate = dateFornater(date);
//
//        PaiementDAOImp paiementDAOImp = new PaiementDAOImp();
//        try {
//            List<Paiement> list = paiementDAOImp.getList();
//            // Verify if the paiement date is equal to the date passed as parameter
//            list.removeIf(paiement -> !paiement.getDate().equals(localDate));
//            return list;
//        } catch (DAOException e) {
//            System.out.println(e.getMessage());
//        }
//        return null;
//    }
