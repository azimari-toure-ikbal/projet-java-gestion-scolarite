package com.gesschoolapp.tests;
import com.gesschoolapp.serial.ArchiveManager;
import com.gesschoolapp.Exceptions.CSVException;
import com.gesschoolapp.db.DAOClassesImpl.ApprenantDAOImp;
import com.gesschoolapp.db.DAOClassesImpl.ClasseDAOImp;
import com.gesschoolapp.db.DAOClassesImpl.ModuleDAOImp;
import com.gesschoolapp.db.DAOClassesImpl.PaiementDAOImp;
import com.gesschoolapp.db.DBManager;
import com.gesschoolapp.gescsv.ApprenantsCSV;
import com.gesschoolapp.gescsv.NotesCSV;
import com.gesschoolapp.models.classroom.Classe;
import com.gesschoolapp.models.classroom.Classes;
import com.gesschoolapp.models.matieres.Note;
import com.gesschoolapp.models.student.Apprenant;

import java.io.File;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;

public class TestClass {
    public static void main(String[] args) {

//        testLastView();
//        testGetClasses();
//        testNoteCSV();
//        testApprenantCSV();
        testCSVWriter();
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
            apprenant.setNom("Emery");
            apprenant.setPrenom("Warren");
            apprenant.setSexe("M");
            apprenant.setNationalite("Français");
            apprenant.setDateNaissance(LocalDate.of(2009, 10, 5));
            apprenant.setEtatPaiement(0);
            apprenant.setClasse("4eme");
            ApprenantDAOImp apprenantDAOImp = new ApprenantDAOImp();
            apprenantDAOImp.create(apprenant);

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
    public static void testGetApprenantsOfClass(int idClasse){
        ClasseDAOImp classeDAOImp = new ClasseDAOImp();
        try {
            Classe classe = classeDAOImp.read(idClasse);
            System.out.println(new ApprenantDAOImp().getList().stream().
                    filter(apprenant -> Objects.equals(apprenant.getClasse(), classe.getIntitule())).collect(Collectors.toList()));
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
            System.out.println(classeDAOImp.read(id));
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
            List<Note> notesList = noteCSV.csvToObject(noteCSV.getData(notes));
            for(Note note : notesList) {
                System.out.println(note);
            }
        } catch (CSVException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
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
    }

    public static void testCSVWriter() {
        ApprenantsCSV apprenantCSV = new ApprenantsCSV();
        File file = new File("src/com/gesschoolapp/gescsv/newapprenants.csv");
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

}