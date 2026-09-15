public class Gradient {
    public static void main(String[] args) {
        int width = 200;
        int height = 100;
        Image img = new Image(width, height);

        // Génération du dégradé de bleu (de 0 à 255 de gauche à droite)
        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                
                int bleu = x;
                
                img.setPixel(x, y, 0, 0, bleu);
            }
        }

        try {
            img.save_txt("gradient.ppm");
            System.out.println("Dégradé créé avec succès !");
        } catch (Exception e) {
            System.err.println("Erreur lors de la création du dégradé : " + e.getMessage());
        }
    }
}
