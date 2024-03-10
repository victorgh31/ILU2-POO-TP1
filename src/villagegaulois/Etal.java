package villagegaulois;

import personnages.Gaulois;

public class Etal {
	//attributs de la classe Etal
	private Gaulois vendeur;
	private String produit;
	private int quantiteDebutMarche;
	private int quantite;
	private boolean etalOccupe = false;

	public boolean isEtalOccupe() {
		return etalOccupe;
	}
	
	//methode getVendeur
	public Gaulois getVendeur() {
		return vendeur;
	}
	
	//constructeur de la classe Etal 
	public void occuperEtal(Gaulois vendeur, String produit, int quantite) {
		this.vendeur = vendeur;
		this.produit = produit;
		this.quantite = quantite;
		quantiteDebutMarche = quantite;
		etalOccupe = true;
	}
	
	//methode libererEtal
	public String libererEtal() throws EtalNonOccupeException {
		if(!etalOccupe) {
			throw new EtalNonOccupeException("L'étal n'a pas été occupé par un vendeur.");
		}
		etalOccupe = false;
		StringBuilder chaine = new StringBuilder(
				"Le vendeur " + vendeur.getNom() + " quitte son étal, ");
		int produitVendu = quantiteDebutMarche - quantite;
		if (produitVendu > 0) {
			chaine.append(
					"il a vendu " + produitVendu + " parmi " + produit + ".\n");
		} else {
			chaine.append("il n'a malheureusement rien vendu.\n");
		}
		return chaine.toString();
	}
	
	//methode afficherEtal
	public String afficherEtal() {
		if (etalOccupe) {
			return "L'étal de " + vendeur.getNom() + " est garni de " + quantite
					+ " " + produit + "\n";
		}
		return "L'étal est libre";
	}
	
	//methode acheterProduit
	public String acheterProduit(int quantiteAcheter, Gaulois acheteur) {
		
		try {
			if (acheteur == null) {
				throw new AcheteurNullException("L'acheteur ne peut pas être null.");
			}
			
			if (!etalOccupe) {
	            throw new IllegalStateException("L'étal doit être occupé pour effectuer un achat.");
	        }
			
			if (quantiteAcheter < 1) {
	            throw new IllegalArgumentException("La quantité doit être positive.");
	        }
		
			StringBuilder chaine = new StringBuilder();
			chaine.append(acheteur.getNom() + " veut acheter " + quantiteAcheter
					+ " " + produit + " à " + vendeur.getNom());
			if (quantite == 0) {
				chaine.append(", malheureusement il n'y en a plus !");
				quantiteAcheter = 0;
			}
			if (quantiteAcheter > quantite) {
				chaine.append(", comme il n'y en a plus que " + quantite + ", "
						+ acheteur.getNom() + " vide l'étal de "
						+ vendeur.getNom() + ".\n");
				quantiteAcheter = quantite;
				quantite = 0;
			}
			if (quantite != 0) {
				quantite -= quantiteAcheter;
				chaine.append(". " + acheteur.getNom()
						+ ", est ravi de tout trouver sur l'étal de "
						+ vendeur.getNom() + "\n");
			}
			return chaine.toString();
		
		} catch (AcheteurNullException | IllegalArgumentException | IllegalStateException e) {
	        System.err.println("Erreur : " + e.getMessage());
	        return "";
	    }
	}
	
	//methode contientProduit
	public boolean contientProduit(String produit) {
		return this.produit.equals(produit);
	}

}
