package com.gesschoolapp.db.DAOClassesImpl;

import com.gesschoolapp.Exceptions.DAOException;
import com.gesschoolapp.db.DAOInterfaces.SearchDAO;
import com.gesschoolapp.db.DBManager;
import com.gesschoolapp.models.paiement.Paiement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PaiementDAOImp implements SearchDAO<Paiement> {

    @Override
    public Paiement create(Paiement obj) throws DAOException {
        //Generate a method to insert a paiement in the database
        try (Connection connection = DBManager.getConnection()) {
            String query = "INSERT INTO paiements (numeroRecu, date, montant, apprenant, classe, rubrique, caissier, observation) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, "RCU" + (int) (Instant.now().getEpochSecond()/1000));
            statement.setString(2, obj.getDate().toString());
            statement.setDouble(3, obj.getMontant());
            statement.setInt(4, obj.getApprenant().getIdApprenant());
            statement.setString(5, obj.getClasse());
            statement.setString(6, obj.getRubrique());
            statement.setString(7, obj.getCaissier());
            statement.setString(8, obj.getObservation());
            if (Objects.equals(obj.getRubrique(), "inscription") || Objects.equals(obj.getRubrique(), "scolarite")){
                new ApprenantDAOImp().incrementEtatPaiement(obj.getApprenant());
            }
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
            int id;
            String numeroRecu;
            String date;
            double montant;
            int idApprenant;
            String classe;
            String rubrique;
            String caissier;
            String observation;
            while (rs.next()) {
                id = rs.getInt("idPaiement");
                numeroRecu = rs.getString("numeroRecu");
                date = rs.getString("date");
                montant = rs.getDouble("montant");
                idApprenant = rs.getInt("idApprenant");
                classe = rs.getString("classe");
                rubrique = rs.getString("rubrique");
                caissier = rs.getString("caissier");
                observation = rs.getString("observation");

                LocalDate datePaiement = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

                paiements.add(new Paiement(id, numeroRecu, montant, rubrique, datePaiement, observation, new ApprenantDAOImp().read(idApprenant), caissier, classe));
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
            int id;
            String numeroRecu;
            String date;
            double montant;
            int idApprenant;
            String classe;
            String rubrique;
            String caissier;
            String observation;
            while (rs.next()) {
                id = rs.getInt("idPaiement");
                numeroRecu = rs.getString("numeroRecu");
                date = rs.getString("date");
                montant = rs.getDouble("montant");
                idApprenant = rs.getInt("idApprenant");
                classe = rs.getString("classe");
                rubrique = rs.getString("rubrique");
                caissier = rs.getString("caissier");
                observation = rs.getString("observation");

                LocalDate formatedDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

                Paiement paiement = new Paiement(id, numeroRecu, montant, rubrique, formatedDate, observation, new ApprenantDAOImp().read(idApprenant) , caissier, classe);
                paiements.add(paiement);
            }
        } catch (Exception e) {
            throw new DAOException(e.getMessage());
        }
        return paiements;
    }
}
