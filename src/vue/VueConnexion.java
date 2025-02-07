package vue;

import modele.Groupe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class VueConnexion extends JFrame {

    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton connexionButton;
    private JPanel groupesPanel;
    private JLabel titre;

    public VueConnexion() {
        setTitle("Connexion des Décideurs");
        setSize(700, 900);  
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(20, 20));

        // Panneau de gauche (image ou illustration)
        JPanel panelGauche = new JPanel();
        panelGauche.setBackground(new Color(33, 47, 61));
        panelGauche.setLayout(new BorderLayout());

        // Panneau principal (connexion)
        JPanel panelConnexion = new JPanel();
        panelConnexion.setLayout(new GridBagLayout());
        panelConnexion.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 10, 15, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        titre = new JLabel("Espace Décideur - Connexion");
        titre.setFont(new Font("SansSerif", Font.BOLD, 32));
        titre.setForeground(new Color(52, 152, 219));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panelConnexion.add(titre, gbc);

        gbc.gridwidth = 1;

        // Email
        JLabel emailLabel = new JLabel("Adresse Email");
        emailLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 1;
        panelConnexion.add(emailLabel, gbc);

        emailField = new JTextField(30);
        emailField.setFont(new Font("SansSerif", Font.PLAIN, 16));
        emailField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(52, 152, 219)));
        gbc.gridx = 1;
        gbc.gridy = 1;
        panelConnexion.add(emailField, gbc);

        // Mot de passe
        JLabel passwordLabel = new JLabel("Mot de passe");
        passwordLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 2;
        panelConnexion.add(passwordLabel, gbc);

        passwordField = new JPasswordField(30);
        passwordField.setFont(new Font("SansSerif", Font.PLAIN, 16));
        passwordField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(52, 152, 219)));
        gbc.gridx = 1;
        gbc.gridy = 2;
        panelConnexion.add(passwordField, gbc);

        // Bouton Connexion
        connexionButton = new JButton("Se Connecter");
        connexionButton.setFont(new Font("SansSerif", Font.BOLD, 18));
        connexionButton.setFocusPainted(false);
        connexionButton.setBackground(new Color(52, 152, 219));
        connexionButton.setForeground(Color.WHITE);
        connexionButton.setBorder(BorderFactory.createEmptyBorder(10, 40, 10, 40));
        connexionButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        connexionButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                connexionButton.setBackground(new Color(41, 128, 185));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                connexionButton.setBackground(new Color(52, 152, 219));
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panelConnexion.add(connexionButton, gbc);

        // Panneau dynamique pour l'affichage des groupes
        groupesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        groupesPanel.setBackground(new Color(245, 245, 245));

        JScrollPane scrollPane = new JScrollPane(groupesPanel);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Groupes Décideurs"));
        scrollPane.setBackground(Color.WHITE);
        scrollPane.getViewport().setBackground(Color.WHITE);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        panelConnexion.add(scrollPane, gbc);

        add(panelGauche, BorderLayout.WEST);
        add(panelConnexion, BorderLayout.CENTER);
    }

    public String getEmail() {
        return emailField.getText();
    }

    public String getMotDePasse() {
        return new String(passwordField.getPassword());
    }

    public void afficherGroupes(ArrayList<Groupe> groupes, ActionListener listener) {
        groupesPanel.removeAll();

        if (groupes.isEmpty()) {
            JLabel message = new JLabel("Aucun groupe disponible.");
            message.setFont(new Font("SansSerif", Font.BOLD, 18));
            message.setAlignmentX(Component.CENTER_ALIGNMENT);
            groupesPanel.add(message);
        } else {
            for (Groupe groupe : groupes) {
                JButton boutonGroupe = new JButton(groupe.getNom());
                Color couleurGroupe = Color.decode(groupe.getCouleur());
                boutonGroupe.setBackground(couleurGroupe);
                boutonGroupe.setForeground(Color.WHITE);
                boutonGroupe.setFont(new Font("SansSerif", Font.BOLD, 14));
                boutonGroupe.setFocusPainted(false);
                boutonGroupe.setPreferredSize(new Dimension(495, 70));
                boutonGroupe.setCursor(new Cursor(Cursor.HAND_CURSOR));

                boutonGroupe.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseEntered(java.awt.event.MouseEvent evt) {
                        boutonGroupe.setBackground(new Color(39, 174, 96));
                    }

                    public void mouseExited(java.awt.event.MouseEvent evt) {
                        Color couleurGroupe = Color.decode(groupe.getCouleur());
                        boutonGroupe.setBackground(couleurGroupe);
                    }
                });

                boutonGroupe.addActionListener(listener);
                boutonGroupe.setActionCommand(String.valueOf(groupe.getNum()));
                groupesPanel.add(boutonGroupe);
            }
        }
        groupesPanel.revalidate();
        groupesPanel.repaint();
    }

    public void ajouterEcouteur(ActionListener listener) {
        connexionButton.addActionListener(listener);
    }
}
