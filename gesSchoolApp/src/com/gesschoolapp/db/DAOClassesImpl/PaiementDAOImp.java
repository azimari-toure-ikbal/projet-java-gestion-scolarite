//package com.gesschoolapp.db.DAOClassesImpl;
//
//import com.gesschoolapp.Exceptions.DAOException;
//import com.gesschoolapp.db.DAOInterfaces.SearchDAO;
//import com.gesschoolapp.db.DBManager;
//import com.gesschoolapp.models.paiement.Paiement;
//import com.gesschoolapp.models.users.Admin;
//import com.gesschoolapp.models.users.Caissier;
//import com.gesschoolapp.models.users.Secretaire;
//import com.gesschoolapp.models.users.Utilisateur;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.util.ArrayList;
//import java.util.List;
//
//public class PaiementDAOImp implements SearchDAO<Paiement> {
//    @Override
//    public void create(Paiement obj) throws DAOException {
//
//    }
//
//    @Override
//    public void update(Paiement obj) throws DAOException {
//
//    }
//
//    @Override
//    public void delete(int id) throws DAOException {
//
//    }
//
//    @Override
//    public Paiement read(int id) throws DAOException {
//        return null;
//    }
//
//    @Override
//    public List<Paiement> getList() throws DAOException {
//        return null;
//    }
//
//    @Override
//    public List<Paiement> search(String stringToSearch) throws DAOException {
//        List<Paiement> users = new ArrayList<>();
//
//        try (Connection connection = DBManager.getConnection()) {
//
//            String query = "SELECT * FROM paiements WHERE idPaiement LIKE ?";
//            PreparedStatement statement = connection.prepareStatement(query);
//
//            statement.setString(1, "%" + stringToSearch + "%");
//            ResultSet resultSet = statement.executeQuery();
//
//            while (resultSet.next()) {
//                int idPaiement = resultSet.getInt("idPaiement");
//
//
//                switch (type) {
//                    case "admin" -> users.add(new Admin(idUtilisateur, nom, prenom, numero, type, password));
//                    case "secretaire" -> users.add(new Secretaire(idUtilisateur, nom, prenom, numero, type, password));
//                    case "caissier" -> users.add(new Caissier(idUtilisateur, nom, prenom, numero, type, password));
//                    default -> {}
//                }
//            }
//        } catch (Exception e) {
//            throw new DAOException(e.getMessage());
//        }
//
//        return users;
//    }
//}
