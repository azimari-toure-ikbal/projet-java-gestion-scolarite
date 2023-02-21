package com.gesschoolapp.db.DAOClassesImpl;

import com.gesschoolapp.Exceptions.DAOException;
import com.gesschoolapp.db.DAOInterfaces.PaiementDAO;
import com.gesschoolapp.db.DBManager;
import com.gesschoolapp.models.actions.Action;
import com.gesschoolapp.models.paiement.Paiement;
import com.gesschoolapp.serial.ActionManager;
import com.gesschoolapp.view.util.ActionType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PaiementDAOImp implements PaiementDAO {

    @Override
    public Paiement create(Paiement obj, String user) throws DAOException {
        String rubrique = obj.getRubrique();
        //Generate a method to insert a paiement in the database
        try (Connection connection = DBManager.getConnection()) {
            if(Objects.equals(obj.getRubrique(), "inscription") && obj.getApprenant().getEtatPaiement() > 0 ){
                throw new DAOException("L'inscription de cet apprenant a déjà été payée");
            }
            if (obj.getApprenant().getEtatPaiement() == 9 && Objects.equals(obj.getRubrique(), "scolarite")){
                throw new DAOException("Cet apprenant a déjà payé toutes ses scolarités");
            }

            String  query = "INSERT INTO paiements (numeroRecu, date, montant, idApprenant, classe, rubrique, caissier, observation, apprenant ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, "RCU" + (int) (Instant.now().getEpochSecond()/1000));
            statement.setString(2, obj.getDate().toString());
            statement.setDouble(3, obj.getMontant());
            statement.setInt(4, obj.getApprenant().getIdApprenant());
            statement.setString(5, obj.getClasse());
            if(Objects.equals(obj.getRubrique(), "scolarite") ){
                rubrique = obj.getRubrique() + " " + new ClasseDAOImp().search(obj.getClasse()).
                        get(0).getEcheancier().get(obj.getApprenant().getEtatPaiement()).getDate().getMonth().toString().toLowerCase();
            }
            statement.setString(6, rubrique);
            statement.setString(7, obj.getCaissier());
            statement.setString(8, obj.getObservation());
            statement.setString(9, obj.getApprenant().getFullName());
            statement.executeUpdate();
            if (Objects.equals(obj.getRubrique(), "inscription") || Objects.equals(obj.getRubrique(), "scolarite")){
                new ApprenantDAOImp().incrementEtatPaiement(obj.getApprenant());
            }

            Action action = new Action();
            action.setAction(ActionType.ADD);
            action.setDate(LocalDateTime.now());
            action.setActor(user);
            ActionManager.add(action);

            return getList().get(getList().size() - 1);
        } catch (Exception e) {
            throw new DAOException( "Error in PaiementDAO.create() : " + e.getMessage());
        }
    }

    @Override
    public void update(Paiement obj, String user) throws DAOException {
    }

    @Override
    public void delete(int id, String user) throws DAOException {
        try (Connection connection = DBManager.getConnection()) {
            if(new PaiementDAOImp().read(id).getRubrique().equals("inscription") || new PaiementDAOImp().read(id).getRubrique().equals("scolarite")){
                new ApprenantDAOImp().decrementEtatPaiement(new ApprenantDAOImp().read(new PaiementDAOImp().read(id).getApprenant().getIdApprenant()));
            }
            String query = "DELETE FROM paiements WHERE idPaiement = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            statement.executeUpdate();

            Action action = new Action();
            action.setAction(ActionType.DELETE);
            action.setDate(LocalDateTime.now());
            action.setActor(user);
            ActionManager.add(action);
        } catch (Exception e) {
            throw new DAOException(e.getMessage());
        }
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
            String nomApprenant;
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
                nomApprenant = rs.getString("apprenant");

                LocalDate datePaiement = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

                paiements.add(new Paiement(id, numeroRecu, montant, rubrique, datePaiement, observation, caissier, classe, nomApprenant, new ApprenantDAOImp().read(idApprenant)));
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
            String nomApprenant;
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
                nomApprenant = rs.getString("apprenant");

                LocalDate formatedDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

                Paiement paiement = new Paiement(id, numeroRecu, montant, rubrique, formatedDate, observation, new ApprenantDAOImp().read(idApprenant) , caissier, classe);
                paiement.setNameApprenant(nomApprenant);
                paiements.add(paiement);
            }
        } catch (Exception e) {
            throw new DAOException(e.getMessage());
        }
        return paiements;
    }

    public List<String> getAnnees() throws DAOException {
        try (Connection connection = DBManager.getConnection()) {
            String query = "SELECT DISTINCT YEAR(date) FROM paiements";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            List<String> annees = new ArrayList<>();
            while (rs.next()) {
                annees.add(rs.getString(1));
            }
            return annees;
        } catch (Exception e) {
            throw new DAOException(e.getMessage());
        }
    }
}
