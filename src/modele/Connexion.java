package modele;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Connexion {

	private static ArrayList<Internaute> internautes = new ArrayList<>();

    // Charger tous les internautes
    public static void chargerInternautes() {
        String url = "https://projets.iut-orsay.fr/saes3-dteixei/ProjetS3/json/get_internautes.php";

        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Erreur HTTP : " + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String ligne;
            while ((ligne = br.readLine()) != null) {
                response.append(ligne);
            }

            br.close();
            conn.disconnect();

            JSONArray internautesJSON = new JSONArray(response.toString());
            internautes.clear();  

            for (int i = 0; i < internautesJSON.length(); i++) {
                JSONObject internauteJSON = internautesJSON.getJSONObject(i);

                Internaute internaute = new Internaute(
                    internauteJSON.getInt("NumInternaute"),
                    internauteJSON.getString("NomInternaute"),
                    internauteJSON.getString("PrenomInternaute"),
                    internauteJSON.getString("AdresseMail"),
                    internauteJSON.getString("MotDePasse"),
                    internauteJSON.getString("AdressePostal"),
                    internauteJSON.getString("DateInscription"),
                    new ArrayList<>()
                );

                internaute.setSesGroupes(chargerGroupesDecideur(internaute.getNum()));

                internautes.add(internaute);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static ArrayList<Theme> chargerThemesPourGroupe(int numGroupe) {
        String url = "https://projets.iut-orsay.fr/saes3-dteixei/ProjetS3/json/get_themesGroupe.php?NumGroupe=" + numGroupe;
        ArrayList<Theme> themes = new ArrayList<>();

        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Erreur HTTP : " + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String ligne;
            while ((ligne = br.readLine()) != null) {
                response.append(ligne);
            }

            br.close();
            conn.disconnect();

            JSONArray themesJSON = new JSONArray(response.toString());

            for (int i = 0; i < themesJSON.length(); i++) {
                JSONObject themeJSON = themesJSON.getJSONObject(i);
                Theme theme = new Theme(
                    themeJSON.getInt("NumTheme"),
                    themeJSON.getString("NomTheme"),
                    themeJSON.getInt("MontantTheme")
                );
                themes.add(theme);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return themes;
    }

    // Charger les groupes où un internaute est décideur
    private static ArrayList<Groupe> chargerGroupesDecideur(int numInternaute) {
        String url = "https://projets.iut-orsay.fr/saes3-dteixei/ProjetS3/json/get_groupesPourDecideur.php?NumInternaute=" + numInternaute;
        ArrayList<Groupe> groupes = new ArrayList<>();

        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Erreur HTTP : " + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String ligne;
            while ((ligne = br.readLine()) != null) {
                response.append(ligne);
            }

            br.close();
            conn.disconnect();

            JSONArray groupesJSON = new JSONArray(response.toString());

            for (int i = 0; i < groupesJSON.length(); i++) {
                JSONObject groupeJSON = groupesJSON.getJSONObject(i);
                Groupe groupe = new Groupe(
                    groupeJSON.getInt("NumGroupe"),
                    groupeJSON.getString("NomGroupe"),
                    groupeJSON.getString("DescriptionGroupe"),
                    groupeJSON.getString("CouleurGroupe"),
                    groupeJSON.getString("DateCreation"),
                    groupeJSON.getInt("MontantTotalDispo"),
                    groupeJSON.getInt("NbInternautes")
                );

                ArrayList<Theme> themes = chargerThemesPourGroupe(groupe.getNum());
                groupe.setSesThemes(themes); 

                groupes.add(groupe);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return groupes;
    }


    // Vérification de connexion
    public static Internaute verifierConnexion(String email, String motDePasse) {
        for (Internaute internaute : internautes) {
            if (internaute.getEmail().equals(email) && internaute.getMotDePasse().equals(motDePasse)) {
                return internaute;
            }
        }
        return null;
    }
    
    public static ArrayList<Proposition> getPropositionsPourGroupe(int numGroupe) {
        ArrayList<Proposition> propositions = new ArrayList<>();

        String url = "https://projets.iut-orsay.fr/saes3-dteixei/ProjetS3/json/get_propositionsGroupe.php?NumGroupe=" + numGroupe;
        Groupe groupe = getGroupeById(numGroupe);

        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("HTTP Error : " + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line);
            }

            br.close();
            conn.disconnect();

            JSONArray jsonPropositions = new JSONArray(response.toString());

            for (int i = 0; i < jsonPropositions.length(); i++) {
                JSONObject obj = jsonPropositions.getJSONObject(i);

                Proposition proposition = new Proposition(
                    obj.getInt("NumProposition"),
                    obj.getString("TitreProposition"),
                    obj.getString("DescriptionProposition"),
                    obj.getString("DateSoumission"),
                    obj.getString("DureeDiscussion"),
                    obj.getString("EtatProposition"),
                    obj.getInt("CoutProposition"),
                    obj.getInt("NbLikes"),
                    obj.getInt("NbVotesPour")
                );

                String themeUrl = "https://projets.iut-orsay.fr/saes3-dteixei/ProjetS3/json/get_themeProposition.php?NumProposition=" + proposition.getNum();

                HttpURLConnection themeConn = (HttpURLConnection) new URL(themeUrl).openConnection();
                themeConn.setRequestMethod("GET");
                themeConn.setRequestProperty("Accept", "application/json");

                if (themeConn.getResponseCode() == 200) {
                    BufferedReader themeBr = new BufferedReader(new InputStreamReader(themeConn.getInputStream()));
                    StringBuilder themeResponse = new StringBuilder();
                    String themeLine;
                    while ((themeLine = themeBr.readLine()) != null) {
                        themeResponse.append(themeLine);
                    }
                    themeBr.close();
                    themeConn.disconnect();

                    JSONArray jsonThemes = new JSONArray(themeResponse.toString());
                    if (jsonThemes.length() > 0) {
                        JSONObject themeObj = jsonThemes.getJSONObject(0);

                        Theme theme = new Theme(
                            themeObj.getInt("NumTheme"),
                            themeObj.getString("NomTheme"),
                            themeObj.getInt("MontantTheme")
                        );

                        proposition.setSonTheme(theme);
                    }
                }

                proposition.setSonGroupe(groupe);
                propositions.add(proposition);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return propositions;
    }

    
    public static Groupe getGroupeById(int numGroupe) {
        for (Internaute internaute : internautes) {
            for (Groupe groupe : internaute.getSesGroupes()) {
                if (groupe.getNum() == numGroupe) {
                    return groupe;
                }
            }
        }
        return null; 
    }

}
