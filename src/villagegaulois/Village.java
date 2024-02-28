package villagegaulois;

import java.util.ArrayList;
import java.util.List;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;

	public Village(String nom, int nbVillageoisMaximum) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
	}
	
	private class Marche {
		//Attributs de la classe Interne
		private Etal[] etals;
		
		//Constructeur de la classe Interne
		public Marche(int nombreEtals) {
			etals = new Etal[nombreEtals];
			for (int i=0 ; i<nombreEtals ; i++)
				etals[i] = new Etal();
		}
		
		/* Méthode permettant de trouver un étal non occupé dans le tableau etals. 
		 * S'il n'y a pas d'étal disponible, la méthode retourne -1. */
		public void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			if (indiceEtal>=0 && indiceEtal<etals.length && !etals[indiceEtal].isEtalOccupe())
				etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
		}
		
		/* Méthode permettant de trouver un étal non occupé dans le tableau etals.
		 * S'il n'y d'étal disponible, la méthode retourne -1 */
		public int trouverEtalLibre() {
			for (int i=0 ; i<etals.length ; i++) {
				if (!etals[i].isEtalOccupe())
					return 1;
			}
			return -1;
		}
		
		/* Méthode qui retourne l'état sur lequel s'est installé le vendeur passé
		 * en paramètre d'entté ou null s'il n'y en a pas. */
		Etal [] trouverEtals(String produit) {
			List <Etal> matchingEtals = new ArrayList<>();
			for (Etal etal : etals) {
				if (etal.contientProduit(produit))
					matchingEtals.add(etal);						
			}
			return matchingEtals.toArray(new Etal[0]);
		}
		
		/* Méthode qui retourne l'étal sur lequel s'est installé le vendeur passé
		 * en paramètre d'entrée ou null s'il n'y en a pas. */
		public Etal trouverVendeur(Gaulois gaulois) {
			for (Etal etal : etals) {
				if (etal.getVendeur() == gaulois)
					return etal;
			}
			return null;
		}
	}
	
	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() {
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef "
					+ chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom()
					+ " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}
}