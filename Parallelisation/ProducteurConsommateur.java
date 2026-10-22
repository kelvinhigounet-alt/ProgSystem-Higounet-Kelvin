import java.util.concurrent.Semaphore;
import java.util.LinkedList;
import java.util.Queue;

public class ProducteurConsommateur {

    private static final int TAILLE_BUFFER = 5;
    private final Integer[] buffer = new Integer[TAILLE_BUFFER];
    private int indiceAjout = 0;    // où le producteur écrit
    private int indiceRetrait = 0;  // où le consommateur lit

    private final Semaphore placesLibres = new Semaphore(TAILLE_BUFFER);
    private final Semaphore elementsDisponibles = new Semaphore(0);
    private final Semaphore mutexBuffer = new Semaphore(1); // quel erreur ici ? vous pouvez le résoudre plus tard

    int nbProduits = 0;
    int nbConsommes = 0;
    private int compteur = 0;

    private synchronized int genererProduit() {
        return ++compteur;
    }

    class Producteur implements Runnable {
        public void run() {
            try {
                for (int i = 0; i < 10; i++) {
                    int produit = genererProduit();
                    // TODO: Attendre une place libre
                    placesLibres.acquire();
                    // TODO: Acquérir l'accès exclusif au buffer  
                    mutexBuffer.acquire();
                    // TODO: Ajouter l'élément au buffer
                    buffer[indiceAjout] = produit;
                    indiceAjout = (indiceAjout + 1) % TAILLE_BUFFER;
                    nbProduits++;
                    // TODO: Libérer l'accès au buffer
                    mutexBuffer.release();
                    // TODO: Signaler qu'un élément est disponible
                    elementsDisponibles.release();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    class Consommateur implements Runnable {
        public void run() {
            try {
                for (int i = 0; i < 7; i++) {
                    // TODO: Attendre qu'un élément soit disponible
                    elementsDisponibles.acquire();
                    // TODO: Acquérir l'accès exclusif au buffer
                    mutexBuffer.acquire();
                    // TODO: Retirer un élément du buffer  
                    Integer produit = buffer[indiceRetrait];
                    buffer[indiceRetrait] = null;
                    indiceRetrait = (indiceRetrait + 1) % TAILLE_BUFFER;
                    nbConsommes++;
                    // TODO: Libérer l'accès au buffer
                    mutexBuffer.release();
                    // TODO: Signaler qu'une place est libre
                    placesLibres.release();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
