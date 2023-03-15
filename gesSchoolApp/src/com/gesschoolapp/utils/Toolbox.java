package com.gesschoolapp.utils;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.gesschoolapp.Exceptions.DAOException;
import com.gesschoolapp.db.DAOClassesImpl.PaiementDAOImp;
import com.gesschoolapp.models.paiement.Paiement;
import com.gesschoolapp.models.paiement.Rubrique;
import com.gesschoolapp.models.student.Apprenant;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.time.temporal.ChronoField;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Toolbox {

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
        String regex = "^[a-zA-Z0-9_!#$%&amp;'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&amp;'*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(emailAdressen);
        return matcher.matches();
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

    public static String duplicataText(String exception) {
        if (exception.contains("la clef 'email'") && exception.contains("la clef 'numero'")) {
            return "Cet email et ce numéro sont déjà utilisés";
        } else if (exception.contains("la clef 'numero'")) {
            return "Ce numéro est déjà utilisé";
        }
        return "Cet email est déjà utilisé";
    }

    public static List<Rubrique> getRubriques(){
        List<Rubrique> list = new ArrayList<>();
        list.add(new Rubrique(2, "scolarite", 0));
        list.add(new Rubrique(4, "tenue", 0));
        list.add(new Rubrique(7, "inscription", 0));

        return list;
    }

    public static boolean checkNoteFormat(final String input) {
        // Compile regular expression
        final Pattern pattern = Pattern.compile("([+-]?(?=\\.\\d|\\d)(?:\\d+)?(?:\\.?\\d*))(?:[Ee]([+-]?\\d+))?/20", Pattern.CASE_INSENSITIVE);
        // Match regex against input
        final Matcher matcher = pattern.matcher(input);
        // Use results...
        return matcher.matches();
    }


    public static String getEnv() {
        try {
            // Open file
            FileInputStream fos = new FileInputStream(".env");
            // Read file
            byte[] data = new byte[fos.available()];
            fos.read(data);
            fos.close();
            // Convert to string
            String test = new String(data, StandardCharsets.UTF_8);
            return test.split("=")[1];
        } catch (Exception e) {
            System.out.println("Erreur getenv: " + e.getMessage());
        }
        return null;
    }

    public static String generateRandomPassword() {
        // Generate random password with 12 characters
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()";
        StringBuilder password = new StringBuilder();
        SecureRandom random = new SecureRandom();
        while (password.length() < 12) {
            int index = (int) (random.nextFloat() * chars.length());
            password.append(chars.charAt(index));
        }

        return password.toString();
    }
    public static String generateSecurePassword(String password) {
        if (password == null || password.isEmpty())
            return null;
        return BCrypt.withDefaults().hashToString(12, password.toCharArray());
    }

    public static boolean verifyPassword(String password, String storedPasswordHash) {
        BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), storedPasswordHash);
        return result.verified;
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

    public static void pdfToImage(String path, String filename) {
        try {
            PDDocument doc = Loader.loadPDF(new File(path));
            PDFRenderer pdfRenderer = new PDFRenderer(doc);
            for(int page = 0;page<doc.getNumberOfPages();++page){
                BufferedImage bim = pdfRenderer.renderImageWithDPI(page,300, ImageType.RGB);
                ImageIOUtil.writeImage(bim, filename,300);
            }
            doc.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public static String generateRandomString(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder(length);
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(chars.length());
            sb.append(chars.charAt(index));
        }
        return "RCU" + sb;
    }

    public static String[] getBulletinImgPaths(Apprenant apprenant) throws RuntimeException {

        String[] filepaths = new String[2];

        File bulletinSemestre1 = new File("storage/bulletins/imgs/" + apprenant.getFullName().replace(" ", "_") + "_bulletin_semestre_1.png");
        File bulletinSemestre2 = new File("storage/bulletins/imgs/" + apprenant.getFullName().replace(" ", "_") + "_bulletin_semestre_2.png");
        System.out.println(bulletinSemestre1.getPath());
        System.out.println(bulletinSemestre2.getPath());
        if (!bulletinSemestre1.exists() && !bulletinSemestre2.exists()) {
            throw new RuntimeException(apprenant.getFullName() + " n'a aucun bulletin.");
        } else {
            if (bulletinSemestre1.exists()) {
                filepaths[0] = "storage/bulletins/imgs/" + apprenant.getFullName().replace(" ", "_") + "_bulletin_semestre_1.png";
            }
            if (bulletinSemestre2.exists()) {
                filepaths[1] = "storage/bulletins/imgs/" + apprenant.getFullName().replace(" ", "_") + "_bulletin_semestre_2.png";
            }
            return filepaths;
        }
    }

    public static String getCertificatsImgPath(Apprenant apprenant) {
        File file = new File("storage/certificats/imgs/" + apprenant.getFullName().replace(" ", "_") + "_" + LocalDate.now() + ".png");
        if (!file.exists()) {
            throw new RuntimeException("Il n'y a pas de certificat pour " + apprenant.getFullName() + ".");
        } else {
            return "storage/certificats/imgs/" + apprenant.getFullName().replace(" ", "_") + "_" + LocalDate.now() + ".png";
        }
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


//    public static List<Paiement> paiementsJournalier(String date) throws MismatchException {
//        // Verify if the date is in the correct format
//        if (!date.matches("\\d{2}-\\d{2}-\\d{4}")) {
//            throw new MismatchException("La date doit être au format 'dd-MM-yyyy'");
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
