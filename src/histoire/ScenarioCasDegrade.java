package histoire;

import personnages.Gaulois;
import villagegaulois.AcheteurNullException;
import villagegaulois.Etal;
import villagegaulois.EtalNonOccupeException;

public class ScenarioCasDegrade {
    public static void main(String[] args) {
        Etal etal = new Etal();

        try {
            etal.libererEtal();
            System.out.println("Fin du test");
            etal.acheterProduit(-5, new Gaulois("Acheteur", 10));
            System.out.println("Fin du test");
        } catch (EtalNonOccupeException | AcheteurNullException | IllegalArgumentException | IllegalStateException e) {
            System.out.println("Exception attrapée : " + e.getMessage());
        }
    }
}
