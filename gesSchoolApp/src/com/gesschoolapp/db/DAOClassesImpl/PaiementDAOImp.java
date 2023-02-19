package com.gesschoolapp.db.DAOClassesImpl;

import com.gesschoolapp.Exceptions.DAOException;
import com.gesschoolapp.db.DAOInterfaces.SearchDAO;
import com.gesschoolapp.db.DBManager;
import com.gesschoolapp.models.paiement.Paiement;
import com.gesschoolapp.models.users.Admin;
import com.gesschoolapp.models.users.Caissier;
import com.gesschoolapp.models.users.Secretaire;
import com.gesschoolapp.models.users.Utilisateur;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class PaiementDAOImp implements SearchDAO<Paiement> {

    @Override
    public Paiement create(Paiement obj) throws DAOException {
        //Generate a method to insert a paiement in the database
        try (Connection connection = DBManager.getConnection()) {
            String query = "INSERT INTO paiements (numeroRecu, date, montant, apprenant, classe, rubrique, caissier, observation) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, obj.getNumeroRecu());
            statement.setString(2, obj.getDate().toString());
            statement.setString(3, obj.getMontant());
            statement.setString(4, obj.getApprenant());
            statement.setString(5, obj.getClasse());
            statement.setString(6, obj.getRubrique());
            statement.setString(7, obj.getCaissier());
            statement.setString(8, obj.getObservation());
            statement.executeUpdate();
            return getList().get(getList().size() - 1);
        } catch (Exception e) {
            throw new DAOException( "Error in PaiementDAO.create() : " + e.getMessage());
        }
    }

    @Override
    public void update(Paiement obj) throws DAOException {

    }

    @Override
    public void delete(int id) throws DAOException {

    }

    @Override
    public Paiement read(int id) throws DAOException {
        return null;
    }

    @Override
    public List<Paiement> getList() throws DAOException {
        try (Connection connection = DBManager.getConnection()) {
            String query = "SELECT * FROM paiements";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            List<Paiement> paiements = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt("idPaiement");
                String numeroRecu = rs.getString("numeroRecu");
                String date = rs.getString("date");
                String montant = rs.getString("montant");
                String apprenant = rs.getString("apprenant");
                String classe = rs.getString("classe");
                String rubrique = rs.getString("rubrique");
                String caissier = rs.getString("caissier");
                String observation = rs.getString("observation");

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDate datePaiement = LocalDate.parse(date, formatter);

                paiements.add(new Paiement(id, numeroRecu, montant, rubrique, datePaiement, observation, apprenant, caissier, classe));
            }
            return paiements;
        } catch (Exception e) {
            throw new DAOException(e.getMessage());
        }
}

    @Override
    public List<Paiement> search(String stringToSearch) throws DAOException {
        // search paiemeent by numeroRecu
        List<Paiement> paiements = new ArrayList<>();

        try (Connection connection = DBManager.getConnection()) {

            String query = "SELECT * FROM paiements WHERE numeroRecu LIKE ?";
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, "%" + stringToSearch + "%");
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("idPaiement");
                String numeroRecu = rs.getString("numeroRecu");
                String date = rs.getString("date");
                String montant = rs.getString("montant");
                String apprenant = rs.getString("apprenant");
                String classe = rs.getString("classe");
                String rubrique = rs.getString("rubrique");
                String caissier = rs.getString("caissier");
                String observation = rs.getString("observation");

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                //convert String to LocalDate
                LocalDate formatedDate = LocalDate.parse(date, formatter);

                Paiement paiement = new Paiement(id, numeroRecu, montant, rubrique, formatedDate, observation, apprenant, caissier, classe);
                paiements.add(paiement);
            }
        } catch (Exception e) {
            throw new DAOException(e.getMessage());
        }
        return paiements;
    }
}
