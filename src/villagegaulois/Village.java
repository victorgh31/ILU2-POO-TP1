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
	private Marche marche;

	public Village(String nom, int nbVillageoisMaximum, int nbEtalsMarche) {
		this.nom = nom;
		this.marche = new Marche(nbEtalsMarche);
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
		public Etal [] trouverEtals(String produit) {
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
		
		/* Méthode permettant de retourner une chaîne de caractères contenant l'affichage 
		 * de l'ensemble des étals occupés du marché. S'il reste des étals vide la chaîne 
		 * de retour se terminera par "Il reste + nbEtalVide + étals non utilisés dans le marché.\n".
		 */
		public String afficherMarche() {
			StringBuilder result = new StringBuilder();
			int nbEtalVide = 0;
			
			for (Etal etal : etals) {
				if (etal.isEtalOccupe())
					result.append(etal.afficherEtal()).append("\n");
				else
					nbEtalVide++;	
			}
			
			if (nbEtalVide > 0)
				result.append("Il reste " + nbEtalVide + " étals non utilisés dans le marché.\n");
	
			return result.toString();
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
	
	
	// Méthode permettant d'installer un vendeur sur le marché
	public String installerVendeur(Gaulois vendeur, String produit, int nbProduit) {
		 int indiceEtal = marche.trouverEtalLibre();
		 if (indiceEtal != -1) {
	    	marche.utiliserEtal(indiceEtal, vendeur, produit, nbProduit);
            return vendeur.getNom() + " vend des " + produit + " à l'étal n°" + (indiceEtal + 1) + ".";
        } else {
            return "Aucun étal disponible pour " + vendeur.getNom() + ".";
        }
    }

	// Méthode permettant de rechercher des vendeurs d'un produit spécifique sur le marché    
	public String rechercherVendeursProduit(String produit) {
        Etal[] etals = marche.trouverEtals(produit);
        if (etals.length > 0) {
            StringBuilder result = new StringBuilder("Les vendeurs qui proposent des " + produit + " sont : ");
            for (Etal etal : etals) {
                result.append("- ").append(etal.getVendeur().getNom()).append(" ");
            }
            return result.toString();
        } else {
            return "Aucun vendeur ne propose des " + produit + ".";
        }
    }

	// Méthode permettant de trouver l'étal où se trouve un vendeur
	public Etal rechercherEtal(Gaulois vendeur) {
        return marche.trouverVendeur(vendeur);
    }

    // Méthode permettant pour un vendeur de quitter le marché
    public String partirVendeur(Gaulois vendeur) throws EtalNonOccupeException {
        Etal etal = marche.trouverVendeur(vendeur);
        if (etal != null) {
            etal.libererEtal();
            return "Le vendeur " + vendeur.getNom() + " quitte son étal.";
        } else {
            return "Le vendeur " + vendeur.getNom() + " n'a pas d'étal.";
        }
    }

    // Méthode permettant d'afficher l'état sur le marché
    public String afficherMarche() {
        return marche.afficherMarche();
    }	
	
}