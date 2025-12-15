package fr.lui.neogames.models.jeu;

import fr.lui.neogames.enums.Genre;
import fr.lui.neogames.enums.Plateforme;

public class JeuRetro extends Jeu {

    private int annee;
    private boolean boiteOriginale;
    private boolean noticeIncluse;
    private String etatGeneral; // "Neuf", "Très bon", "Bon", "Moyen"
    private boolean collector;

    public JeuRetro(String titre, String editeur, double prix, Genre genre, Plateforme plateforme,
                    int stock, int annee, boolean boiteOriginale, boolean noticeIncluse,
                    String etatGeneral, boolean collector) {
        // Les jeux rétro sont toujours d'occasion sauf s'ils sont neufs collector
        super(titre, editeur, prix, genre, plateforme, stock, !etatGeneral.equals("Neuf"));
        this.annee = annee;
        this.boiteOriginale = boiteOriginale;
        this.noticeIncluse = noticeIncluse;
        this.etatGeneral = etatGeneral;
        this.collector = collector;
    }
}
