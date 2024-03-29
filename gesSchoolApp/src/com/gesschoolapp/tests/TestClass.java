package com.gesschoolapp.tests;
import com.gesschoolapp.Exceptions.DAOException;
import com.gesschoolapp.Exceptions.MismatchException;
import com.gesschoolapp.db.DAOClassesImpl.*;
import com.gesschoolapp.docmaker.PDFGenerator;
import com.gesschoolapp.models.actions.Action;
import com.gesschoolapp.models.actions.Notification;
import com.gesschoolapp.models.matieres.Module;
import com.gesschoolapp.models.paiement.Paiement;
import com.gesschoolapp.models.users.Utilisateur;
import com.gesschoolapp.models.users.concrete.Caissier;
import com.gesschoolapp.serial.ArchiveManager;
import com.gesschoolapp.Exceptions.CSVException;
import com.gesschoolapp.db.DBManager;
import com.gesschoolapp.gescsv.ApprenantsCSV;
import com.gesschoolapp.gescsv.NotesCSV;
import com.gesschoolapp.models.classroom.Classe;
import com.gesschoolapp.models.classroom.Classes;
import com.gesschoolapp.models.matieres.Note;
import com.gesschoolapp.models.student.Apprenant;
import com.gesschoolapp.utils.Toolbox;

import java.io.File;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;

public class TestClass {
    public static void main(String[] args) {

//        System.out.println("RCU" + (int) (Instant.now().getEpochSecond()/10000));
//        testLastView();
//        testGetClasses();
//        testNoteCSV();
//        testApprenantCSV();
//        testCSVWriter();
//        testUpdateNote(14);
        testDeleteApprenant();
//        testDeleteApprenant(36);
//        testDeleteApprenant(37);
//        testCreateApprenant();
//        System.out.println(ListRubriques.getRubriques());
//        testReadClasse(15);
//        testGetPaiements();
//        testSearchApprenant();
//        testPaiement();
//        testCheckPaiement();
//        testCreateApprenant();
//        System.out.println(LocalDate.now());
//        testGetAnnees();
//        testCreateUser();
//        testGenerateCertificat();
//        System.out.println(Date.from(Instant.now()).toInstant()
//                .atZone(ZoneId.systemDefault())
//                .toLocalDateTime());
//        testCancelActions();
//        System.out.println( + LocalDateTime.now().getHour() + ":" + LocalDateTime.now().getMinute() );
//        testCreateModule();
//        testGetNotifs();
//        System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYY-MM-dd à HH:mm")));
//        testGetUtilisateurs();

//        String pwd = Toolbox.generateSecurePassword("wissam");
//        System.out.println(Toolbox.generateSecurePassword("wissam"));
//        System.out.println(Toolbox.verifyPassword("wissam", pwd));
//        ApprenantDAOImp apprenantDAOImp = new ApprenantDAOImp();
//        Apprenant apprenant = new Apprenant();
//        try {
//            apprenant = apprenantDAOImp.read(23);
//        } catch (DAOException e) {
//            e.printStackTrace();
//        }

//        apprenant.setNom("Wissam");

//        System.out.println(apprenant);
//        String[] test = Toolbox.getBulletinImgPaths(apprenant);
//        for (String s : test) {
//            System.out.println(s);
//        }
//        testGetApprenantsOfClass();

//        System.out.println("Systeme d'exploitation: " + System.getProperty("os.name"));
//        System.out.println(Toolbox.verifyPassword("NCo0)dh7Ac7j", "$2a$12$u1yzk0HiNayeUPQzzEuh9uXbgUYfpKeVTwwLXS1gyuQ9Te1sgyY9K"));
//    testApprenantCSV();
        System.out.println(Toolbox.emailFormatChecker("azimari.toure.ikbal@gmail.com"));
    }

