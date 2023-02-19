package com.gesschoolapp.utils;

import com.gesschoolapp.models.paiement.Paiement;
import com.gesschoolapp.models.paiement.Rubrique;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Toolbox {

    public static String capitalizeName(String name) {
        String[] words = name.split(" ");
        StringBuilder result = new StringBuilder();

        for (String word : words) {
            if (!word.isEmpty()) {
                result.append(Character.toUpperCase(word.charAt(0)));
                result.append(word.substring(1).toLowerCase());
                result.append(" ");
            }
        }

        return result.toString().trim();
    }

    public static LocalDate dateFornater(String date) {
        // Convert from format 'dd-MM-yyyy' to 'yyyy-MM-dd'
        String[] dateParts = date.split("-");
        return LocalDate.of(Integer.parseInt(dateParts[2]), Integer.parseInt(dateParts[1]), Integer.parseInt(dateParts[0]));
    }

    public static List<Rubrique> getRubriques(){
        List<Rubrique> list = new ArrayList<>();
        list.add(new Rubrique(1, "droitInscription", 0));
        list.add(new Rubrique(2, "scolarite", 0));
        list.add(new Rubrique(3, "album", 0));
        list.add(new Rubrique(4, "tenue", 0));
        list.add(new Rubrique(5, "fraisGeneraux", 0));
        list.add(new Rubrique(6, "cotisationAPE", 0));
        list.add(new Rubrique(7, "inscription", 0));

        return list;
    }

    public static List<Paiement> paiementsJournalier(List<Paiement> paiements, LocalDate date){
        return null;
    }

    public static List<Paiement> paiementsHebdomadaire(List<Paiement> paiements, LocalDate date){
        return null;
    }

    public static List<Paiement> paiementsMensuel(List<Paiement> paiements, LocalDate date){
        return null;
    }

    public static List<Paiement> paiementsAnnuel(List<Paiement> paiements, LocalDate date){
        return null;
    }
}
