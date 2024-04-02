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
	
	public void occuperEtal(Gaulois vendeur, String produit, int quantite) {
		this.vendeur = vendeur;
		this.produit = produit;
		this.quantite = quantite;
		quantiteDebutMarche = quantite;
		etalOccupe = true;
	}
	
	public String libererEtal() throws NullPointerException {
		etalOccupe = false;
		StringBuilder chaine = new StringBuilder("Le vendeur ");
		try {
			chaine.append(vendeur.getNom() + " quitte son étal, ");
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		
		int produitVendu = quantiteDebutMarche - quantite;
		if (produitVendu > 0) {
			chaine.append(
					"il a vendu " + produitVendu + " parmi " + produit + ".\n");
		} else {
			chaine.append("il n'a malheureusement rien vendu.\n");
		}
		return chaine.toString();
	}
	

	public String afficherEtal() {
		if (etalOccupe) {
			return "L'étal de " + vendeur.getNom() + " est garni de " + quantite
					+ " " + produit + "\n";
		}
		return "L'étal est libre";
	}
	
	public String acheterProduit(int quantiteAcheter, Gaulois acheteur) 
			throws NullPointerException, IllegalArgumentException, IllegalStateException{
		StringBuilder chaine = new StringBuilder();
		try {
			chaine.append(acheteur.getNom() + " veut acheter " + quantiteAcheter
					+ " " + produit + " à " + vendeur.getNom());
		} catch (NullPointerException e) {
			e.printStackTrace();
			return "";
		}
		if (quantiteAcheter < 1) {
			throw new IllegalArgumentException("la quantité est négative");
		}
		if (!etalOccupe) {
			throw new IllegalStateException("L'étal est inoccupé");
		}
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
	}
	
	public boolean contientProduit(String produit) {
		return this.produit.equals(produit);
	}

}
