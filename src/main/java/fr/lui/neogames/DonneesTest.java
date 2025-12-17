package fr.lui.neogames;

import fr.lui.neogames.enums.Genre;
import fr.lui.neogames.enums.Plateforme;
import fr.lui.neogames.models.Client;
import fr.lui.neogames.models.Magasin;
import fr.lui.neogames.models.jeu.JeuConsole;
import fr.lui.neogames.models.jeu.JeuPC;
import fr.lui.neogames.models.jeu.JeuRetro;

public class DonneesTest {

    public static void Initialiser(Magasin magasin) {
        // Ajouter des jeux PC
        magasin.ajouterJeu(new JeuPC("Cyberpunk 2077", "CD Projekt Red", 59.99, Genre.RPG, 15, false,
                "Intel Core i5, 8GB RAM", "Intel Core i7, 16GB RAM", "Steam"));

        magasin.ajouterJeu(new JeuPC("The Witcher 3", "CD Projekt Red", 39.99, Genre.RPG, 20, false,
                "Intel Core i5, 6GB RAM", "Intel Core i7, 8GB RAM", "GOG"));

        magasin.ajouterJeu(new JeuPC("Counter-Strike 2", "Valve", 10.0, Genre.FPS, 50, false,
                "Intel Core i3, 4GB RAM", "Intel Core i5, 8GB RAM", "Steam"));

        // Ajouter des jeux console
        magasin.ajouterJeu(new JeuConsole("The Last of Us Part II", "Naughty Dog", 69.99, Genre.ACTION,
                Plateforme.PLAYSTATION_5, 10, false, true, true, true, 1));

        magasin.ajouterJeu(new JeuConsole("Mario Kart 8 Deluxe", "Nintendo", 59.99, Genre.COURSE,
                Plateforme.NINTENDO_SWITCH, 25, false, true, true, false, 4));

        magasin.ajouterJeu(new JeuConsole("Halo Infinite", "343 Industries", 69.99, Genre.FPS,
                Plateforme.XBOX_SERIES, 12, false, true, true, true, 16));

        magasin.ajouterJeu(new JeuConsole("God of War Ragnarök", "Santa Monica Studio", 79.99, Genre.ACTION,
                Plateforme.PLAYSTATION_5, 8, false, false, false, false, 1));

        magasin.ajouterJeu(new JeuConsole("Zelda: Tears of the Kingdom", "Nintendo", 69.99, Genre.AVENTURE,
                Plateforme.NINTENDO_SWITCH, 18, false, false, false, false, 1));

        // Ajouter des jeux rétro
        magasin.ajouterJeu(new JeuRetro("Super Mario Bros", "Nintendo", 89.99, Genre.PLATEFORME,
                Plateforme.RETRO_NES, 3, 1985, true, true, "Très bon", true));

        magasin.ajouterJeu(new JeuRetro("Sonic the Hedgehog", "Sega", 59.99, Genre.PLATEFORME,
                Plateforme.RETRO_MEGADRIVE, 5, 1991, true, false, "Bon", false));

        magasin.ajouterJeu(new JeuRetro("The Legend of Zelda", "Nintendo", 129.99, Genre.AVENTURE,
                Plateforme.RETRO_NES, 2, 1986, true, true, "Neuf", true));

        magasin.ajouterJeu(new JeuRetro("Street Fighter II", "Capcom", 79.99, Genre.COMBAT,
                Plateforme.RETRO_SNES, 4, 1991, true, true, "Très bon", false));

        // Ajouter des jeux d'occasion
        magasin.ajouterJeu(new JeuConsole("FIFA 23", "EA Sports", 29.99, Genre.SPORT,
                Plateforme.PLAYSTATION_5, 8, true, true, true, false, 4));

        magasin.ajouterJeu(new JeuConsole("Call of Duty: MW II", "Activision", 39.99, Genre.FPS,
                Plateforme.XBOX_SERIES, 6, true, true, true, true, 16));

        magasin.ajouterJeu(new JeuPC("Elden Ring", "FromSoftware", 44.99, Genre.RPG, 10, true,
                "Intel Core i5, 12GB RAM", "Intel Core i7, 16GB RAM", "Steam"));

        // Jeux en promotion
        JeuPC jeuPromo1 = new JeuPC("Red Dead Redemption 2", "Rockstar Games", 59.99, Genre.AVENTURE, 15, false,
                "Intel Core i5, 8GB RAM", "Intel Core i7, 12GB RAM", "Steam");
        jeuPromo1.setReduction(30); // -30%
        magasin.ajouterJeu(jeuPromo1);

        JeuConsole jeuPromo2 = new JeuConsole("Spider-Man Miles Morales", "Insomniac Games", 49.99, Genre.ACTION,
                Plateforme.PLAYSTATION_5, 12, false, true, true, false, 1);
        jeuPromo2.setReduction(25); // -25%
        magasin.ajouterJeu(jeuPromo2);

        JeuPC jeuPromo3 = new JeuPC("Starfield", "Bethesda", 69.99, Genre.RPG, 20, false,
                "Intel Core i7, 16GB RAM", "Intel Core i9, 32GB RAM", "Steam");
        jeuPromo3.setReduction(15); // -15%
        magasin.ajouterJeu(jeuPromo3);

        JeuConsole jeuPromo4 = new JeuConsole("Resident Evil 4 Remake", "Capcom", 59.99, Genre.HORREUR,
                Plateforme.PLAYSTATION_5, 10, false, true, true, true, 1);
        jeuPromo4.setReduction(40); // -40%
        magasin.ajouterJeu(jeuPromo4);

        JeuConsole jeuPromo5 = new JeuConsole("Forza Horizon 5", "Playground Games", 59.99, Genre.COURSE,
                Plateforme.XBOX_SERIES, 18, false, true, true, true, 4);
        jeuPromo5.setReduction(20); // -20%
        magasin.ajouterJeu(jeuPromo5);

        JeuPC jeuPromo6 = new JeuPC("Baldur's Gate 3", "Larian Studios", 59.99, Genre.RPG, 25, false,
                "Intel Core i5, 8GB RAM", "Intel Core i7, 16GB RAM", "Steam");
        jeuPromo6.setReduction(10); // -10%
        magasin.ajouterJeu(jeuPromo6);

        // Ajouter des clients de test
        Client client1 = new Client("Dupont", "Jean", 150.00);
        client1.setCarteFidelite(true);
        client1.setPointsFidelite(25.50);
        magasin.ajouterClient(client1);

        Client client2 = new Client("Martin", "Sophie", 320.75);
        client2.setCarteFidelite(true);
        client2.setPointsFidelite(45.20);
        magasin.ajouterClient(client2);

        Client client3 = new Client("Durand", "Pierre", 85.00);
        client3.setCarteFidelite(false);
        magasin.ajouterClient(client3);

        Client client4 = new Client("Leroy", "Marie", 500.00);
        client4.setCarteFidelite(true);
        client4.setPointsFidelite(78.90);
        magasin.ajouterClient(client4);

        Client client5 = new Client("Bernard", "Luc", 200.50);
        client5.setCarteFidelite(false);
        magasin.ajouterClient(client5);

        Client client6 = new Client("Petit", "Emma", 450.25);
        client6.setCarteFidelite(true);
        client6.setPointsFidelite(120.00);
        magasin.ajouterClient(client6);

        Client client7 = new Client("Moreau", "Thomas", 75.00);
        client7.setCarteFidelite(false);
        magasin.ajouterClient(client7);

        Client client8 = new Client("Simon", "Camille", 650.00);
        client8.setCarteFidelite(true);
        client8.setPointsFidelite(95.50);
        magasin.ajouterClient(client8);

        Client client9 = new Client("Laurent", "Nicolas", 120.00);
        client9.setCarteFidelite(true);
        client9.setPointsFidelite(15.75);
        magasin.ajouterClient(client9);

        Client client10 = new Client("Lefebvre", "Julie", 280.00);
        client10.setCarteFidelite(false);
        magasin.ajouterClient(client10);

    }
}
