package fr.lui.neogames.models.jeu;

import fr.lui.neogames.enums.Genre;
import fr.lui.neogames.enums.Plateforme;

public class JeuPC extends Jeu {
    private String configMinimale;
    private String configRecommandee;
    private String platformeDistribution; // Steam, Epic, GOG, etc.

    public JeuPC(String titre, String editeur, double prix, Genre genre, int stock, boolean occasion,
                 String configMinimale, String configRecommandee, String platformeDistribution) {
        super(titre, editeur, prix, genre, Plateforme.PC, stock, occasion);
        this.configMinimale = configMinimale;
        this.configRecommandee = configRecommandee;
        this.platformeDistribution = platformeDistribution;
    }
}
