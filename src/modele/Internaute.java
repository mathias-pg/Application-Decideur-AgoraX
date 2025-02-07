package modele;

import java.util.ArrayList;

public class Internaute {
    
    private ArrayList<Groupe> sesGroupes;
    private int num;
    private String nom;
    private String prenom;
    private String email; 
    private String motDePasse;
    private String adresse;
    private String dateInscrition;
    
    public Internaute(int num, String nom, String prenom, String email, String motDePasse, String adresse, String dateInscrition, ArrayList<Groupe> sesGroupes) {
        this.num = num;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.motDePasse = motDePasse;
        this.adresse = adresse;
        this.dateInscrition = dateInscrition;
        this.sesGroupes = new ArrayList<>();
    }

    public ArrayList<Groupe> getSesGroupes() {
        return sesGroupes;
    }	

    public void setSesGroupes(ArrayList<Groupe> sesGroupes) {
        this.sesGroupes = sesGroupes;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getDateInscrition() {
        return dateInscrition;
    }

    public void setDateInscrition(String dateInscrition) {
        this.dateInscrition = dateInscrition;
    }
} 
