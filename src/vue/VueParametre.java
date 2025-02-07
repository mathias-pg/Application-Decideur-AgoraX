package vue;

import controleur.ControleurParametre;
import modele.Groupe;
import modele.Theme;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class VueParametre extends JFrame {

    private Groupe groupe;
    private VueProposition vueProposition;
    private JTextField budgetField;
    private JPanel themePanel;
    private ArrayList<JTextField> themeBudgetFields;

    private ControleurParametre controleurParametre;

    public VueParametre(Groupe groupe, VueProposition vueProposition) {
        this.groupe = groupe;
        this.vueProposition = vueProposition;

        setTitle("Paramètres du Groupe - " + groupe.getNom());
        setSize(1200, 900);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(20, 20));

        // En-tête stylé
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(41, 128, 185));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        JLabel titre = new JLabel(groupe.getNom(), SwingConstants.CENTER);
        titre.setFont(new Font("SansSerif", Font.BOLD, 42));
        titre.setForeground(Color.WHITE);

        headerPanel.add(titre, BorderLayout.CENTER);

        // Section Budget avec icône et meilleure disposition
        JPanel budgetPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 40, 20));
        budgetPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(41, 128, 185), 2),
                "Budget du Groupe", 0, 0, new Font("SansSerif", Font.BOLD, 24), new Color(41, 128, 185)));

        JLabel budgetLabel = new JLabel("Budget Annuel Global (€) :");
        budgetLabel.setFont(new Font("SansSerif", Font.BOLD, 26));

        budgetField = new JTextField(10);
        budgetField.setFont(new Font("SansSerif", Font.PLAIN, 26));
        budgetField.setHorizontalAlignment(JTextField.CENTER);
        budgetField.setText(String.valueOf(groupe.getBudgetAnnuelGlobal()));

        budgetPanel.add(budgetLabel);
        budgetPanel.add(budgetField);

        // Section des budgets des thèmes
        themePanel = new JPanel();
        themePanel.setLayout(new BoxLayout(themePanel, BoxLayout.Y_AXIS));
        themePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(41, 128, 185), 2),
                "Budgets des Thèmes (€)", 0, 0, new Font("SansSerif", Font.BOLD, 24), new Color(41, 128, 185)));

        themeBudgetFields = new ArrayList<>();

        for (Theme theme : groupe.getSesThemes()) {
            ajouterBudgetTheme(theme);
        }

        controleurParametre = new ControleurParametre(groupe, budgetField, themeBudgetFields);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 30));

        JButton enregistrerBtn = new JButton("Enregistrer");
        styliserBouton(enregistrerBtn, new Color(46, 204, 113));

        JButton retourBtn = new JButton("Retour");
        styliserBouton(retourBtn, new Color(231, 76, 60));

        retourBtn.addActionListener(e -> {
            this.dispose();
            vueProposition.setVisible(true);
        });

        enregistrerBtn.addActionListener(e -> {
            boolean succes = controleurParametre.enregistrerModifications();
            if (succes) {
                this.dispose();
                vueProposition.setVisible(true);
            }
        });

        buttonPanel.add(enregistrerBtn);
        buttonPanel.add(retourBtn);

        add(headerPanel, BorderLayout.NORTH);
        add(budgetPanel, BorderLayout.CENTER);
        add(new JScrollPane(themePanel), BorderLayout.EAST);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    // Méthode pour ajouter un champ de budget pour chaque thème existant
    private void ajouterBudgetTheme(Theme theme) {
        JPanel themeRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 15));

        JLabel themeLabel = new JLabel(theme.getNom() + " :");
        themeLabel.setFont(new Font("SansSerif", Font.BOLD, 22));

        JTextField themeBudgetField = new JTextField(10);
        themeBudgetField.setFont(new Font("SansSerif", Font.PLAIN, 22));
        themeBudgetField.setHorizontalAlignment(JTextField.CENTER);
        themeBudgetField.setText(String.valueOf(theme.getMontant()));

        themeBudgetFields.add(themeBudgetField);

        themeRow.add(themeLabel);
        themeRow.add(themeBudgetField);
        themeRow.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(189, 195, 199)));
        themePanel.add(themeRow);
        themePanel.add(Box.createVerticalStrut(10));
        themePanel.revalidate();
        themePanel.repaint();
    }

    private void styliserBouton(JButton bouton, Color bgColor) {
        bouton.setFont(new Font("SansSerif", Font.BOLD, 22));
        bouton.setForeground(Color.WHITE);
        bouton.setBackground(bgColor);
        bouton.setBorder(BorderFactory.createEmptyBorder(15, 40, 15, 40));
        bouton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        bouton.setFocusPainted(false);

        bouton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                bouton.setBackground(bgColor.darker());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                bouton.setBackground(bgColor);
            }
        });
    }
}
