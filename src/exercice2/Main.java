package exercice2;

import java.util.ArrayList;

/**
 * Contexte : Programme principal de l'exercice 2 du TP de Grammaires et Langages
 * Date : 17 Janvier 2021
 * @author Antonin GIROIRE et Fares SCHERIF
 */
public class Main {
	
	// *********************** Initialisation : exemple du cours ***********************
	
	/**
	 * Liste des transitions
	 * De l'état [0] à l'état [2] avec le symbole [1]
	 */
	private static String[][] transitions = {
			{"0","a","1"},
			{"0","b","4"},
			{"1","a","5"},
			{"1","b","2"},
			{"2","b","2"},
			{"3","a","6"},
			{"3","b","4"},
			{"4","a","2"},
			{"4","b","5"},
			{"5","a","5"},
			{"5","b","3"},
			{"6","a","5"},
			{"6","b","2"},

	};
	
	/**
	 * Liste des etats
	 */
	private static String[] etats = {"0","1","2","3","4","5","6"};
	
	/**
	 * Etat initial
	 */
	private static String eI = "0";
	
	/**
	 * Etats finaux
	 */
	private static String[] etatsFinaux = {"2"};
	
	/**
	 * Liste des symboles de l'alphabet
	 */
	private static String[] symboles = {"a","b"};
	
	/*
	 * *********************** Variables ***********************
	 */
	
	/**
	 * Liste de tous les couples d'états
	 */
	private static ArrayList<String[]> couplesEtats;
	
	/**
	 * Liste des etats séparables trouvés pendant l'algorithme
	 */
	private static ArrayList<String[]> etatsSeparables;
	
	/**
	 * Liste des etats fusionnés pendant la minimisation
	 */
	private static ArrayList<String[]> etatsFusionnes;
	
	/**
	 * Liste des transitions mises à jour pour l'automate minimisé
	 */
	private static ArrayList<String[]> nouvellesTransitions;
	
	/**
	 * Liste des états mises à jour pour l'automate minimisé
	 */
	private static ArrayList<String> nouveauxEtats;
	
	/* 
	 * *********************** Main ***********************
	 */
	
	/**
	 * Fonction principale
	 * @param args : Paramètres optionnels
	 */
	public static void main(String[] args) {
		
		couplesEtats = new ArrayList<String[]>();
		
		/*
		 * Etape 1 : Trouver les couples d'états possibles
		 */
		
		print("---Etape 1 : Listes des couples d'états---");
		print("");
		
		//Pour chacun des états
		for (int i = 0; i < etats.length; i++) {
			//Pour chacun des autres états
			for (int j = i+1; j < etats.length; j++) {
				String[] couple = {etats[i], etats[j]};
				couplesEtats.add(couple);
				print(etats[i] + "-" + etats[j]);
			}
		}
		
		print("");
		print("---Fin étape 1---");
		
		etatsSeparables = new ArrayList<String[]>();
		
		/*
		 * Etape 2 : Trouver les couples d'états séparables/distinguables,
		 * uniquement en regardant si un des 2 états du couple est final
		 */
		
		print("");
		print("---Etape 2 : On relève les états séparables faciles à trouver (première partie)---");
		print("");
		
		//Pour chacun des couples d'états
		for (int i = 0; i < couplesEtats.size(); i++) {
			String[] coupleEtat = couplesEtats.get(i); 
			if (estFinal(coupleEtat[0]) || estFinal(coupleEtat[1])) {
				etatsSeparables.add(coupleEtat);
				print(coupleEtat[0] + "-" + coupleEtat[1]);
			}
		}
		
		print("");
		print("---Fin étape 2---");
		
		etatsFusionnes = new ArrayList<String[]>();
		
		/*
		 * Etape 3 : Ici on cherche à trouver les autres états séprables,
		 * uniquement en regardant si pour un même symbole, un couple d'états pointe vers un couple d'états séparables (récursion)
		 */
		
		print("");
		print("---Etape 3 : Listes des états autres états séparables (inclus les précédents)---");
		print("");
		
		//Pour chacun des couples d'états existants
		for (int i = 0; i < couplesEtats.size(); i++) {
			//0-1
			
			String[] coupleEtat = couplesEtats.get(i);
			
			if(!estSeparable(coupleEtat)) {
				etatsFusionnes.add(coupleEtat);
			} else {
				etatsSeparables.add(coupleEtat);
				print(coupleEtat[0] + "-" + coupleEtat[1]);
			}

		}
		
		print("");
		print("---Fin étape 3---");
		
		/*
		 * Etape 4 : Déduire l'automate minimisé
		 */
		
		print("");
		print("---Etape 4 : Automate minimisé obtenu---");
		print("");
		
		nouveauxEtats = new ArrayList<String>();
		
		print("Liste des états mis à jour : ");
		
		for (int i = 0; i < etats.length; i++) {
			
			if (!estDansUnEtatFusionne(etats[i])) {
				print("___On garde l'état : " + etats[i]);
				nouveauxEtats.add(etats[i]);
			}
		}
		
		for (int i = 0; i < etatsFusionnes.size(); i++) {
			String[] etatCourant = etatsFusionnes.get(i);
			String etatFusionne = etatCourant[0] + "," + etatCourant[1];
			print("___Fusion d'état : " + etatFusionne);
			nouveauxEtats.add(etatFusionne);
		}
		
		print("");
		print("---Fin étape 4---");
		
		print("");
		print("***FIN DU PROGRAMME***");
	}
	
