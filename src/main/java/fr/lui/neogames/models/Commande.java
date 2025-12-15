package fr.lui.neogames.models;

import fr.lui.neogames.models.jeu.Jeu;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Commande {
    public static class DetailCommande {
        private List<Jeu> jeux;
        private LocalDateTime date;
        private String statut;
        private double montant;

        public DetailCommande(List<Jeu> jeux, double montant) {
            this.jeux = jeux;
            this.date = LocalDateTime.now();
            this.statut = "Livrée";
            this.montant = montant;
        }

        public List<Jeu> getJeux() {
            return jeux;
        }

        public LocalDateTime getDate() {
            return date;
        }

        public String getStatut() {
            return statut;
        }

        public double getMontant() {
            return montant;
        }

        public void setStatut(String statut) {
            this.statut = statut;
        }
    }

    private List<DetailCommande> commandes;

    public Commande() {
        this.commandes = new ArrayList<>();
    }

    public Commande(List<Jeu> commandes) {
        this.commandes = new ArrayList<>();
        double total = commandes.stream().mapToDouble(Jeu::getPrice).sum();
        this.commandes.add(new DetailCommande(commandes, total));
    }

    public List<DetailCommande> getCommandes() {
        return commandes;
    }

    public void addCommande(List<Jeu> jeux) {
        double total = jeux.stream().mapToDouble(Jeu::getPrice).sum();
        this.commandes.add(new DetailCommande(jeux, total));
    }

}
