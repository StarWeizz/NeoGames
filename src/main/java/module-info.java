module fr.lui.neogames {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires javafx.base;

    opens fr.lui.neogames to javafx.fxml;
    opens fr.lui.neogames.controllers to javafx.fxml;
    opens fr.lui.neogames.controllers.client to javafx.fxml;
    opens fr.lui.neogames.controllers.employe to javafx.fxml;
    opens fr.lui.neogames.models.jeu to javafx.base, javafx.fxml;

    exports fr.lui.neogames;
    exports fr.lui.neogames.controllers;
    exports fr.lui.neogames.controllers.client;
    exports fr.lui.neogames.controllers.employe;
}