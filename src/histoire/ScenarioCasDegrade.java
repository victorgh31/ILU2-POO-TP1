package histoire;

import villagegaulois.Etal;
import villagegaulois.EtalNonOccupeException;

public class ScenarioCasDegrade {
    public static void main(String[] args) {
        Etal etal = new Etal();

        try {
            etal.libererEtal();
            System.out.println("Fin du test");
        } catch (EtalNonOccupeException e) {
            System.out.println("Exception attrapée : " + e.getMessage());
        }
    }
}
