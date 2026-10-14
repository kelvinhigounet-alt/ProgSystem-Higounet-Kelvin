public class CalculParallele {
	public static void main(String[] args) {
		Calcule[] threads = new Calcule[DONNEES.length];
		
		for (int i = 0; i < DONNEES.length; i++) {
            threads[i] = new Calcule(DONNEES[i], MULTIPLICATEUR, i);
			threads[i].start();
        }
		
		long sommeTotal = 0;
		
		for (Calcule thread : threads) {
		thread.join();	
		sommeTotal += thread.getResultat();		
		}
	}
}