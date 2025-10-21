# Insurance API

Bonjour M. Grandjean et l'équipe API Factory,

Vous trouverez ci-dessous quelques informations sur l'API développée dans le cadre de l'exercice technique que vous m'avez
confié.

J’espère que cela fonctionnera parfaitement chez vous également.

Cordialement,

Damien COTE.

## 📋 Prérequis pour lancer la solution

- Avoir Docker et Docker Compose sur votre machine.
- Ne pas avoir de projet qui utilise les ports 80, 8080 ou 5432.

## 📋 Informations sur les mots de passe

Pour simplifier le test de l'API, je me suis permis de mettre les informations de connexion à la base en dur dans les différents fichiers.

Cela n'est évidemment pas une bonne pratique en entreprise ou dans tout projet professionnel 😅

## 📋 La jar insurance-API-0.0.1-SNAPSHOT.jar

Pour simplifier le test de l'API, la jar SpringBoot a été push sur le repo pour que vous puissiez lancer le projet plus simplement.

Si vous souhaitez tout de même générer la jar, vous pouvez le faire via les wrappers maven :

    (linux) ./mvnw clean package
    (windows) mvnw.cmd clean package

## 🚀 Démarrage rapide

### 1. Cloner le projet

```bash
  git clone https://github.com/damsrv/exercice-la-vaudoise.git
```

### 2. Lancer les conteneurs Docker

#### a. Se déplacer dans le dossier exercice-la-vaudoise

```bash
  cd exercice-la-vaudoise
```
#### b. Build et run les conteneurs

```bash
  docker-compose up --build -d
```

### 3. Accéder à l'API

- **Page d'acceuil de l'API** : http://localhost
- **Documentation Swagger** : http://localhost/swagger-ui.html
- **OpenAPI JSON** : http://localhost/api-docs

## 📊 Base de données

### Connexion PostgreSQL

```bash
# Se connecter à PostgreSQL
docker exec -it insurance-db psql -U insurance_user -d insurance_db
```

## 🏗️ Why the API works ?

L'API a été testée manuellement via l'interface swagger afin de vérifier que tous les endpoints fonctionnent.

## 🏗️ Architecture & Design Decisions

Le projet utilise une architecture conteneurisée avec Docker Compose :

- **PostgreSQL** : Base de données relationnelle
- **Spring Boot** : API REST backend
- **Nginx** : Reverse proxy pour éviter l'exposition directe des ports


### Architecture globale
**Layered Architecture** (architecture en couches) avec séparation des responsabilités.

### Structure du projet

- **Controller** : Couche présentation (REST API). Gère uniquement les requêtes HTTP.
- **DTO** : Objets de transfert de données. Isolent l'API des entités JPA (évite d'exposer la structure interne).
- **Mapper** : Conversion DTO ↔ Entity. Sépare la logique de transformation de la logique métier.
- **Service** : Couche métier. Contient la logique business, orchestration, et transactions.
- **Repository** : Couche d'accès aux données (Spring Data JPA). Abstraction de la persistance.
- **Entity** : Modèle de domaine (JPA). Représente la structure de la base de données.

### Avantages

- **Séparation des préoccupations** : Chaque couche a une responsabilité unique (SRP - SOLID)
- **Testabilité** : Chaque couche peut être testée indépendamment
- **Maintenabilité** : Changements isolés (ex: changer la BDD n'impacte pas les controllers)
- **Évolutivité** : Facile d'ajouter de nouvelles fonctionnalités

### Choix techniques spécifiques

- **Stratégie d'héritage SINGLE_TABLE** : Person/Company dans une seule et même table.
- **Index database** : `client_id`, `update_date`, `end_date` pour optimiser les requêtes actives.
- **Endpoint optimisé `/sum`** : La logique de somme est faite côté base pour éviter de prendre des ressources côté backend.
- **Validation DTO** : Jakarta Validation pour les règles métier indépendantes de la persistance
- **Polymorphisme JSON** : `@JsonTypeInfo` pour gérer Person/Company via champ `type`
- **Conteneurisation** : Nginx + Spring Boot + PostgreSQL pour déploiement simple et scalable

## 📊 URL pour tester l'API depuis POSTMAN

# 📂 Clients

| Action | Méthode | URL |
|--------|--------|-----|
| Créer un client (Person) | POST | http://localhost/api/clients |
| Créer un client (Company) | POST | http://localhost/api/clients |
| Récupérer un client | GET | http://localhost/api/clients/{id} |
| Mettre à jour un client | PUT | http://localhost/api/clients/{id} |
| Supprimer un client | DELETE | http://localhost/api/clients/{id} |

# 📂 Contrats

| Action | Méthode | URL |
|--------|--------|-----|
| Créer un contrat | POST | http://localhost/api/contracts |
| Mettre à jour le coût d’un contrat | PUT | http://localhost/api/contracts/{id}/cost |
| Récupérer les contrats actifs d’un client | GET | http://localhost/api/contracts/client/{clientId} |
| Récupérer les contrats actifs filtrés par date de mise à jour | GET | http://localhost/api/contracts/client/{clientId}?updateDate={yyyy-MM-dd} |
| Obtenir la somme des coûts des contrats actifs d’un client | GET | http://localhost/api/contracts/client/{clientId}/sum |
