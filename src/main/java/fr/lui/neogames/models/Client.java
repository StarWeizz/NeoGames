package fr.lui.neogames.models;

import javafx.beans.value.ObservableValue;

public class Client {
    private String nom;
    private String prenom;
    private Panier panier;
    private Commande commande;
    private double solde;
    private boolean carteFidelite;
    private double pointsFidelite;

    public Client(String nom, String prenom) {
        this.nom = nom;
        this.prenom = prenom;
        this.panier = new Panier(this);
        this.commande = new Commande();
        this.solde = 0;
        this.carteFidelite = false;
        this.pointsFidelite = 0;
    }

    public Client(String nom, String prenom, double solde) {
        this.nom = nom;
        this.prenom = prenom;
        this.panier = new Panier(this);
        this.commande = new Commande();
        this.solde = solde;
        this.carteFidelite = false;
        this.pointsFidelite = 0;
    }

    public double getSolde() {
        return solde;
    }

    public void setSolde(double solde) {
        this.solde = solde;
    }


    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public boolean isCarteFidelite() {
        return carteFidelite;
    }

    public void setCarteFidelite(boolean carteFidelite) {
        this.carteFidelite = carteFidelite;
    }

    public double getPointsFidelite() {
        return pointsFidelite;
    }

    public void setPointsFidelite(double pointsFidelite) {
        this.pointsFidelite = pointsFidelite;
    }

    public String getFullName() {
        return nom + ", " + prenom;
    }

    public Panier getPanier() {
        return panier;
    }

    public Commande getCommande() {
        return commande;
    }

}
