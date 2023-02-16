package com.gesschoolapp.gescsv;

import com.gesschoolapp.Exceptions.CSVException;
import com.gesschoolapp.Exceptions.DAOException;
import com.gesschoolapp.db.DAOClassesImpl.ApprenantDAOImp;
import com.gesschoolapp.db.DAOClassesImpl.ModuleDAOImp;
import com.gesschoolapp.db.DAOClassesImpl.NoteDAOImp;
import com.gesschoolapp.gescsv.reader.CSVReader;
import com.gesschoolapp.models.matieres.Note;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
        ModuleDAOImp moduleDAOImp = new ModuleDAOImp();

        List<Note> notes = new ArrayList<>();

        for (String[] line : data) {
            //Note has id, note(int), apprenant(Apprenant), module(Module), semestre(int)
            if (line.length != 4) {
                throw new CSVException("Le fichier n'est pas au bon format");
            }

            Note note = new Note();
            try {
                note.setApprenant(apprenantDAOImp.searchByMatricule(Integer.parseInt(line[0])));
            } catch (DAOException e) {
                throw new CSVException("Une erreur est survenue lors de la recherche de l'apprenant : " + e.getMessage());
            }
            note.setModule(line[2]);
            note.setNote(Integer.parseInt(line[3]));
            note.setSemestre(1);

            // Create the note
            try {
                noteDAOImp.create(note);
            } catch (DAOException e) {
                throw new CSVException("Une erreur est survenue lors de la création de la note : " + e.getMessage());
            }
        }
        return notes;
    }

}
