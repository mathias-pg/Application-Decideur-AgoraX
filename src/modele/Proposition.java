package modele;

public class Proposition {

    private Groupe sonGroupe;
    private Theme sonTheme;
    private int num;
    private String titre;
    private String description;	
    private String dateSoumission;
    private String dureeDiscussion;
    private String etat;
    private int cout;
    private int nbLikes;
    private int nbVotesPour;

    public Proposition() {
    }

    public Proposition(int num, String titre, String description, String dateSoumission, String dureeDiscussion, String etat, int cout, int nbLikes, int nbVotesPour) {
        this.num = num;
        this.titre = titre;
        this.description = description;
        this.dateSoumission = dateSoumission;
        this.dureeDiscussion = dureeDiscussion;
        this.etat = etat;
        this.cout = cout;
        this.nbLikes = nbLikes;
        this.nbVotesPour = nbVotesPour;
    }

    public Groupe getSonGroupe() {
        return sonGroupe;
    }

    public void setSonGroupe(Groupe sonGroupe) {
        this.sonGroupe = sonGroupe;
    }

    public Theme getSonTheme() {
        return sonTheme;
    }

    public void setSonTheme(Theme sonTheme) {
        this.sonTheme = sonTheme;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDateSoumission() {
        return dateSoumission;
    }

    public void setDateSoumission(String dateSoumission) {
        this.dateSoumission = dateSoumission;
    }

    public String getDureeDiscussion() {
        return dureeDiscussion;
    }

    public void setDureeDiscussion(String dureeDiscussion) {
        this.dureeDiscussion = dureeDiscussion;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public int getCout() {
        return cout;
    }

    public void setCout(int cout) {
        this.cout = cout;
    }

    public int getNbLikes() {
        return nbLikes;
    }

    public void setNbLikes(int nbLikes) {
        this.nbLikes = nbLikes;
    }
    
    public int getNbVotesPour() {
        return nbVotesPour;
    }

    public void setNbVotesPour(int nbVotesPour) {
        this.nbVotesPour = nbVotesPour;
    }
}
