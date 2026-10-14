public class Calcule extends Thread {
    // TODO: Déclarer les attributs nécessaires
    private final int VALEUR;
    private final int MULTIPLICATEUR;
    private final int INDEX;
	private long resultat;

    public Calcule(int VALEUR, int MULTIPLICATEUR, int INDEX) {
        this.VALEUR = VALEUR;
        this.MULTIPLICATEUR = MULTIPLICATEUR;
        this.INDEX = INDEX;
    }

    @Override
    public void run() {
        // TODO: Implémenter le calcul intensif
        // Même logique que dans la boucle interne de CalculSequentiel
		long resultat = 0;
        for (int j = 0; j < MULTIPLICATEUR; j++) {
            resultat += VALEUR * VALEUR + VALEUR;
            
        }
        this.resultat = resultat;
    }

    public long getResultat() {
        return resultat;
	}
}