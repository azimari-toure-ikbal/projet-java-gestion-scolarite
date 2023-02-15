package com.gesschoolapp.db;
import com.gesschoolapp.ArchiveManager;
import com.gesschoolapp.Exceptions.DAOException;
import com.gesschoolapp.db.DAOClassesImpl.ApprenantDAOImp;
import com.gesschoolapp.db.DAOClassesImpl.ClasseDAOImp;
import com.gesschoolapp.db.DAOClassesImpl.ModuleDAOImp;
import com.gesschoolapp.db.DAOClassesImpl.PaiementDAOImp;
import com.gesschoolapp.models.classroom.Classe;
import com.gesschoolapp.models.classroom.Classes;
import com.gesschoolapp.models.student.Apprenant;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;

public class TestConnection {
    public static void main(String[] args) {

       testGetModules();
    }


    public static void testCreateApprenant(){
        try {
            Apprenant apprenant = new Apprenant();
            apprenant.setNom("Zagadou");
            apprenant.setPrenom("Axel");
            apprenant.setSexe("M");
            apprenant.setNationalite("Camerounais");
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
            System.out.println(classeDAOImp.getList());
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

}