package application;

import controleur.ControleurConnexion;
import vue.VueConnexion;

public class Application {

    public static void main(String[] args) {
        VueConnexion vue = new VueConnexion();
        new ControleurConnexion(vue);
        vue.setVisible(true);
    }
}