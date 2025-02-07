package vue;

import modele.Proposition;
import modele.Groupe;
import controleur.ControleurProposition;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class VueProposition extends JFrame {

	private JFrame vueConnexion;
	private Groupe groupe;
	private ControleurProposition controleur;

	public VueProposition(ArrayList<Proposition> propositions, Groupe groupe, JFrame vueConnexion) {
		this.vueConnexion = vueConnexion;
		this.groupe = groupe;
		groupe.setSesPropositions(propositions);

		setTitle("Propositions du Groupe");
		setSize(1200, 900);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout(20, 20));

		controleur = new ControleurProposition(groupe, this);

		// En-tête avec nom du groupe
		JPanel headerPanel = new JPanel(new BorderLayout());
		headerPanel.setBackground(new Color(52, 152, 219));
		headerPanel.setPreferredSize(new Dimension(1200, 100));
		headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

		JLabel nomGroupe = new JLabel(groupe.getNom(), SwingConstants.CENTER);
		nomGroupe.setFont(new Font("SansSerif", Font.BOLD, 36));
		nomGroupe.setForeground(Color.WHITE);

		ImageIcon icon = new ImageIcon(getClass().getResource("settings.png"));
		JButton paramButton = new JButton(icon);
		paramButton.setBorderPainted(false);
		paramButton.setContentAreaFilled(false);
		paramButton.setFocusPainted(false);
		paramButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		paramButton.setToolTipText("Modifier les paramètres du groupe");



		// Ajout de la barre de navigation sous le titre
		JPanel navigationBar = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
		navigationBar.setBackground(new Color(245, 245, 245));

		JButton trierBasiqueBtn = new JButton("Trier Basiquement");
		JButton trierMode1Btn = new JButton("Mode 1");
		JButton trierMode2Btn = new JButton("Mode 2");
		JButton trierMode3Btn = new JButton("Mode 3");

		styleBouton(trierBasiqueBtn);
		styleBouton(trierMode1Btn);
		styleBouton(trierMode2Btn);
		styleBouton(trierMode3Btn);
		

		// Action des boutons
		trierBasiqueBtn.addActionListener(e -> controleur.trierPropositions("basique"));
		trierMode1Btn.addActionListener(e -> controleur.trierPropositions("mode1"));
		trierMode2Btn.addActionListener(e -> controleur.trierPropositions("mode2"));
		trierMode3Btn.addActionListener(e -> controleur.trierPropositions("mode3"));

		// Ajout des boutons à la barre de navigation
		navigationBar.add(trierBasiqueBtn);
		navigationBar.add(trierMode1Btn);
		navigationBar.add(trierMode2Btn);
		navigationBar.add(trierMode3Btn);

		// Bouton retour
		JButton retourBtn = new JButton("Retour");
		retourBtn.setBackground(new Color(231, 76, 60));
		retourBtn.setForeground(Color.WHITE);
		retourBtn.setFocusPainted(false);
		retourBtn.setFont(new Font("SansSerif", Font.BOLD, 16));
		retourBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

		retourBtn.addActionListener(e -> {
			this.dispose();
			vueConnexion.setVisible(true);
		});
		
		navigationBar.add(retourBtn);

		add(navigationBar, BorderLayout.AFTER_LAST_LINE);


		// Appel au contrôleur pour ouvrir les paramètres
		paramButton.addActionListener(e -> controleur.ouvrirVueParametres());

		headerPanel.add(nomGroupe, BorderLayout.CENTER);
		headerPanel.add(paramButton, BorderLayout.EAST);

		// Panel des propositions
		JPanel propositionsPanel = new JPanel();
		propositionsPanel.setLayout(new BoxLayout(propositionsPanel, BoxLayout.Y_AXIS));
		propositionsPanel.setBackground(new Color(245, 245, 245));
		

		if (propositions.isEmpty()) {
			JLabel message = new JLabel("Aucune proposition pour ce groupe.");
			message.setFont(new Font("SansSerif", Font.BOLD, 24));
			message.setForeground(Color.GRAY);
			message.setHorizontalAlignment(SwingConstants.CENTER);
			propositionsPanel.add(message);
		} else {
			for (Proposition proposition : propositions) {
				propositionsPanel.add(creerPanelProposition(proposition));
				propositionsPanel.add(Box.createVerticalStrut(20));  // Espacement entre les propositions
			}
		}

		add(headerPanel, BorderLayout.NORTH);
		add(new JScrollPane(propositionsPanel), BorderLayout.CENTER);
	}

	private void styleBouton(JButton bouton) {
		bouton.setBackground(new Color(52, 152, 219));
		bouton.setForeground(Color.WHITE);
		bouton.setFocusPainted(false);
		bouton.setFont(new Font("SansSerif", Font.BOLD, 16));
		bouton.setCursor(new Cursor(Cursor.HAND_CURSOR));

		bouton.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				bouton.setBackground(new Color(41, 128, 185));
			}

			public void mouseExited(java.awt.event.MouseEvent evt) {
				bouton.setBackground(new Color(52, 152, 219));
			}
		});
	}


	// Crée un panel pour chaque proposition
	public JPanel creerPanelProposition(Proposition proposition) {
		JPanel panel = new JPanel(new BorderLayout(10, 10));
		panel.setBorder(BorderFactory.createEmptyBorder(15, 25, 15, 25));  
		panel.setBackground(Color.WHITE);
		panel.setPreferredSize(new Dimension(900, 150));
		panel.setMaximumSize(new Dimension(1000, 180));
		panel.setBorder(BorderFactory.createLineBorder(new Color(189, 195, 199), 1));

		JLabel titre = new JLabel(proposition.getTitre());
		titre.setFont(new Font("SansSerif", Font.BOLD, 22));
		titre.setForeground(new Color(41, 128, 185));

		JTextArea description = new JTextArea(proposition.getDescription());
		description.setWrapStyleWord(true);
		description.setLineWrap(true);
		description.setEditable(false);
		description.setFont(new Font("SansSerif", Font.PLAIN, 16));
		description.setBackground(new Color(250, 250, 250));

		JScrollPane scrollPane = new JScrollPane(description);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		scrollPane.setPreferredSize(new Dimension(750, 80));

		// Section pour modifier le budget
		JPanel budgetPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
		budgetPanel.setBackground(Color.WHITE);

		JLabel budgetLabel = new JLabel("Budget (€):");
		budgetLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));

		JTextField budgetField = new JTextField(String.valueOf(proposition.getCout()), 10);
		budgetField.setFont(new Font("SansSerif", Font.PLAIN, 18));

		JButton enregistrerBtn = new JButton("Enregistrer");
		enregistrerBtn.setBackground(new Color(39, 174, 96));
		enregistrerBtn.setForeground(Color.WHITE);
		enregistrerBtn.setFocusPainted(false);

		enregistrerBtn.addActionListener(e -> {
			try {
				int nouveauBudget = Integer.parseInt(budgetField.getText());
				controleur.mettreAJourBudget(proposition.getNum(), nouveauBudget);
				proposition.setCout(nouveauBudget);
				JOptionPane.showMessageDialog(null, "Budget mis à jour avec succès !");
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(null, "Veuillez entrer un budget valide.", "Erreur", JOptionPane.ERROR_MESSAGE);
			}
		});

		budgetPanel.add(budgetLabel);
		budgetPanel.add(budgetField);
		budgetPanel.add(enregistrerBtn);

		// Ajout au panel principal
		panel.add(titre, BorderLayout.NORTH);
		panel.add(scrollPane, BorderLayout.CENTER);
		panel.add(budgetPanel, BorderLayout.EAST);

		return panel;
	}
	
	public JFrame getVueConnexion() {
	    return vueConnexion;
	}

	public Groupe getGroupe() {
		return groupe;
	}

}
