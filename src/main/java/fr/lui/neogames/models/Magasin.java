package fr.lui.neogames.models;

import fr.lui.neogames.models.employe.Employe;
import fr.lui.neogames.models.jeu.Jeu;

import java.util.ArrayList;
import java.util.List;

public class Magasin {
    private String nom;
    private String adresse;
    private List<Employe> employes;
    private List<Client> clients;
    private List<Jeu> catalogue;
    private List<Panier> paniers;
    private double chiffreAffaires;
    private List<CodePromo> codesPromo;

    public Magasin(String nom, String adresse) {
        this.nom = nom;
        this.adresse = adresse;
        this.employes = new ArrayList<>();
        this.clients = new ArrayList<>();
        this.catalogue = new ArrayList<>();
        this.paniers = new ArrayList<>();
        this.chiffreAffaires = 0.0;
        this.codesPromo = new ArrayList<>();
        initialiserCodesPromo();
    }

    private void initialiserCodesPromo() {
        // Codes promo par défaut
        codesPromo.add(new CodePromo("BIENVENUE", 10));
        codesPromo.add(new CodePromo("PROMO20", 20));
        codesPromo.add(new CodePromo("NOEL2024", 25, java.time.LocalDate.of(2024, 12, 31)));
        codesPromo.add(new CodePromo("VIP50", 50, 5)); // Code VIP avec 5 utilisations max
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public double getChiffreAffaires() {
        return chiffreAffaires;
    }

    public void setChiffreAffaires(double chiffreAffaires) {
        this.chiffreAffaires = chiffreAffaires;
    }

    public void ajouterJeu(Jeu jeu) {
        this.catalogue.add(jeu);
    }

    public List<Jeu> getCatalogue() {
        return catalogue;
    }

    // Gestion des codes promo
    public CodePromo trouverCodePromo(String code) {
        return codesPromo.stream()
                .filter(cp -> cp.getCode().equalsIgnoreCase(code))
                .findFirst()
                .orElse(null);
    }

    public void ajouterCodePromo(CodePromo codePromo) {
        codesPromo.add(codePromo);
    }

    public List<CodePromo> getCodesPromo() {
        return codesPromo;
    }
}
