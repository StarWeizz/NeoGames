package fr.lui.neogames.models;

import java.time.LocalDate;

public class CodePromo {
    private String code;
    private double reduction; // Pourcentage de réduction
    private LocalDate dateExpiration;
    private boolean actif;
    private int utilisationsMax;
    private int utilisationsRestantes;

    public CodePromo(String code, double reduction) {
        this.code = code;
        this.reduction = reduction;
        this.actif = true;
        this.utilisationsMax = -1; // -1 = illimité
        this.utilisationsRestantes = -1;
    }

    public CodePromo(String code, double reduction, LocalDate dateExpiration) {
        this(code, reduction);
        this.dateExpiration = dateExpiration;
    }

    public CodePromo(String code, double reduction, int utilisationsMax) {
        this(code, reduction);
        this.utilisationsMax = utilisationsMax;
        this.utilisationsRestantes = utilisationsMax;
    }

    public boolean estValide() {
        // Vérifier si actif
        if (!actif) return false;

        // Vérifier la date d'expiration
        if (dateExpiration != null && LocalDate.now().isAfter(dateExpiration)) {
            return false;
        }

        // Vérifier les utilisations restantes
        if (utilisationsMax != -1 && utilisationsRestantes <= 0) {
            return false;
        }

        return true;
    }

    public void utiliser() {
        if (utilisationsRestantes > 0) {
            utilisationsRestantes--;
        }
    }

    // Getters et Setters
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public double getReduction() {
        return reduction;
    }

    public void setReduction(double reduction) {
        this.reduction = reduction;
    }

    public LocalDate getDateExpiration() {
        return dateExpiration;
    }

    public void setDateExpiration(LocalDate dateExpiration) {
        this.dateExpiration = dateExpiration;
    }

    public boolean isActif() {
        return actif;
    }

    public void setActif(boolean actif) {
        this.actif = actif;
    }

    public int getUtilisationsMax() {
        return utilisationsMax;
    }

    public int getUtilisationsRestantes() {
        return utilisationsRestantes;
    }

    @Override
    public String toString() {
        return code + " (-" + reduction + "%)";
    }
}
