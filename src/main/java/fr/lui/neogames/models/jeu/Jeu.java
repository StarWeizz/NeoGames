package fr.lui.neogames.models.jeu;

import fr.lui.neogames.enums.Genre;
import fr.lui.neogames.enums.Plateforme;
import fr.lui.neogames.interfaces.Promotion;
import javafx.beans.property.*;

public class Jeu implements Promotion {
    private final StringProperty nom = new SimpleStringProperty();
    private final StringProperty editeur = new SimpleStringProperty();
    private final DoubleProperty price = new SimpleDoubleProperty();
    private final ObjectProperty<Genre> genre = new SimpleObjectProperty<>();
    private final ObjectProperty<Plateforme> plateforme = new SimpleObjectProperty<>();
    private final IntegerProperty stock = new SimpleIntegerProperty();
    private final BooleanProperty occasion = new SimpleBooleanProperty();
    private final DoubleProperty reduction = new SimpleDoubleProperty(0);

    public Jeu(String nom, String editeur, double price, Genre genre, Plateforme plateforme, int stock, boolean occasion) {
        this.nom.set(nom);
        this.editeur.set(editeur);
        this.price.set(price);
        this.genre.set(genre);
        this.plateforme.set(plateforme);
        this.stock.set(stock);
        this.occasion.set(occasion);
    }

    public String getNom() { return nom.get(); }
    public void setNom(String nom) { this.nom.set(nom); }
    public StringProperty nomProperty() { return nom; }

    public String getEditeur() { return editeur.get(); }
    public void setEditeur(String editeur) { this.editeur.set(editeur); }
    public StringProperty editeurProperty() { return editeur; }

    public double getPrice() { return price.getValue().doubleValue(); }
    public void setPrice(double price) { this.price.set(price); }
    public DoubleProperty priceProperty() { return price; }

    public Genre getGenre() { return genre.get(); }
    public void setGenre(Genre genre) { this.genre.set(genre); }
    public ObjectProperty<Genre> genreProperty() { return genre; }

    public Plateforme getPlateforme() { return plateforme.get(); }

    public int  getStock() { return stock.get(); }
    public void setStock(int stock) { this.stock.set(stock); }

    public boolean isOccasion() { return occasion.get(); }
    public void  setOccasion(boolean occasion) { this.occasion.set(occasion); }

    // Implémentation de l'interface Promotion
    @Override
    public double getReduction() { return reduction.get(); }

    @Override
    public void setReduction(double reduction) { this.reduction.set(reduction); }

    public DoubleProperty reductionProperty() { return reduction; }

}
