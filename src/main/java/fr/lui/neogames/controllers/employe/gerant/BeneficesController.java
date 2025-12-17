package fr.lui.neogames.controllers.employe.gerant;

import fr.lui.neogames.MainApp;
import fr.lui.neogames.controllers.employe.EmployeController;
import fr.lui.neogames.models.Client;
import fr.lui.neogames.models.Magasin;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class BeneficesController {

    @FXML
    private Label lblChiffreAffaires;
    @FXML
    private Label lblNombreVentes;
    @FXML
    private Label lblVenteMoyenne;
    @FXML
    private Label lblJeuxStock;
    @FXML
    private Label lblCodesPromo;

    private EmployeController employeController;

    @FXML
    public void initialize() {
        rafraichir();
    }

    public void setEmployeController(EmployeController employeController) {
        this.employeController = employeController;
    }

    @FXML
    private void rafraichir() {
        Magasin magasin = MainApp.getMagasin();

        // Chiffre d'affaires
        double chiffreAffaires = magasin.getChiffreAffaires();
        lblChiffreAffaires.setText(String.format("%.2f €", chiffreAffaires));

        // Nombre de jeux en stock
        int nombreJeux = magasin.getCatalogue().size();
        lblJeuxStock.setText(String.valueOf(nombreJeux));

        // Codes promo actifs
        long codesActifs = magasin.getCodesPromo().stream()
                .filter(cp -> cp.estValide())
                .count();
        lblCodesPromo.setText(String.valueOf(codesActifs));

        // Calculer nombre de ventes (basé sur les commandes des clients de test)
        // Pour l'instant on met des valeurs par défaut
        int nombreVentes = 0;
        double totalVentes = chiffreAffaires;

        lblNombreVentes.setText(String.valueOf(nombreVentes));

        if (nombreVentes > 0) {
            double venteMoyenne = totalVentes / nombreVentes;
            lblVenteMoyenne.setText(String.format("%.2f €", venteMoyenne));
        } else {
            lblVenteMoyenne.setText("0.00 €");
        }
    }
}
