package fr.lui.neogames.interfaces;

public interface Promotion {

    /**
     * Obtenir le pourcentage de réduction
     * @return Le pourcentage de réduction (ex: 20 pour 20%)
     */
    double getReduction();

    /**
     * Définir le pourcentage de réduction
     * @param reduction Le pourcentage de réduction
     */
    void setReduction(double reduction);

    /**
     * Calculer le prix avec la promotion appliquée
     * @param prixOriginal Le prix original de l'article
     * @return Le prix après réduction
     */
    default double getPrixAvecPromo(double prixOriginal) {
        return prixOriginal * (1 - getReduction() / 100);
    }

    /**
     * Vérifier si l'article est en promotion
     * @return true si en promotion, false sinon
     */
    default boolean estEnPromotion() {
        return getReduction() > 0;
    }
}
