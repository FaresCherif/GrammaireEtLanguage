package exercice1;

import java.util.ArrayList;

/**
 * Contexte : Programme principal de l'exercice 1 du TP de Grammaires et Langages
 * Date : 17 Janvier 2021
 * @author Antonin GIROIRE et Fares SCHERIF
 */
public class Main {
	
	// *********************** Initialisation ***********************
	//Exemple utilis� : http://www.desmontils.net/emiage/Module209EMiage/c5/Ch5_8.htm
	//Fonctionne aussi sur l'exemple vu en cours
	
	/**
	 * Liste des transitions
	 * De l'�tat [0] � l'�tat [2] avec le symbole [1]
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
	 * Etats finaux
	 */
	private static String[] etatsFinaux = {"3","4","5","6"};
	
	/**
	 * Liste des symboles de l'alphabet
	 */
	private static String[] symboles = {"a","b"};
	
	/*
	 * *********************** Variables ***********************
	 */
	
	/**
	 * Liste des super �tats
	 */
	private static ArrayList<String> supersEtats;
	
	/**
	 * Liste des nouvelles transitions
	 */
	private static ArrayList<String[]> nouvellesTransitions;
	
	/**
	 * Liste des supers �tats finaux (il peut y en avoir qu'un seul)
	 */
	private static ArrayList<String> supersEtatsFinaux;
	
	/* 
	 * *********************** Main ***********************
	 */
	
	/**
	 * Fonction principale
	 * @param args : Param�tres optionnels
	 */
	public static void main(String[] args) {
		
		//Init
		supersEtats = new ArrayList();
		supersEtatsFinaux = new ArrayList();
		supersEtats.add(eI);
		nouvellesTransitions = new ArrayList();
		//Fin init
		
		int indexSuperEtat = 0;
		
		while(indexSuperEtat < supersEtats.size()) {
			
			//D�coupage du nom du super �tat courant
			String[] splitedSuperEtat = (supersEtats.get(indexSuperEtat)).split("");
			
			afficherDecoupage(splitedSuperEtat);
			
			//Pour chaque symbole de l'alphabet
			for (int i = 0; i < symboles.length; i++) {
				
				String symboleCourant = symboles[i];
				
				System.out.println("Symbole courant : " + symboleCourant);
				
				String superEtat = "";
			
				//Pour chaque charact�re du super �tat courant
				for(int h = 0; h < splitedSuperEtat.length; h++) {
					
					String charactereCourant = splitedSuperEtat[h];
					
					System.out.println("Char courant : " + charactereCourant);
					
					for (int j = 0; j < transitions.length; j++) {
						
						String[] transitionCourante = transitions[j];
						
						if ((transitionCourante[1]).equals(symboleCourant) && (transitionCourante[0]).equals(charactereCourant)) {
							
							superEtat += transitionCourante[2];
							
							//Si le super �tat contient un �tat final
							if (estFinal(transitionCourante[2]) && !superEtatExiste(supersEtatsFinaux, superEtat)) {
								supersEtatsFinaux.add(superEtat);
							}
						}
					}
					
					if (!superEtatExiste(supersEtats, superEtat) && !superEtat.equals("")) {
						
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
		
		System.out.println("Super �tat initial : " + supersEtats.get(0));
		
		afficherSupersEtatsFinaux();
		
		afficherSupersEtats();
		
		afficherNouvellesTransitions();
	}

	
	/*
	 * *********************** M�thodes ***********************
	 */
	
	/**
	 * D�termine si le super �tat pass� en param�tre existe d�j� dans la liste, pass� en param�tre elle aussi
	 * @return true si et seulement si le super �tat existe d�j�,
	 * c'est � dire s'il est d�j� contenu la liste pass� en param�tre
	 */
	public static boolean superEtatExiste(ArrayList<String> listeSupersEtats, String superEtat) {
		for (int i = 0; i < listeSupersEtats.size(); i++) {
			if ((listeSupersEtats.get(i)).equals(superEtat)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * D�termine si l'�tat pass� en param�tre, est un �tat final
	 * @return true si et seulement si l'�tat (superEtat) est une cha�ne �gale et 
	 * �quivalente � une cha�ne du tableau des �tats finaux
	 */
	public static boolean estFinal(String superEtat) {
		for (int i = 0; i < etatsFinaux.length; i++) {
			if (etatsFinaux[i].equals(superEtat)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Affiche la liste des supers �tats
	 */
	public static void afficherSupersEtats() {
		System.out.println("Liste des super Etats : ");
		for (int i = 0; i < supersEtats.size(); i++) {
			System.out.println("=> " + supersEtats.get(i));
		}
		System.out.println("");
	}
	
	/**
	 * Affiche la liste des supers �tats finaux
	 */
	public static void afficherSupersEtatsFinaux() {
		System.out.println("Liste des super Etats finaux : ");
		for (int i = 0; i < supersEtatsFinaux.size(); i++) {
			System.out.println("=> " + supersEtatsFinaux.get(i));
		}
		System.out.println("");
	}
	
	/**
	 * Affiche la liste des nouvelles transitions � partir des super �tats
	 */
	public static void afficherNouvellesTransitions() {
		System.out.println("Liste des nouvelles transitions obtenues : ");
		for (int i = 0; i < nouvellesTransitions.size(); i++) {
			String[] nouvelleTransitionCourante = nouvellesTransitions.get(i); 
			if (!nouvelleTransitionCourante[2].equals("")) {
				System.out.println("=> De l'�tat " + nouvelleTransitionCourante[0] + " � l'�tat " + nouvelleTransitionCourante[2] + " avec le symbole " + nouvelleTransitionCourante[1]);
			}
		}
		System.out.println("");
	}
	
	/**
	 * Affiche le d�coupage du super �tat pass� en param�tre
	 */
	public static void afficherDecoupage(String[] superEtat) {
		System.out.print("D�coupage du super �tat : [");
		for (int i = 0; i < superEtat.length; i++) {
			System.out.print(" " + superEtat[i] + " ");
		}
		System.out.println("]");
		System.out.println("");
	}
}
