public class CalculParallele {
	private static final int[] DONNEES = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
	private static final int MULTIPLICATEUR = 1000;
	public static void main(String[] args) {		
		long debut = System.nanoTime();
		Calcule[] threads = new Calcule[DONNEES.length];
		
		for (int i = 0; i < DONNEES.length; i++) {
            threads[i] = new Calcule(DONNEES[i], MULTIPLICATEUR, i);
			threads[i].start();
        }
		
		long sommeTotal = 0;
		
		for (Calcule thread : threads) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}	
		sommeTotal += thread.getResultat();		
		}

		long duree = (System.nanoTime() - debut) / 1_000_000;
        System.out.println("Résultat total : " + sommeTotal);
        System.out.println("Durée : " + duree + " ms");
	}
}