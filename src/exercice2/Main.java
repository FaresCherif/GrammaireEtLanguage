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
	 * De l'�tat [0] � l'�tat [2] avec le symbole [1]
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
	 * Liste de tous les couples d'�tats
	 */
	private static ArrayList<String[]> couplesEtats;
	
	/**
	 * Liste des etats s�parables trouv�s pendant l'algorithme
	 */
	private static ArrayList<String[]> etatsSeparables;
	
	/**
	 * Liste des etats fusionn�s pendant la minimisation
	 */
	private static ArrayList<String[]> etatsFusionnes;
	
	/**
	 * Liste des transitions mises � jour pour l'automate minimis�
	 */
	private static ArrayList<String[]> nouvellesTransitions;
	
	/**
	 * Liste des �tats mises � jour pour l'automate minimis�
	 */
	private static ArrayList<String> nouveauxEtats;
	
	/* 
	 * *********************** Main ***********************
	 */
	
	/**
	 * Fonction principale
	 * @param args : Param�tres optionnels
	 */
	public static void main(String[] args) {
		
		couplesEtats = new ArrayList<String[]>();
		
		/*
		 * Etape 1 : Trouver les couples d'�tats possibles
		 */
		
		print("---Etape 1 : Listes des couples d'�tats---");
		print("");
		
		//Pour chacun des �tats
		for (int i = 0; i < etats.length; i++) {
			//Pour chacun des autres �tats
			for (int j = i+1; j < etats.length; j++) {
				String[] couple = {etats[i], etats[j]};
				couplesEtats.add(couple);
				print(etats[i] + "-" + etats[j]);
			}
		}
		
		print("");
		print("---Fin �tape 1---");
		
		etatsSeparables = new ArrayList<String[]>();
		
		/*
		 * Etape 2 : Trouver les couples d'�tats s�parables/distinguables,
		 * uniquement en regardant si un des 2 �tats du couple est final
		 */
		
		print("");
		print("---Etape 2 : On rel�ve les �tats s�parables faciles � trouver (premi�re partie)---");
		print("");
		
		//Pour chacun des couples d'�tats
		for (int i = 0; i < couplesEtats.size(); i++) {
			String[] coupleEtat = couplesEtats.get(i); 
			if (estFinal(coupleEtat[0]) || estFinal(coupleEtat[1])) {
				etatsSeparables.add(coupleEtat);
				print(coupleEtat[0] + "-" + coupleEtat[1]);
			}
		}
		
		print("");
		print("---Fin �tape 2---");
		
		etatsFusionnes = new ArrayList<String[]>();
		
		/*
		 * Etape 3 : Ici on cherche � trouver les autres �tats s�prables,
		 * uniquement en regardant si pour un m�me symbole, un couple d'�tats pointe vers un couple d'�tats s�parables (r�cursion)
		 */
		
		print("");
		print("---Etape 3 : Listes des �tats autres �tats s�parables (inclus les pr�c�dents)---");
		print("");
		
		//Pour chacun des couples d'�tats existants
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
		print("---Fin �tape 3---");
		
		/*
		 * Etape 4 : D�duire l'automate minimis�
		 */
		
		print("");
		print("---Etape 4 : Automate minimis� obtenu---");
		print("");
		
		nouveauxEtats = new ArrayList<String>();
		
		print("Liste des �tats mis � jour : ");
		
		for (int i = 0; i < etats.length; i++) {
			
			if (!estDansUnEtatFusionne(etats[i])) {
				print("___On garde l'�tat : " + etats[i]);
				nouveauxEtats.add(etats[i]);
			}
		}
		
		for (int i = 0; i < etatsFusionnes.size(); i++) {
			String[] etatCourant = etatsFusionnes.get(i);
			String etatFusionne = etatCourant[0] + "," + etatCourant[1];
			print("___Fusion d'�tat : " + etatFusionne);
			nouveauxEtats.add(etatFusionne);
		}
		
		print("");
		print("---Fin �tape 4---");
		
		print("");
		print("***FIN DU PROGRAMME***");
	}
	
	/* 
	 * *********************** M�thodes ***********************
	 */
	
	/**
	 * M�thode/processus principal du programme pour d�terminer si un couple d'�tat est s�parable
	 * @param coupleEtat : couple d'�tat dont on veut d�terminer la s�parabilit�
	 * @return true si et seulement si l'�tat est s�parable
	 */
	public static boolean estSeparable(String[] coupleEtat) {
		
		if ((estFinal(coupleEtat[0]) && !estFinal(coupleEtat[1])) || (!estFinal(coupleEtat[0]) && estFinal(coupleEtat[1]))) {
			return true;
		}
		else {
			
			//Pour le couple courant, on mettra dans la liste les �tats point�s par les transitions de ce couple
			ArrayList<String> etatsPointes = new ArrayList<String>();
			
			//Pour chacun des symboles de l'alphabet
			for (int j = 0; j < symboles.length; j++) {
				//a //b
				
				//Pour chaque transitions
				for (int k = 0; k < transitions.length; k++) {
					
					//Si le symbole de transition de la transition courante est �gale au symbole courant
					if (transitions[k][1].equals(symboles[j])) {
						
						//Si la transition commence par un �tat du couple courant
						if(transitions[k][0].equals(coupleEtat[0]) || transitions[k][0].equals(coupleEtat[1])) {
							etatsPointes.add(transitions[k][2]);
						}
					}
				}
			}
			
			//Bool�en indiquant si le couple 1 d'�tats est en fait un seul �tat exemple "5-5"
			boolean etat1Unique = false;
			
			//Bool�en indiquant si le couple 2 d'�tats est en fait un seul �tat exemple "5-5"
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
	 * D�termine si l'�tat pass� en param�tre, est un �tat final
	 * @param etat : �tat � v�rifier
	 * @return true si et seulement si l'�tat est une cha�ne �gale et 
	 * �quivalente � une cha�ne du tableau des �tats finaux
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
	 * V�rifie si l'�tat pass� en param�tre est contenu dans un �tat fusionn�
	 * @param etat : �tat � v�rifier
	 * @return true si et seulement si l'�tat pass� en param�tre est contenu dans un �tat fusionn�
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
	 * @param chaine : cha�ne de charact�res � afficher dans la console 
	 */
	public static void print(String chaine) {
		System.out.println(chaine);
	}
}
