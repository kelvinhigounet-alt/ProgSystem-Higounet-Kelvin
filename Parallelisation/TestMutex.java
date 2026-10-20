public class TestMutex {

    public static void main(String[] args) {
        executerCompteur(9, 1_111_111, 50);

        for (int nbThreads : new int[]{1, 2, 5, 10}) {
            executerCompteur(nbThreads, 1111111, 20);
        }
		
		for (int nbIncrements : new int[]{100, 10000, 1000000}) {
            executerCompteur(9, nbIncrements, 20);
        }
    }


    private static void executerCompteur(int nbThreads, int nbIncrements, int nbTests) {
        
		int resultatAttendu = nbThreads * nbIncrements;
		int nbCorrects = 0;
		int min = 0;
        int max = 0;
		
		for (int test = 1; test <= nbTests; test++) {
            CompteurSecurite.resetCompteur();

            Thread[] threads = new Thread[nbThreads];
            for (int t = 0; t < nbThreads; t++) {
                threads[t] = new Thread(
                        new CompteurSecurite.Incrementeur("Thread" + t, nbIncrements));
            }
            for (Thread thread : threads) {
                thread.start();
            }
            for (Thread thread : threads) {
                try {
					thread.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}	
            }
			
			int resultatTest = CompteurSecurite.getCompteur();
			
			System.out.println("Test " + test + " : " + resultatTest + " / " + resultatAttendu
                    + (resultatTest == resultatAttendu ? "  OK" : "  PERTE de " + (resultatAttendu - resultatTest)));
			
			if (resultatTest == resultatAttendu) {
                nbCorrects++;
            }
			
			min = Math.min(min, resultatTest);
            max = Math.max(max, resultatTest);
		}
		
		double pourcentage = 100.0 * nbCorrects / nbTests;
        System.out.println(nbCorrects + "/" + nbTests + " corrects, "
                + pourcentage + " %, min = " + min
                + ", max = " + max + ", attendu = " + resultatAttendu);
    }
}