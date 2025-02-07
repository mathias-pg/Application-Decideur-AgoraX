package modele;

import java.util.ArrayList;

public class Groupe {

    private ArrayList<Proposition> sesPropositions;
    private ArrayList<Theme> sesThemes;
    private int num;
    private String nom;
    private String description;
    private String couleur; 
    private String dateCreation;
    private int budgetAnnuelGlobal;
    private int nbInternautes;

    public Groupe() {
        this.sesPropositions = new ArrayList<>();
        this.sesThemes = new ArrayList<>();
    }

    public Groupe(int num, String nom, String description, String couleur, String dateCreation, int budgetAnnuelGlobal, int nbInternautes) {
        this.num = num;
        this.nom = nom;
        this.description = description;
        this.couleur = couleur;
        this.dateCreation = dateCreation;
        this.budgetAnnuelGlobal = budgetAnnuelGlobal;
        this.nbInternautes = nbInternautes;
        this.sesPropositions = new ArrayList<>();
        this.sesThemes = new ArrayList<>();
    }


    public ArrayList<Proposition> genererRecommendation(ArrayList<Proposition> listePropositions, ArrayList<Theme> listeThemes) {
        throw new UnsupportedOperationException("Méthode non implémentée.");
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCouleur() {
        return couleur;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public String getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(String dateCreation) {
        this.dateCreation = dateCreation;
    }

    public int getBudgetAnnuelGlobal() {
        return budgetAnnuelGlobal;
    }

    public void setBudgetAnnuelGlobal(int budgetAnnuelGlobal) {
        this.budgetAnnuelGlobal = budgetAnnuelGlobal;
    }

    public int getNbInternautes() {
        return nbInternautes;
    }

    public void setNbInternautes(int nbInternautes) {
        this.nbInternautes = nbInternautes;
    }

    public ArrayList<Proposition> getSesPropositions() {
        return sesPropositions;
    }

    public void setSesPropositions(ArrayList<Proposition> sesPropositions) {
        this.sesPropositions = sesPropositions;
    }

    public ArrayList<Theme> getSesThemes() {
        return sesThemes;
    }

    public void setSesThemes(ArrayList<Theme> sesThemes) {
        this.sesThemes = sesThemes;
    }
}
