package com.nf28.td6;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Taches {
    static DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");;
    private String libelle = "";
    private boolean statut = false;
    private String priorite = "";
    private Date deadline = new Date();

    public Taches(String lib, boolean sta, String prio, Date dead){
        libelle = lib;
        statut = sta;
        priorite = prio;
        deadline = dead;
    }

    public String getLibelle(){
        return libelle;
    }

    public void setLibelle(String lib){
        libelle = lib;
    }

    public boolean getStatut(){
        return statut;
    }

    public void setStatut(boolean stat){
        statut = stat;
    }

    public String getPriorite(){
        return priorite;
    }

    public void setPriorite(String prio){
        priorite = prio;
    }

    public Date getDeadline(){
        return deadline;
    }

    public void setDeadline(Date dead){
        deadline = dead;
    }

    public static List<String> getAllTaches(List<Taches> list) {
        List<String> taches = new ArrayList<>();
        for(Taches t : list){
            taches.add(t.getLibelle());
        }
        return taches;
    }
}
