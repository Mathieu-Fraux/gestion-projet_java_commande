# AgileTool - Gestion de Projet en Ligne de Commande

**AgileTool** est une application console (CLI) développée en Java permettant de piloter un projet informatique. Elle offre une solution légère et structurée pour gérer les spécifications (besoins), les obstacles (contraintes) et les comptes-rendus de réunion.

##  Fonctionnalités

### 1. Gestion des Besoins (Spécifications)
* **Ajouter** un besoin avec une description.
* **Lister** les besoins sous forme de tableau formaté.
* **Cycle de vie complet** via des états : `À ANALYSER`, `ANALYSÉ`, `EN COURS` (avec progression %), `TERMINÉ`, `ANNULÉ`.
* Saisie de détails lors des changements d'état (charge, dates, responsable).
* **Supprimer** un besoin.

### 2. Gestion des Contraintes (Obstacles)
* **Ajouter** une contrainte technique ou organisationnelle.
* **Suivi des états** : `À PRENDRE EN COMPTE`, `À VÉRIFIER`, `VÉRIFIÉE`, `ANNULÉE`.
* Gestion des vérificateurs et des dates de validation.

### 3. Rapports de Réunion
* **Création de rapports** avec Auteur, Date, et Participants.
* **Ajout dynamique d'actions** (ActionItems) durant la saisie : *Quoi, Qui, Quand*.
* **Sauvegarde hybride** : 
    * Un fichier lisible par l'humain (En-tête + Tableau).
    * Un format technique pour la relecture par le logiciel.
* **Consultation** des détails d'un rapport passé directement dans la console.

---

## Architecture Technique

Le projet suit une architecture modulaire pour séparer les responsabilités :

* **`src/model`** : Contient les objets métiers (*POJO*) et les énumérations (`Besoin`, `Contrainte`, `Rapport`, `ActionItem`, `EtatBesoin`...). Ce sont les briques de base.
* **`src/dao` (Data Access Object)** : Gère exclusivement la lecture et l'écriture dans les fichiers (`.csv` et `.txt`). Isole la logique de persistance.
* **`src/logique`** : Le "cerveau" de l'application. Il fait le lien entre l'interface et les données, gère les listes en mémoire et appelle le DAO pour sauvegarder à chaque modification.
* **`src/ui`** : L'interface utilisateur. Contient les menus, la gestion des saisies sécurisées (`ConsoleHelper`) et l'affichage des tableaux.
* **`src/main`** : Le point d'entrée de l'application.

---

## 💾 Structure des Données

L'application crée automatiquement un dossier `data/` à la racine du projet lors du premier lancement.

```text
Projet/
├── src/
├── data/
│   ├── besoins.csv          # Base de données des besoins
│   ├── contraintes.csv      # Base de données des contraintes
│   ├── rapports_index.csv   # Liste sommaire des rapports
│   └── rapport/             # Sous-dossier dédié au rapport
│       ├── rapport_2025-01-22_Pierre.txt
│       └── rapport_2025-02-10_Paul.txt