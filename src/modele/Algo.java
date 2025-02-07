package modele;

import java.util.ArrayList;

public class Algo {

	public static List<Proposition> minimiserBudgetGlouton(List<Proposition> propositions) {
		// Vérifie si la liste de propositions est vide ou nulle
		if (propositions == null || propositions.length() == 0) {
			return null;
		}

		// Récupère le nombre total d'internautes et les votes satisfaits par la première proposition
		int nbInternautes = propositions.data().getSonGroupe().getNbInternautes();
		int votesSatisfaits = propositions.data().getNbVotesPour();

		// Appelle la méthode récursive pour minimiser le budget
		return minimiserBudgetGloutonRecursif(propositions, votesSatisfaits, nbInternautes);
	}

	private static List<Proposition> minimiserBudgetGloutonRecursif(List<Proposition> propositions, int votesSatisfaits, int nbInternautes) {
		// Condition d'arrêt : si tous les votes sont satisfaits ou si la liste de propositions est vide
		if (votesSatisfaits >= nbInternautes || propositions == null || propositions.length() == 0) {
			return null;
		}

		// Trouve la proposition avec le coût le plus faible
		Proposition minProp = propositions.data();
		List<Proposition> temp = propositions.tail();

		while (temp != null) {
			if (temp.data().getCout() < minProp.getCout()) {
				minProp = temp.data();
			}
			temp = temp.tail();
		}

		// Ajoute la proposition avec le coût le plus faible à la liste des résultats
		List<Proposition> resultat = new List<>(minProp);
		votesSatisfaits += minProp.getNbVotesPour();

		// Enlève la proposition sélectionnée de la liste et continue récursivement
		List<Proposition> restant = removeProposition(propositions, minProp);
		List<Proposition> recursiveResultat = minimiserBudgetGloutonRecursif(restant, votesSatisfaits, nbInternautes);

		// Ajoute les résultats récursifs à la liste des résultats
		if (recursiveResultat != null) {
			resultat.setTail(recursiveResultat);
		}

		return resultat;
	}

	public static List<Proposition> maximiserVotesGlouton(List<Proposition> propositions, int budgetMax) {
		if (propositions == null || propositions.length() == 0 || budgetMax <= 0) {
			return null;
		}

		Proposition meilleurProposition = null;
		List<Proposition> prop = propositions;

		// On choisit la proposition qui a le plus de votes
		while (prop != null) {
			if (prop.data().getCout() <= budgetMax &&
					prop.data().getSonTheme().getMontant() >= prop.data().getCout() &&
					(meilleurProposition == null || prop.data().getNbVotesPour() > meilleurProposition.getNbVotesPour())) {
				meilleurProposition = prop.data();
			}
			prop = prop.tail();
		}

		if (meilleurProposition == null) {
			return null;
		}

		List<Proposition> result = new List<>(meilleurProposition);
		meilleurProposition.getSonTheme().setMontant(meilleurProposition.getSonTheme().getMontant() - meilleurProposition.getCout());
		List<Proposition> reste = removeProposition(propositions, meilleurProposition);

		List<Proposition> resultatRecursif = maximiserVotesGlouton(reste, budgetMax - meilleurProposition.getCout());
		if (resultatRecursif != null) {
			result.setTail(resultatRecursif);
		}

		return result;
	}

	public static List<Proposition> maximiserVotesGlouton(ArrayList<Theme> themes, List<Proposition> propositions) {
		// Cas de base : plus de thèmes à traiter
		if (themes.isEmpty()) {
			return null; // Pas de propositions à ajouter
		}

		// Récupérer et supprimer le premier thème
		Theme themeActuel = themes.remove(0);

		// Trouver la meilleure proposition pour ce thème
		Proposition meilleureProposition = null;
		int maxVotes = 0;

		List<Proposition> currentPropositions = propositions;
		while (currentPropositions != null) {
			Proposition proposition = currentPropositions.data();
			if (proposition.getSonTheme().getNum() == themeActuel.getNum() && proposition.getNbVotesPour() > maxVotes) {
				meilleureProposition = proposition;
				maxVotes = proposition.getNbVotesPour();
			}
			currentPropositions = currentPropositions.tail();
		}

		// Récursivité : récupérer la meilleure combinaison pour les thèmes restants
		List<Proposition> combinaisonRestante = maximiserVotesGlouton(themes, propositions);

		// Si une meilleure proposition a été trouvée, l'ajouter à la combinaison
		if (meilleureProposition != null) {
			return new List<>(meilleureProposition, combinaisonRestante);
		} else {
			// Sinon, retourner uniquement la combinaison restante
			return combinaisonRestante;
		}
	}

	private static List<Proposition> removeProposition(List<Proposition> propositions, Proposition toRemove) {
		if (propositions == null) return null;
		if (propositions.data().equals(toRemove)) {
			return propositions.tail();
		} else {
			return new List<>(propositions.data(), removeProposition(propositions.tail(), toRemove));
		}
	}

	private static int sumVotes(List<Proposition> propositions) {
		if (propositions == null) return 0;
		return propositions.data().getNbVotesPour() + sumVotes(propositions.tail());
	}

	public static <T> ArrayList<T> convertListToArrayList(List<T> list) {
		ArrayList<T> arrayList = new ArrayList<>();
		while (list != null) {
			arrayList.add(list.data());
			list = list.tail();
		}
		return arrayList;
	}

	public static <T> List<T> convertirArrayListToList(ArrayList<T> arrayList) {
		if (arrayList == null || arrayList.isEmpty()) {
			return null;
		}

		List<T> list = new List<>(arrayList.get(0));
		List<T> current = list;

		for (int i = 1; i < arrayList.size(); i++) {
			current.setTail(new List<>(arrayList.get(i)));
			current = current.tail();
		}

		return list;
	}

	public static void displayPropositions(ArrayList<Proposition> propositions) {
		for(int i = 0; i < propositions.size(); i++) {
			System.out.println(propositions.get(i));
		}
	}
}
