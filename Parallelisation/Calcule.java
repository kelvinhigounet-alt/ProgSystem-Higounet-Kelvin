public class Calcule extends Thread {
    // TODO: Déclarer les attributs nécessaires
    private final int valeur;
    private final int multiplicateur;
    private final int index;

    public Calcule(int valeur, int multiplicateur, int index) {
        this.valeur = valeur;
        this.multiplicateur = multiplicateur;
        this.index = index;
    }

    @Override
    public void run() {
        // TODO: Implémenter le calcul intensif
        // Même logique que dans la boucle interne de CalculSequentiel
		long resultat = 0;
        for (int j = 0; j < multiplicateur; j++) {
            resultat += valeur * valeur + valeur;
            
        }
        this.resultat = resultat;
    }

    public long getResultat() {
        return resultat;
	}
}