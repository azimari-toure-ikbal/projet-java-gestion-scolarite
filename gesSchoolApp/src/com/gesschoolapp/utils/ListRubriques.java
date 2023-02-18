package com.gesschoolapp.utils;

import com.gesschoolapp.models.paiement.Rubrique;

import java.util.ArrayList;
import java.util.List;

public class ListRubriques {


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

}
