package com.gesschoolapp.db;
import com.gesschoolapp.db.DAOClassesImpl.ClasseDAOImp;
import com.gesschoolapp.db.DAOClassesImpl.PaiementDAOImp;

import java.sql.Connection;
import javax.swing.JOptionPane;

public class TestConnection {
    public static void main(String[] args) {
        testSearchPaiement("69");
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
    public static void testGetApprenants(int idClasse){
        ClasseDAOImp classeDAOImp = new ClasseDAOImp();
        try {
            System.out.println(classeDAOImp.getApprenantsOfClass(idClasse));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getClass() + " " + e.getMessage());
        }
    }

    public static void testGetModules(int idClasse){
        ClasseDAOImp classeDAOImp = new ClasseDAOImp();
        try {
            System.out.println(classeDAOImp.getModulesOfClass(idClasse));
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