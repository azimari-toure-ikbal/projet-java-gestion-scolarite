package com.gesschoolapp.gescsv;

import com.gesschoolapp.Exceptions.CSVException;
import com.gesschoolapp.Exceptions.DAOException;
import com.gesschoolapp.Exceptions.Mismatch;
import com.gesschoolapp.db.DAOClassesImpl.ApprenantDAOImp;
import com.gesschoolapp.gescsv.reader.CSVReader;
import com.gesschoolapp.gescsv.writter.CSVWritter;
import com.gesschoolapp.models.classroom.Classe;
import com.gesschoolapp.models.student.Apprenant;
import com.gesschoolapp.utils.Utilitaire;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ApprenantsCSV implements CSVReader<Apprenant>, CSVWritter<Apprenant> {
    @Override
    public List<String> readFile(File file) throws CSVException {
        // Verify if the file exists
        if (!file.exists()) {
            throw new CSVException("Le fichier n'existe pas");
        }

        // Verify if the file is a CSV file
        if (!file.getName().endsWith(".csv")) {
            throw new CSVException("Le fichier n'est pas un fichier CSV");
        }

        // Read the file
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            List<String> lines = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
            return lines;
        } catch (IOException e) {
            throw new CSVException("Une erreur est survenue lors de la lecture du fichier");
        }
    }

    @Override
    public List<String[]> getData(List<String> lines) throws CSVException {
        // Verify if the list is not empty
        if (lines.isEmpty()) {
            throw new CSVException("Le fichier est vide");
        }

        List<String[]> data = new ArrayList<>();
        for (String line : lines) {
            data.add(line.split(";"));
        }
        return data;
    }

    @Override
    public List<Apprenant> csvToObject(List<String[]> data) throws CSVException {
        // Verify if the list is not empty
        if (data.isEmpty()) {
            throw new CSVException("La liste est vide");
        }

        List<Apprenant> apprenants = new ArrayList<>();

        for (String[] line : data) {
            if (line.length != 5) {
                throw new CSVException("Le fichier n'est pas au bon format");
            }

            Apprenant apprenant = new Apprenant();
            apprenant.setPrenom(line[0].substring(0, 1).toUpperCase() + line[0].substring(1).toLowerCase());
            apprenant.setNom(line[1].toUpperCase());
            apprenant.setDateNaissance(LocalDate.parse(line[2]));
            apprenant.setNationalite(line[3].substring(0, 1).toUpperCase() + line[3].substring(1).toLowerCase());
            apprenant.setEtatPaiement(0);
            apprenant.setSexe(line[4].toUpperCase());
            // generate matricule
            int matricule = 105;
            apprenant.setMatricule(matricule++);
            apprenant.setClasse("3eme");

            // Create the apprenant
            try {
                new ApprenantDAOImp().create(apprenant);
            } catch (DAOException e) {
                throw new CSVException("Une erreur est survenue lors de la création de l'apprenant : " + e.getMessage());
            }

            // Add the apprenant to the list
            apprenants.add(apprenant);
        }
        return apprenants;
    }

    public List<Apprenant> csvToObject(List<String[]> data, Classe classe) throws CSVException, Mismatch {
        // Verify if the list is not empty
        if (data.isEmpty()) {
            throw new CSVException("La liste est vide");
        }

        List<Apprenant> apprenants = new ArrayList<>();

        ApprenantDAOImp apprenantDAOImp = new ApprenantDAOImp();

        for (String[] line : data) {
            if (line.length != 5) {
                throw new CSVException("Le fichier n'est pas au bon format \n" +
                        "Le fichier doit être au format : Prenom;Nom;Date de naissance;Nationalité;Sexe");
            }

            Apprenant apprenant = new Apprenant();
            apprenant.setPrenom(Utilitaire.capitalizeName(line[0]));
            apprenant.setNom(line[1].toUpperCase());
            // Verify if the date has format 'dd-MM-yyyy'
            if (!line[2].matches("\\d{2}-\\d{2}-\\d{4}")) {
                throw new Mismatch("La date doit être au format 'jj-MM-aaaa'");
            }
            // Convert the date to LocalDate with format 'yyyy-MM-dd'
            apprenant.setDateNaissance(Utilitaire.dateFornater(line[2]));
            apprenant.setNationalite(line[3].substring(0, 1).toUpperCase() + line[3].substring(1).toLowerCase());
            apprenant.setEtatPaiement(0);
            // Verify if the sexe is 'M' or 'F'
            if (!line[4].equalsIgnoreCase("M") && !line[4].equalsIgnoreCase("F")) {
                throw new Mismatch("Le sexe doit être 'M' ou 'F'");
            }
            apprenant.setSexe(line[4].toUpperCase());
            apprenant.setClasse(classe.getIntitule());

            // Create the apprenant
            try {
                new ApprenantDAOImp().create(apprenant);
            } catch (DAOException e) {
                throw new CSVException("Une erreur est survenue lors de la création de l'apprenant : " + e.getMessage());
            }

            // Add the apprenant to the list
            apprenants.add(apprenant);
        }
        return apprenants;
    }

    @Override
    public void writeCSVFile(String fileName, List<Apprenant> list) throws CSVException {
        // Check if the list is not empty
        if (list.isEmpty()) {
            throw new CSVException("La liste est vide");
        }

        // Check if the file name is not empty
        if (fileName.isEmpty()) {
            throw new CSVException("Le nom du fichier est vide");
        }

        // Check if the file name is a CSV file
        if (!fileName.endsWith(".csv")) {
            throw new CSVException("Le fichier n'est pas un fichier CSV");
        }

        // Get the data
        List<String[]> data = new ArrayList<>();
        for (Apprenant apprenant : list) {
            String[] line = new String[7];
            line[0] = apprenant.getPrenom();
            line[1] = apprenant.getNom();
            line[2] = apprenant.getDateNaissance().toString();
            line[3] = apprenant.getNationalite();
            line[4] = apprenant.getSexe();
            line[5] = String.valueOf(apprenant.getMatricule());
            line[6] = apprenant.getClasse();
            data.add(line);
        }

        // Write the file
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            for (String[] line : data) {
                bw.write(String.join(";", line));
                bw.newLine();
            }
        } catch (IOException e) {
            throw new CSVException("Une erreur est survenue lors de l'écriture du fichier : " + e.getMessage());
        }
    }

}
