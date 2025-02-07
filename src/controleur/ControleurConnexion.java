package controleur;

import modele.Connexion;
import modele.Internaute;
import modele.Proposition;
import modele.Groupe;
import vue.VueConnexion;
import vue.VueProposition;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class ControleurConnexion implements ActionListener {

    private VueConnexion vue;

    public ControleurConnexion(VueConnexion vue) {
        this.vue = vue;
        this.vue.ajouterEcouteur(this);
        Connexion.chargerInternautes();  
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String email = vue.getEmail();
        String motDePasse = vue.getMotDePasse();

        Internaute internaute = Connexion.verifierConnexion(email, motDePasse);

        if (internaute == null) {
        	JOptionPane.showMessageDialog(null, "Identifiants incorrects.");
        } else {
            if (internaute.getSesGroupes().isEmpty()) {
            	JOptionPane.showMessageDialog(null, "Aucun groupe trouvé pour cet internaute ou il possède le rôle de décideur.");
            } else {
            	ArrayList<Groupe> groupes = internaute.getSesGroupes();
                vue.afficherGroupes(groupes, evt -> ouvrirVuePropositions(Integer.parseInt(evt.getActionCommand())));
                }
         }
    }

    
    private void ouvrirVuePropositions(int numGroupe) {
        ArrayList<Proposition> propositions = Connexion.getPropositionsPourGroupe(numGroupe);
        
        Groupe groupe = Connexion.getGroupeById(numGroupe);

        VueProposition vuePropositions = new VueProposition(propositions, groupe, vue);
        vuePropositions.setVisible(true);
        vue.setVisible(false); 
    }
}