    public static void testGenerateCertificat(){
        try {
            Apprenant apprenant = new ApprenantDAOImp().read(38);
            PDFGenerator.cerficatScolariteGenerator(apprenant);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public static void testCreateUser(){
        try {
            Utilisateur utilisateur = new Caissier("Marshall", "Matthers", "marshall@mail.com",
                    "", "785682536");
            new UserDAOImp().create(utilisateur, "Marshall");
            System.out.println(new UserDAOImp().search("Marshall"));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }


    public static void testCreateModule(){
        try {
            Module module = new Module("TP Couple", "3eme", 2);

            System.out.println(new ModuleDAOImp().create(module, "Seishiro"));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public static void testGetUtilisateurs(){
        try {
            List<Utilisateur> utilisateurs = new UserDAOImp().getList();
            System.out.println(utilisateurs);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }


    public static void testSearchApprenant(){
        try {
            List<Apprenant> apprenant = new ApprenantDAOImp().search("Ikbal");
            System.out.println(apprenant);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public static void testGetNotifs(){
        try {
            Notification notif = new UserDAOImp().readNotif(26);
            notif.setSeen();
            System.out.println(notif);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

    }

    public static void testParsePDFToImage(){
//        PDDocument.
    }

    public static void testCancelActions(){
        try{
            List<Action> actions = new ActionDAOImp().getActions();
            System.out.println(actions);
            //Cancel action of id 56
            Action action = new ActionDAOImp().read(56);
            action.cancelAction("The admin who canceled the action");
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public static void testGetAnnees(){
        try {
            List<String> annees = new PaiementDAOImp().getAnnees();
            System.out.println(annees);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public static void testDeleteApprenant(){
        try {
            int id = 38;
            ApprenantDAOImp apprenantDAOImp = new ApprenantDAOImp();
            apprenantDAOImp.delete(id, "Rick Astley");
            System.out.println(apprenantDAOImp.read(id));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public static void testCheckPaiement(){
        try {
            Apprenant apprenant = new ApprenantDAOImp().read(40);
            boolean check = new ClasseDAOImp().read(15).isCurrentEcheancePaid(apprenant);
            System.out.println(new ClasseDAOImp().read(15).getCurrentEcheance());
            System.out.println(check);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }


    public static void testPaiement(){
        try {
            PaiementDAOImp paiementDAOImp = new PaiementDAOImp();
            Paiement paiement = new Paiement();
            paiement.setMontant(22000);
            paiement.setApprenant(new ApprenantDAOImp().read(38));
            paiement.setRubrique("scolarite");
            paiement.setDate(LocalDate.now());
            paiement.setClasse(new ApprenantDAOImp().read(38).getClasse());
            paiement.setCaissier("Bob Marley");
            paiement.setObservation("Paiement de scolarite");

            System.out.println(paiementDAOImp.create(paiement, paiement.getCaissier()));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }


    public static void testUpdateNote(int idNote){
        try {
            Note note = new NoteDAOImp().read(idNote);
            //before update
            System.out.println(note);
            note.setNote(0);
            System.out.println("-------------------");
            new NoteDAOImp().update(note, 1, "Seishiro Itachi");
            //after update
            System.out.println(note);
        } catch (DAOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void testLastView(){
        try {
            ClasseDAOImp classeDAOImp = new ClasseDAOImp();
            Classe classe = classeDAOImp.read(18);
            System.out.println("Before set : \n" + classe);
            classeDAOImp.setLastView(classe);
            System.out.println("\n-------------------\nAfter set : \n" + classe);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public static void testCreateApprenant(){
        try {
            Apprenant apprenant = new Apprenant();
            apprenant.setNom("Bowen");
            apprenant.setPrenom("Jamie");
            apprenant.setSexe("M");
            apprenant.setNationalite("Anglais");
            apprenant.setDateNaissance(LocalDate.of(2010, 8, 2));
            apprenant.setEtatPaiement(0);
            apprenant.setClasse("3eme");
            ApprenantDAOImp apprenantDAOImp = new ApprenantDAOImp();
            System.out.println(apprenantDAOImp.create(apprenant, "Sophie Dee"));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public static void getApprenants(){
        try {
            ApprenantDAOImp apprenantDAOImp = new ApprenantDAOImp();
            System.out.println(apprenantDAOImp.getList());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public static void testSerializeArchive(){
        try{
            ClasseDAOImp classeDAOImp = new ClasseDAOImp();
            Classes classes = new Classes();
            classes.setClasses(classeDAOImp.getList());
            classes.setYear(classes.getClasses().get(1).getAnnee());
            ArchiveManager.SerializeArchive(classes);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void testDeserializeArchives(){
        try {
            Classes classes = ArchiveManager.DeserializeArchive("2022-2023");
            System.out.println(classes.getClasses());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void testSearchPaiement(String numeroRecu) {
        try {
            PaiementDAOImp paiementDaoImp = new PaiementDAOImp();
            System.out.println(paiementDaoImp.search(numeroRecu));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

    }

    public static void testSearchClasse(String intitule) {
        try {
            ClasseDAOImp classeDAOImp = new ClasseDAOImp();
            System.out.println(classeDAOImp.search(intitule));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    public static void testGetApprenantsOfClass(){
        int idClasse = 15;
        ClasseDAOImp classeDAOImp = new ClasseDAOImp();
        try {
            Classe classe = classeDAOImp.read(idClasse);
            System.out.println(new ApprenantDAOImp().getList().stream().
                    filter(apprenant -> Objects.equals(apprenant.getClasse(),
                            classe.getIntitule())).collect(Collectors.toList()));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getClass() + " " + e.getMessage());
        }
    }

    public static void testGetModules(){
        ModuleDAOImp classeDAOImp = new ModuleDAOImp();
        try {
            System.out.println(new ModuleDAOImp().getList());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public static void testGetClasses(){
        ClasseDAOImp classeDAOImp = new ClasseDAOImp();
        try {
            List<Classe> classes = classeDAOImp.getList();
            System.out.println(classes);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public static void testConnexion(){
        try (Connection connection = DBManager.getConnection()) {
            JOptionPane.showMessageDialog (null, "Connexion à la base OK." );
        } catch (Exception e) {
            JOptionPane.showMessageDialog (null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE );
        }
    }

    public static void testReadClasse(int id){
        ClasseDAOImp classeDAOImp = new ClasseDAOImp();
        try {
            Classe classe = classeDAOImp.read(id);
            System.out.println(classe);
            System.out.println(classe.getCurrentEcheance());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public static void testNoteCSV() {
        NotesCSV noteCSV = new NotesCSV();
        File file = new File("src/com/gesschoolapp/gescsv/notes.csv");
        List<String> notes = new ArrayList<>();

        // Test de la lecture du fichier
        try {
            notes = noteCSV.readFile(file);
            for (String note : notes)
                System.out.println(note);
        } catch (CSVException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        // Récupération des données dans une liste de tableau de String
        try {
            List<String[]> data = noteCSV.getData(notes);
            for (String[] note : data)
                System.out.println(Arrays.toString(note));
        } catch (CSVException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        // Récupération des données dans une liste d'objets Etudiant
        try {
            List<Note> notesList = noteCSV.csvToObject(noteCSV.getData(notes), new ModuleDAOImp().read(23),
                    new ClasseDAOImp().read(15), new UserDAOImp().read(9), 2);
            for(Note note : notesList) {
                System.out.println(note);
            }
        } catch (CSVException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } catch (DAOException | MismatchException e) {
            throw new RuntimeException(e);
        }
    }

    public static void testApprenantCSV() {
        ApprenantsCSV apprenantCSV = new ApprenantsCSV();
        File file = new File("src/com/gesschoolapp/gescsv/apprenants.csv");
        List<String> apprenants = new ArrayList<>();

        // Test de la lecture du fichier
        try {
            apprenants = apprenantCSV.readFile(file);
            for (String apprenant : apprenants)
                System.out.println(apprenant);
        } catch (CSVException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        // Récupération des données dans une liste de tableau de String
        try {
            List<String[]> data = apprenantCSV.getData(apprenants);
            for (String[] apprenant : data)
                System.out.println(Arrays.toString(apprenant));
        } catch (CSVException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        // Récupération des données dans une liste d'objets Etudiant
        try {
            List<Apprenant> apprenantsList = apprenantCSV.csvToObject(apprenantCSV.getData(apprenants));
            for(Apprenant apprenant : apprenantsList) {
                System.out.println(apprenant);
            }
        } catch (CSVException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        JOptionPane.showMessageDialog(null, "Test terminé");
    }

    public static void testCSVWriter() {
        ApprenantsCSV apprenantCSV = new ApprenantsCSV();
        File file = new File("./apprenants/newapprenants.csv");
        List<Apprenant> apprenants = new ArrayList<>();
        LocalDate date = LocalDate.of(2000, 1, 1);

        // Dummy data
        Apprenant apprenant1 = new Apprenant();
        apprenant1.setNom("Doe");
        apprenant1.setPrenom("John");
        apprenant1.setSexe("M");
        apprenant1.setClasse("3eme");
        apprenant1.setNationalite("Française");
        apprenant1.setDateNaissance(date);
        apprenant1.setMatricule(200);

        apprenants.add(apprenant1);

        // Test de la lecture du fichier
        try {
            apprenantCSV.writeCSVFile(String.valueOf(file), apprenants);
        } catch (CSVException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public static void testGetPaiements() {
        PaiementDAOImp paiementDAOImp = new PaiementDAOImp();
        try {
            List<Paiement> paiements = paiementDAOImp.getList();
            for (Paiement paiement : paiements)
                System.out.println(paiement);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

}