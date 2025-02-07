package modele;

public class Theme {

    private int num;
    private String nom;
    private int montant;

    public Theme() {
    }

    public Theme(int num, String nom, int montant) {
        this.num = num;
        this.nom = nom;
        this.montant = montant;
    }

    public int getNum() {
        return this.num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getNom() {
        return this.nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getMontant() {
        return this.montant;
    }

    public void setMontant(int montant) {
        this.montant = montant;
    }
}
