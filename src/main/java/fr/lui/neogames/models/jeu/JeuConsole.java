package fr.lui.neogames.models.jeu;

import fr.lui.neogames.enums.Genre;
import fr.lui.neogames.enums.Plateforme;

public class JeuConsole extends Jeu {

    private boolean modeMultijoueurLocal;
    private boolean modeMultijoueurEnLigne;
    private boolean abonnementRequis;
    private int nombreJoueursMax;

    public JeuConsole(String titre, String editeur, double prix, Genre genre, Plateforme plateforme,
                      int stock, boolean occasion, boolean modeMultijoueurLocal,
                      boolean modeMultijoueurEnLigne, boolean abonnementRequis, int nombreJoueursMax) {
        super(titre, editeur, prix, genre, plateforme, stock, occasion);
        this.modeMultijoueurLocal = modeMultijoueurLocal;
        this.modeMultijoueurEnLigne = modeMultijoueurEnLigne;
        this.abonnementRequis = abonnementRequis;
        this.nombreJoueursMax = nombreJoueursMax;
    }
}
