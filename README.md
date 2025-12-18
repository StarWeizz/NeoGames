# NeoGames - Système de Gestion de Boutique de Jeux Vidéo

Application de bureau développée en JavaFX pour la gestion complète d'une boutique de jeux vidéo.

## Description

NeoGames est une application de gestion de magasin de jeux vidéo qui permet de gérer les ventes, le stock, les clients et les promotions. L'application offre différentes interfaces selon le rôle de l'utilisateur (Client, Vendeur, Préparateur, Gérant).

## Diagramme UML

![Diagramme UML](DiagrammeUML.png)

## Fonctionnalités

### Espace Client
- Consultation du catalogue de jeux (PC, Console, Rétro)
- Visualisation des nouveautés et promotions
- Gestion du panier d'achat
- Passage de commandes
- Système de carte de fidélité avec points
- Application de codes promotionnels
- Suivi des commandes
- Gestion du profil client

### Espace Vendeur
- Gestion des ventes
- Consultation de la liste des clients
- Interface de vente simplifiée

### Espace Préparateur
- Ajout de jeux au catalogue
- Gestion du stock
- Mise à jour des informations produits

### Espace Gérant
- Création et gestion des codes promotionnels
- Suivi du chiffre d'affaires
- Consultation des bénéfices
- Gestion globale du magasin

## Structure du Projet

```
NeoGames/
├── src/main/java/fr/lui/neogames/
│   ├── models/              # Modèles de données
│   │   ├── employe/        # Employé, Vendeur, Préparateur, Gérant
│   │   ├── jeu/            # Jeu, JeuPC, JeuConsole, JeuRetro
│   │   ├── Client.java
│   │   ├── Magasin.java
│   │   ├── Panier.java
│   │   ├── Commande.java
│   │   └── CodePromo.java
│   ├── controllers/         # Contrôleurs JavaFX
│   │   ├── client/         # Contrôleurs client
│   │   └── employe/        # Contrôleurs employés
│   ├── enums/              # Énumérations
│   │   ├── Genre.java
│   │   └── Plateforme.java
│   ├── interfaces/         # Interfaces
│   │   └── Promotion.java
│   ├── MainApp.java        # Point d'entrée
│   └── DonneesTest.java    # Données de test
└── src/main/resources/
    └── fr/lui/neogames/assets/
        ├── fxml/           # Fichiers FXML
        └── img/            # Images et ressources
```

## Prérequis

- Java JDK 21 ou supérieur
- Maven 3.8 ou supérieur

## Installation

1. Cloner le dépôt:
```bash
git clone <url-du-repo>
cd NeoGames
```

2. Compiler le projet:
```bash
mvn clean compile
```

## Lancement de l'Application

### Avec Maven
```bash
mvn clean javafx:run
```

### Avec l'IDE
Exécuter la classe `fr.lui.neogames.MainApp`

## Utilisation

1. Au lancement, l'application affiche un écran de sélection de rôle
2. Choisir le type d'utilisateur (Client, Vendeur, Préparateur ou Gérant)
3. Accéder aux fonctionnalités spécifiques au rôle sélectionné

### Codes Promotionnels Pré-configurés
- `BIENVENUE` : 10% de réduction
- `PROMO20` : 20% de réduction
- `NOEL2024` : 25% de réduction (valable jusqu'au 31/12/2024)
- `VIP50` : 50% de réduction (limité à 5 utilisations)

## Modèle de Données

### Types de Jeux
- **JeuPC** : Jeux pour ordinateur
- **JeuConsole** : Jeux pour consoles (PlayStation, Xbox, Nintendo Switch)
- **JeuRetro** : Jeux rétro pour anciennes plateformes

### Système de Fidélité
Les clients peuvent obtenir une carte de fidélité et accumuler des points à chaque achat.

### Codes Promotionnels
Plusieurs types de promotions:
- Réduction en pourcentage
- Date d'expiration optionnelle
- Nombre d'utilisations limité optionnel

## Auteur

Projet développé dans le cadre d'un cours de Programmation Orientée Objet (POO) à Ynov.

## License

Ce projet est développé à des fins éducatives.