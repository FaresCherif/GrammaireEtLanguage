package exercice1;

import java.util.ArrayList;

/**
 * Contexte : Programme principal de l'exercice 1 du TP de Grammaires et Langages
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
			{"1","a","2"},
			{"1","a","3"},
			{"1","b","4"},
			{"2","b","4"},
			{"4","b","4"},
			{"3","b","5"},
			{"6","b","4"},
			{"5","a","6"},
			{"3","a","6"}
	};
	
	/**
	 * Etat initial
	 */
	private static String eI = "1";
	
	/**
	 * Etat final
	 */
	private static String eF = "6";
	
	/**
	 * Liste des symboles de l'alphabet
	 */
	private static String[] symboles = {"a","b"};
	
	/*
	 * *********************** Variables ***********************
	 */
	
	/**
	 * Liste des super états
	 */
	private static ArrayList<String> supersEtats;
	
	/**
	 * Liste des nouvelles transitions
	 */
	private static ArrayList<String[]> nouvellesTransitions;
	
	/* 
	 * *********************** Main ***********************
	 */
	
	/**
	 * Fonction principale
	 * @param args : Paramètres optionnels
	 */
	public static void main(String[] args) {
		
		//Init
		supersEtats = new ArrayList();
		supersEtats.add(eI);
		nouvellesTransitions = new ArrayList();
		//Fin init
		
		String superEtatFinal = "";
		
		int indexSuperEtat = 0;
		
		while(indexSuperEtat < supersEtats.size()) {
			
			//Découpage du nom du super état courant
			String[] splitedSuperEtat = (supersEtats.get(indexSuperEtat)).split("");
			
			afficherDecoupage(splitedSuperEtat);
			
			//Pour chaque symbole de l'alphabet
			for (int i = 0; i < symboles.length; i++) {
				
				String symboleCourant = symboles[i];
				
				System.out.println("Symbole courant : " + symboleCourant);
				
				String superEtat = "";
			
				//Pour chaque charactère du super état courant
				for(int h = 0; h < splitedSuperEtat.length; h++) {
					
					String charactereCourant = splitedSuperEtat[h];
					
					System.out.println("Char courant : " + charactereCourant);
					
					for (int j = 0; j < transitions.length; j++) {
						
						String[] transitionCourante = transitions[j];
						
						if ((transitionCourante[1]).equals(symboleCourant) && (transitionCourante[0]).equals(charactereCourant)) {
							
							superEtat += transitionCourante[2];
							
							if ((transitionCourante[2]).equals(eF)) {
								superEtatFinal = superEtat;
							}
						}
					}
					
					if (!superEtatExiste(superEtat)) {
						
						System.out.println("Ajouter : " + superEtat);
						supersEtats.add(superEtat);
					}
				}
				
				String[] nouvelleTransition = {supersEtats.get(indexSuperEtat),symboleCourant,superEtat};
				nouvellesTransitions.add(nouvelleTransition);
			}
			
			indexSuperEtat++;
			System.out.println("");
			System.out.println("________________________");
			System.out.println("_______tour_boucle______");
			System.out.println("");
		}
		
		System.out.println("Super état initial : " + supersEtats.get(0));
		
		System.out.println("Super état final : " + superEtatFinal);
		
		afficherSupersEtats();
		
		afficherNouvellesTransitions();
	}

	
	/*
	 * *********************** Méthodes ***********************
	 */
	
	/**
	 * Détermine si le super état passé en paramètre existe déjà
	 * @return true si et seulement si le super état existe déjà,
	 * c'est à dire s'il est déjà contenu la liste supersEtats
	 */
	public static boolean superEtatExiste(String superEtat) {
		for (int i = 0; i < supersEtats.size(); i++) {
			if ((supersEtats.get(i)).equals(superEtat)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Affiche la liste des supers états
	 */
	public static void afficherSupersEtats() {
		System.out.println("Liste des super Etats : ");
		for (int i = 0; i < supersEtats.size(); i++) {
			System.out.println("=> " + supersEtats.get(i));
		}
	}
	
	/**
	 * Affiche la liste des nouvelles transitions
	 */
	public static void afficherNouvellesTransitions() {
		System.out.println("Liste des nouvelles transitions obtenues : ");
		for (int i = 0; i < nouvellesTransitions.size(); i++) {
			String[] nouvelleTransitionCourante = nouvellesTransitions.get(i); 
			System.out.println("=> De l'état " + nouvelleTransitionCourante[0] + " à l'état " + nouvelleTransitionCourante[2] + " avec le symbole " + nouvelleTransitionCourante[1]);
		}
	}
	
	/**
	 * Affiche le découpage du super état courant
	 */
	public static void afficherDecoupage(String[] superEtat) {
		System.out.print("Découpage du super état : [");
		for (int i = 0; i < superEtat.length; i++) {
			System.out.print(" " + superEtat[i] + " ");
		}
		System.out.println("]");
	}
}
