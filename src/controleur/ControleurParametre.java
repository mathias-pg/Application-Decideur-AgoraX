package controleur;

import modele.Groupe;
import modele.Theme;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class ControleurParametre {

    private Groupe groupe;
    private ArrayList<JTextField> themeBudgetFields;
    private JTextField budgetField;

    // URLs des API PHP
    private static final String URL_UPDATE_GROUPE = "https://projets.iut-orsay.fr/saes3-dteixei/ProjetS3/json/set_groupeBudget.php";
    private static final String URL_UPDATE_THEME = "https://projets.iut-orsay.fr/saes3-dteixei/ProjetS3/json/set_themeBudget.php";

    public ControleurParametre(Groupe groupe, JTextField budgetField, ArrayList<JTextField> themeBudgetFields) {
        this.groupe = groupe;
        this.budgetField = budgetField;
        this.themeBudgetFields = themeBudgetFields;
    }

    // Méthode pour enregistrer les modifications (budget groupe + thèmes)
    public boolean enregistrerModifications() {
        try {
            int nouveauBudgetGroupe = Integer.parseInt(budgetField.getText());

            int sommeBudgetsThemes = 0;
            for (JTextField field : themeBudgetFields) {
                sommeBudgetsThemes += Integer.parseInt(field.getText());
            }

            if (sommeBudgetsThemes > nouveauBudgetGroupe) {
                JOptionPane.showMessageDialog(null,
                        "Erreur : La somme des budgets des thèmes (" + sommeBudgetsThemes +
                                " €) dépasse le budget total du groupe (" + nouveauBudgetGroupe + " €).",
                        "Budget invalide",
                        JOptionPane.ERROR_MESSAGE);
                return false;
            }

            boolean groupeMiseAJour = mettreAJourBudgetGroupe(groupe.getNum(), nouveauBudgetGroupe);
            groupe.setBudgetAnnuelGlobal(nouveauBudgetGroupe);

            boolean themesMisesAJour = true;

            for (int i = 0; i < groupe.getSesThemes().size(); i++) {
                int nouveauBudgetTheme = Integer.parseInt(themeBudgetFields.get(i).getText());
                Theme theme = groupe.getSesThemes().get(i);
                theme.setMontant(nouveauBudgetTheme);

                boolean succesTheme = mettreAJourBudgetTheme(theme.getNum(), nouveauBudgetTheme);
                if (!succesTheme) {
                    themesMisesAJour = false;
                }
            }

            if (groupeMiseAJour && themesMisesAJour) {
                JOptionPane.showMessageDialog(null, "Modifications enregistrées avec succès !");
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Une erreur est survenue lors de la mise à jour.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return false;
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null,
                    "Veuillez entrer des valeurs numériques valides.",
                    "Erreur de saisie",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    // Méthode pour mettre à jour le budget du groupe via l'API PHP
    private boolean mettreAJourBudgetGroupe(int numGroupe, int budget) {
        try {
            String urlStr = URL_UPDATE_GROUPE + "?NumGroupe=" + numGroupe + "&MontantTotalDispo=" +
                    URLEncoder.encode(String.valueOf(budget), "UTF-8");

            HttpURLConnection connection = (HttpURLConnection) new URL(urlStr).openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                return true;
            } else {
                System.err.println("Erreur de mise à jour du budget du groupe : " + responseCode);
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Méthode pour mettre à jour le budget des thèmes via l'API PHP
    private boolean mettreAJourBudgetTheme(int numTheme, int budget) {
        try {
            String urlStr = URL_UPDATE_THEME + "?NumTheme=" + numTheme + "&MontantTheme=" +
                    URLEncoder.encode(String.valueOf(budget), "UTF-8");

            HttpURLConnection connection = (HttpURLConnection) new URL(urlStr).openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                return true;
            } else {
                System.err.println("Erreur de mise à jour du budget du thème : " + responseCode);
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