	/* 
	 * *********************** Méthodes ***********************
	 */
	
	/**
	 * Méthode/processus principal du programme pour déterminer si un couple d'état est séparable
	 * @param coupleEtat : couple d'état dont on veut déterminer la séparabilité
	 * @return true si et seulement si l'état est séparable
	 */
	public static boolean estSeparable(String[] coupleEtat) {
		
		if ((estFinal(coupleEtat[0]) && !estFinal(coupleEtat[1])) || (!estFinal(coupleEtat[0]) && estFinal(coupleEtat[1]))) {
			return true;
		}
		else {
			
			//Pour le couple courant, on mettra dans la liste les états pointés par les transitions de ce couple
			ArrayList<String> etatsPointes = new ArrayList<String>();
			
			//Pour chacun des symboles de l'alphabet
			for (int j = 0; j < symboles.length; j++) {
				//a //b
				
				//Pour chaque transitions
				for (int k = 0; k < transitions.length; k++) {
					
					//Si le symbole de transition de la transition courante est égale au symbole courant
					if (transitions[k][1].equals(symboles[j])) {
						
						//Si la transition commence par un état du couple courant
						if(transitions[k][0].equals(coupleEtat[0]) || transitions[k][0].equals(coupleEtat[1])) {
							etatsPointes.add(transitions[k][2]);
						}
					}
				}
			}
			
			//Booléen indiquant si le couple 1 d'états est en fait un seul état exemple "5-5"
			boolean etat1Unique = false;
			
			//Booléen indiquant si le couple 2 d'états est en fait un seul état exemple "5-5"
			boolean etat2Unique = false;

			if (etatsPointes.get(0).equals(etatsPointes.get(1))) {
				etat1Unique = true;
			}
			
			if (etatsPointes.get(2).equals(etatsPointes.get(3))) {
				etat2Unique = true;
			}
			
			String[] couple1 = {etatsPointes.get(0),etatsPointes.get(1)};
			String[] couple2 = {etatsPointes.get(2),etatsPointes.get(3)};
			
			if (etat1Unique && etat2Unique) {
				return false;
			}
			
			if (etat1Unique) {
				return (estSeparable(couple2));
			}
			
			if (etat2Unique) {
				return (estSeparable(couple1));
			}
			
			return (estSeparable(couple1) || estSeparable(couple2));
		}
	}
	
	/**
	 * Détermine si l'état passé en paramètre, est un état final
	 * @param etat : état à vérifier
	 * @return true si et seulement si l'état est une chaîne égale et 
	 * équivalente à une chaîne du tableau des états finaux
	 */
	public static boolean estFinal(String etat) {
		for (int i = 0; i < etatsFinaux.length; i++) {
			if (etatsFinaux[i].equals(etat)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Vérifie si l'état passé en paramètre est contenu dans un état fusionné
	 * @param etat : état à vérifier
	 * @return true si et seulement si l'état passé en paramètre est contenu dans un état fusionné
	 */
	public static boolean estDansUnEtatFusionne(String etat) {
		for (int i = 0; i < etatsFusionnes.size(); i++) {
			
			String[] etatCourant = etatsFusionnes.get(i);
			
			if (etat.equals(etatCourant[0]) || etat.equals(etatCourant[1])) {
				return true;
			}
		}
		
		return false;
	}

	/**
	 * Fonction usuel pour afficher des valeurs dans la console, plus court que System.out.println()
	 * @param chaine : chaîne de charactères à afficher dans la console 
	 */
	public static void print(String chaine) {
		System.out.println(chaine);
	}
}
