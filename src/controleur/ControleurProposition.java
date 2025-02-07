package controleur;

import modele.Groupe;
import modele.Proposition;
import modele.Algo;
import modele.List;
import modele.Connexion;
import vue.VueParametre;
import vue.VueProposition;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;

import javax.swing.*;

public class ControleurProposition {

    private Groupe groupe;
    private VueProposition vueProposition;

    public ControleurProposition(Groupe groupe, VueProposition vueProposition) {
        this.groupe = groupe;
        this.vueProposition = vueProposition;
    }

    // Ouvre la vue des paramètres pour modifier le groupe
    public void ouvrirVueParametres() {
        VueParametre vueParametre = new VueParametre(groupe, vueProposition);
        vueParametre.setVisible(true);
        vueProposition.setVisible(false);
    }
    
    public void mettreAJourBudget(int numProposition, int nouveauBudget) {
        try {
            String url = "https://projets.iut-orsay.fr/saes3-dteixei/ProjetS3/json/set_propositionBudget.php?"
                    + "NumProposition=" + numProposition
                    + "&CoutProposition=" + nouveauBudget;

            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("GET");

            if (conn.getResponseCode() != 200) {
                JOptionPane.showMessageDialog(null, "Erreur lors de la mise à jour du budget.");
            }
            conn.disconnect();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erreur de connexion à la base de données.");
        }
    }
    
    public void trierPropositions(String critere) {
        ArrayList<Proposition> propositions = groupe.getSesPropositions();
        Algo algo = new Algo();
        List<Proposition> prop = algo.convertirArrayListToList(propositions);

        switch (critere) {
        	case "basique":
        		Connexion con = new Connexion();
        		propositions = con.getPropositionsPourGroupe(groupe.getNum());
        		break;
            case "mode1":
            	prop = algo.minimiserBudgetGlouton(prop);
            	propositions = algo.convertListToArrayList(prop);
            	System.out.println("Affichage des propositions avec le mode de décision 1");
                break;
            case "mode2":
            	prop = algo.maximiserVotesGlouton(prop, groupe.getBudgetAnnuelGlobal());
            	propositions = algo.convertListToArrayList(prop);
            	System.out.println("Affichage des propositions avec le mode de décision 2");
                break;
            case "mode3":
            	prop = algo.maximiserVotesGlouton(groupe.getSesThemes(), prop);
            	propositions = algo.convertListToArrayList(prop);
            	System.out.println("Affichage des propositions avec le mode de décision 3");
                break;
        }

        groupe.setSesPropositions(new ArrayList<>(propositions));  

        rafraichirVue();
    }

    // Met à jour l'affichage des propositions
    private void rafraichirVue() {
        vueProposition.dispose();
        
        VueProposition nouvelleVue = new VueProposition(groupe.getSesPropositions(), groupe, vueProposition.getVueConnexion());

        nouvelleVue.setVisible(true);
    }
}
