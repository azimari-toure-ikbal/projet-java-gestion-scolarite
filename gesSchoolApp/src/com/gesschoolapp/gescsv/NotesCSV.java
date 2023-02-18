package com.gesschoolapp.gescsv;

import com.gesschoolapp.Exceptions.CSVException;
import com.gesschoolapp.Exceptions.DAOException;
import com.gesschoolapp.Exceptions.Mismatch;
import com.gesschoolapp.db.DAOClassesImpl.ApprenantDAOImp;
import com.gesschoolapp.db.DAOClassesImpl.ModuleDAOImp;
import com.gesschoolapp.db.DAOClassesImpl.NoteDAOImp;
import com.gesschoolapp.gescsv.reader.CSVReader;
import com.gesschoolapp.models.classroom.Classe;
import com.gesschoolapp.models.matieres.Module;
import com.gesschoolapp.models.matieres.Note;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class NotesCSV implements CSVReader<Note> {
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
    public List<Note> csvToObject(List<String[]> data) throws CSVException {
        // Verify if the list is not empty
        if (data.isEmpty()) {
            throw new CSVException("La liste est vide");
        }

        NoteDAOImp noteDAOImp = new NoteDAOImp();
        ApprenantDAOImp apprenantDAOImp = new ApprenantDAOImp();

        List<Note> notes = new ArrayList<>();

        for (String[] line : data) {
            if (line.length != 4) {
                throw new CSVException("Le fichier n'est pas au bon format");
            }

            Note note = new Note();
            try {
                note.setApprenant(apprenantDAOImp.searchByMatricule(Integer.parseInt(line[0])));
//                System.out.println(apprenantDAOImp.searchByMatricule(Integer.parseInt(line[0])));
            } catch (DAOException e) {
                throw new CSVException("Une erreur est survenue lors de la recherche de l'apprenant : " + e.getMessage());
            }
            note.setModule(line[2]);
            note.setNote(Integer.parseInt(line[3]));

            // Create the note
            try {
                noteDAOImp.create(note);
            } catch (DAOException e) {
                throw new CSVException("Une erreur est survenue lors de la création de la note : " + e.getMessage());
            }

            // Add the note to the list
            notes.add(note);
        }
        return notes;
    }

    /**
     * @param data
     * @param module
     * @param classse
     * @return List
     * @throws CSVException
     * @throws Mismatch
     */
    public List<Note> csvToObject(List<String[]> data, Module module, Classe classse) throws CSVException, Mismatch {
        // Verify if the list is not empty
        if (data.isEmpty()) {
            throw new CSVException("La liste est vide");
        }

        // Verify if the class exists
        if (classse == null) {
            throw new CSVException("La classe n'existe pas");
        }

        NoteDAOImp noteDAOImp = new NoteDAOImp();
        ApprenantDAOImp apprenantDAOImp = new ApprenantDAOImp();
        ModuleDAOImp moduleDAOImp = new ModuleDAOImp();

        List<Note> notes = new ArrayList<>();

        for (String[] line : data) {
            if (line.length != 3) {
                throw new CSVException("Le fichier n'est pas au bon format \n" +
                        "Le fichier doit être au format : matricule;note;semestre");
            }

            // Verify if the student exists
            try {
                if (apprenantDAOImp.searchByMatricule(Integer.parseInt(line[0])) == null) {
                    throw new CSVException("L'apprenant n'existe pas");
                }
            } catch (DAOException e) {
                throw new CSVException("Une erreur est survenue lors de la recherche de l'apprenant : " + e.getMessage());
            }

            // Verify if the student is in the class
            try {
                if (!Objects.equals(apprenantDAOImp.searchByMatricule(Integer.parseInt(line[0])).getClasse(), classse.getIntitule())) {
                    System.out.println("L'intitulé de la classe : " + classse.getIntitule());
                    System.out.println(apprenantDAOImp.searchByMatricule(Integer.parseInt(line[0])));
                    throw new Mismatch("L'apprenant n'est pas dans la classe");
                }
            } catch (DAOException e) {
                throw new CSVException("Une erreur est survenue lors de la recherche de l'apprenant : " + e.getMessage());
            }

            Note note = new Note();
            try {
                note.setApprenant(apprenantDAOImp.searchByMatricule(Integer.parseInt(line[0])));
            } catch (DAOException e) {
                throw new CSVException("Une erreur est survenue lors de la recherche de l'apprenant : " + e.getMessage());
            }
            note.setModule(module.getIntitule());

            // Verify that the note is between 0 and 20
            if (Integer.parseInt(line[1]) < 0 || Integer.parseInt(line[1]) > 20) {
                throw new Mismatch("La note doit être comprise entre 0 et 20");
            }
            note.setNote(Integer.parseInt(line[1]));

            // Verify that the semester is 1 or 2
            if (Integer.parseInt(line[2]) != 1 && Integer.parseInt(line[2]) != 2) {
                throw new Mismatch("Le semestre doit être 1 ou 2");
            }
            int semestre = Integer.parseInt(line[2]);

            // Create the note
            try {
                noteDAOImp.update(note, semestre);
            } catch (DAOException e) {
                throw new CSVException("Une erreur est survenue lors de la création de la note : " + e.getMessage());
            }

            // Add the note to the list
            notes.add(note);
        }
        return notes;
    }

}
