public class TestSemaphore {

    public static void main(String[] args) throws InterruptedException {
        scenario("Scénario équilibré", 2, 3);
        scenario("Scénario déséquilibré", 5, 2);
        System.exit(0);
    }

    static void scenario(String titre, int nbProducteurs, int nbConsommateurs) throws InterruptedException {
        System.out.println("=== " + titre + " : " + nbProducteurs + " producteurs, " + nbConsommateurs + " consommateurs ===");
        ProducteurConsommateur pc = new ProducteurConsommateur();

        for (int i = 0; i < nbProducteurs; i++) {
            new Thread(pc.new Producteur()).start();
        }
        for (int i = 0; i < nbConsommateurs; i++) {
            new Thread(pc.new Consommateur()).start();
        }

        for (int t = 0; t < 4; t++) {
            Thread.sleep(500);
            System.out.println("produits = " + pc.nbProduits + ", consommés = " + pc.nbConsommes
                    + ", dans le buffer = " + (pc.nbProduits - pc.nbConsommes));
        }
    }
}
