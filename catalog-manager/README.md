# Catalog Manager

Catalog Manager est une application Rest API Spring Boot qui permet de gérer un catalogue de produits avec un système d'authentification sécurisé.

## Technologies utilisées

- Java 8
- Spring Boot 2.7.18
- Spring Security
- Spring Data JPA
- MariaDB / H2 Database
- Maven
- Lombok
- MapStruct
- Swagger/OpenAPI

## Prérequis

- JDK 1.8
- Maven 3.x
- MariaDB (pour l'environnement de production)

## Conseption UML

### Diagramme de classe
![Diagramme de classe](https://raw.githubusercontent.com/JavaAura/Mokhlis_Belhaj_S4_B1_catalog_manager/refs/heads/main/UML/class_diagram.png)

### Diagramme de cas d'utilisation
![Diagramme de cas d'utilisation](https://raw.githubusercontent.com/JavaAura/Mokhlis_Belhaj_S4_B1_catalog_manager/refs/heads/main/UML/use-case-diagram.png)

## Configuration

L'application utilise différents profils de configuration :

- `dev` : Utilise une base de données H2 en mémoire
- `prod` : Utilise une base de données MariaDB

### Configuration de la base de données

pour le profil `prod` :

- installer MariaDB
- créer la base de données `catalogdb`
- dans le fichier `application-prod.properties`
  ```properties
  spring.datasource.username=your_username
  spring.datasource.password=your_password
  ```
  

## Installation et démarrage

1. Cloner le repository
```bash
git clone https://github.com/JavaAura/Mokhlis_Belhaj_S4_B1_catalog_manager
```	

2. compiler le projet
avant de compiler le projet, il faut changer le profil de configuration dans le fichier `application.properties`
```properties
spring.profiles.active=dev ou prod
```
```bash
mvn clean install
```
3. lancer le projet
- accéder au dossier `target`
```bash
cd /Mokhlis_Belhaj_S4_B1_catalog_manager/catalog-manager/target
```
- lancer le projet
```bash
java -jar catalog-manager-0.0.1-SNAPSHOT.jar 
```


L'application sera accessible à l'adresse : `http://localhost:8081`

## Documentation API

La documentation Swagger/OpenAPI est disponible à l'adresse :
`http://localhost:8081/swagger-ui.html`

## Fonctionnalités principales

- Gestion des utilisateurs (inscription, authentification)
- Gestion des rôles
- Gestion des catégories de produits
- Gestion des produits
- API REST sécurisée
- Support de deux environnements (dev/prod)

## Tests
pour lancer les tests :
```bash
mvn test
```
## Docker

Pour construire l'image docker :
```bash
docker build -t catalog-manager .
```

Pour lancer le conteneur docker :
```bash
docker run -p 8081:8081 catalog-manager
```


## Jira

- [Jira](https://belhajmokhlis.atlassian.net/jira/software/projects/CM/boards/237)





