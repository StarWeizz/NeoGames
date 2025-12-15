package fr.lui.neogames.models;

import fr.lui.neogames.models.jeu.Jeu;

import java.util.HashMap;
import java.util.Map;

public class Panier {
    private Client client;
    private Map<Jeu, Integer> articles;
    private boolean paye;
    private CodePromo codePromoApplique;

    public Panier(Client client) {
        this.client = client;
        this.articles = new HashMap<Jeu, Integer>();
        this.paye = false;
        this.codePromoApplique = null;
    }

    public void ajouterJeu(Jeu jeu, int quantite) {
        articles.put(jeu, quantite);
    }

    public Map<Jeu, Integer> getArticles() {
        return articles;
    }

    public boolean isPaye() {
        return paye;
    }

    public int getQuantite(Jeu jeu) {
        return articles.get(jeu);
    }

    public double getTotal() {
        double total = getArticles().entrySet().stream()
                .mapToDouble(entry -> entry.getKey().getPrice() * entry.getValue())
                .sum();

        // Appliquer le code promo si présent
        if (codePromoApplique != null && codePromoApplique.estValide()) {
            total = total * (1 - codePromoApplique.getReduction() / 100);
        }

        return total;
    }

    public double getTotalSansPromo() {
        return getArticles().entrySet().stream()
                .mapToDouble(entry -> entry.getKey().getPrice() * entry.getValue())
                .sum();
    }

    public void appliquerCodePromo(CodePromo codePromo) {
        if (codePromo != null && codePromo.estValide()) {
            this.codePromoApplique = codePromo;
        }
    }

    public void retirerCodePromo() {
        this.codePromoApplique = null;
    }

    public CodePromo getCodePromoApplique() {
        return codePromoApplique;
    }

    public void nettoyerPanier() {
        articles.clear();
        codePromoApplique = null;
    }
}
