package fr.lui.neogames;

import fr.lui.neogames.models.Magasin;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class MainApp extends Application {
    private static Magasin magasin;
    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;

        magasin = new Magasin("NeoGames", "Rue de la licorne");

        DonneesTest.Initialiser(magasin);

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("assets/fxml/hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 500);

        primaryStage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResource("assets/img/logo.png")).openStream()));
        primaryStage.setTitle("Micromania - Gestion de Boutique");
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(500);
        primaryStage.setMinHeight(300);
        primaryStage.show();
    }

    /**
     * Change la vue actuelle
     */
    public static void changerVue(String fxmlPath) throws IOException {
        FXMLLoader loader = new FXMLLoader(MainApp.class.getResource(fxmlPath));
        Parent root = loader.load();

        Scene scene = new Scene(root, 800, 500);

        primaryStage.setScene(scene);
    }

    // Getters statiques
    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static Magasin getMagasin() {
        return magasin;
    }

}
