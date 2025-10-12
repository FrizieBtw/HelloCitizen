# HelloCitizen

HelloCitizen est une application développée en collaboration avec les mairies pour accueillir les nouveaux habitants.
Elle permet de :

- Identifier les nouveaux arrivants dans la commune.
- Suivre leur première année d’installation.
- Proposer automatiquement un cadeau symbolique à la fin de leur première année de résidence.
  L’objectif est de renforcer le lien entre la mairie et ses citoyens, de valoriser l’accueil des nouveaux habitants et de favoriser leur intégration dans la vie locale.

---

Plus sérieusement, voici notre dossier de conception avec l'explication de nos choix techniques. Vous y trouverez également une section détaillant nos endpoints ainsi qu'un tutoriel pour tester notre application.

## Conception du projet

Vous trouverez ci-dessous les documents de conception détaillant l'architecture et les processus du projet. Cliquez sur un aperçu ou sur le titre pour ouvrir le PDF correspondant.

<h3>
  <a href="./Conception/Architecture_globale_du_SI.pdf">Architecture globale du SI</a>
</h3>
<a href="./Conception/Architecture_globale_du_SI.pdf" title="Cliquer pour ouvrir le PDF">
  <img src="./Conception/Images/Architecture_globale_du_SI.png" alt="Aperçu du PDF Architecture globale du SI" width="700">
</a>

<h3>
  <a href="./Conception/BPMN_generation_du_recapitulatif_quotidien.pdf">BPMN - Génération du récapitulatif quotidien</a>
</h3>
<a href="./Conception/BPMN_generation_du_recapitulatif_quotidien.pdf" title="Cliquer pour ouvrir le PDF">
  <img src="./Conception/Images/BPMN_generation_du_recapitulatif_quotidien.png" alt="Aperçu du PDF BPMN Génération du récapitulatif quotidien" width="700">
</a>

<h3>
  <a href="./Conception/BPMN_gestion_des_cadeaux.pdf">BPMN - Gestion des cadeaux</a>
</h3>
<a href="./Conception/BPMN_gestion_des_cadeaux.pdf" title="Cliquer pour ouvrir le PDF">
  <img src="./Conception/Images/BPMN_gestion_des_cadeaux.png" alt="Aperçu du PDF BPMN Gestion des cadeaux" width="700">
</a>

<h3>
  <a href="./Conception/BPMN_maj_mensuelle_des_habitants.pdf">BPMN - Mise à jour mensuelle des habitants</a>
</h3>
<a href="./Conception/BPMN_maj_mensuelle_des_habitants.pdf" title="Cliquer pour ouvrir le PDF">
  <img src="./Conception/Images/BPMN_maj_mensuelle_des_habitants.png" alt="Aperçu du PDF BPMN Mise à jour mensuelle des habitants" width="700">
</a>

<h3>
  <a href="./Conception/Cas_d'utilisation_Interface_de_l'habitant.pdf">Cas d'utilisation - Interface de l'habitant</a>
</h3>
<a href="./Conception/Cas_d'utilisation_Interface_de_l'habitant.pdf" title="Cliquer pour ouvrir le PDF">
  <img src="./Conception/Images/Cas_d'utilisation_Interface_de_l'habitant.png" alt="Aperçu du PDF Cas d'utilisation Interface de l'habitant" width="700">
</a>

<h3>
  <a href="./Conception/Cas_d'utilisation_Interface_du_gestionnaire.pdf">Cas d'utilisation - Interface du gestionnaire</a>
</h3>
<a href="./Conception/Cas_d'utilisation_Interface_du_gestionnaire.pdf" title="Cliquer pour ouvrir le PDF">
  <img src="./Conception/Images/Cas_d'utilisation_Interface_du_gestionnaire.png" alt="Aperçu du PDF Cas d'utilisation Interface du gestionnaire" width="700">
</a>

<h3>
  <a href="./Conception/Structure_de_la_base_de_donnees.pdf">Structure de la base de données</a>
</h3>
<a href="./Conception/Structure_de_la_base_de_donnees.pdf" title="Cliquer pour ouvrir le PDF">
  <img src="./Conception/Images/Structure_de_la_base_de_donnees.png" alt="Aperçu du PDF Structure de la base de données" width="700">
</a>

## Choix techniques

Pour le développement de l’application de gestion et d’attribution des cadeaux, nous avons pris les décisions techniques suivantes :

### Backend (Java / Spring Boot)

- **Spring Boot** pour créer rapidement une API REST.
- **JPA / Hibernate** pour la gestion des entités et la persistance en base de données. Issue de Jarkarta anciennement JEE.
- **H2** comme base de données relationnelle pour stocker les résidents, cadeaux et attributions.
- H2 nous a permis de faire une base de données embarqué dans l'api pour que vous puissiez utiliser notre application rapidement.
- Utilisé en entreprise par la majorité des personnes ayant travaillé sur le projet, ce qui a permis une prise en main rapide.

### Frontend (HTML / CSS / JS / Bootstrap)

- **Bootstrap 5** pour une interface responsive et moderne et sans prise de tête.
- **JavaScript vanilla** pour la logique de sélection de cadeau et la communication avec l’API.
- Affichage dynamique des cadeaux.
- Validation de formulaire côté client avant envoi à l’API.
- Pas de complication avec l'utilisation d'un framework front.

### Communication client-serveur

- Utilisation de **fetch API** pour récupérer les cadeaux du résident et envoyer les attributions.
- Format **JSON** pour l’échange de données entre le frontend et le backend.

## Endpoints API

L’application expose plusieurs endpoints REST pour gérer les résidents, les cadeaux et les attributions. Voici un résumé des principaux endpoints et leur utilisation.

---

### Gestion des cadeaux

| Endpoint                    | Méthode | Description                                           | Body / Paramètres                                                                                                                                          | Réponse                           |
| --------------------------- | ------- | ----------------------------------------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------- | --------------------------------- |
| `/api/gifts`                | POST    | Créer un cadeau                                       | `json { "libelle": "Puzzle 1000 pièces", "ageMin": 8, "ageMax": 12, "codeBarres": "123456", "price": 25.0, "image": [/* tableau d'octets en base64 */] } ` | JSON du cadeau créé avec son `id` |
| `/api/gifts/{id}`           | DELETE  | Supprimer un cadeau                                   | id (path)                                                                                                                                                  | JSON vide                         |
| `/api/gifts/{birthday}`     | GET     | Récupérer les cadeaux pour un anniversaire spécifique | birthday (path, format YYYY-MM-DD)                                                                                                                         | Liste des cadeaux compatibles     |
| `/api/residents/{id}/gifts` | GET     | Récupérer les cadeaux disponibles pour un résident    | id (path)                                                                                                                                                  | Liste des cadeaux compatibles     |

---

### Gestion des résidents

| Endpoint              | Méthode | Description           | Body / Paramètres                                                                                                                                                                                                          | Réponse                             |
| --------------------- | ------- | --------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----------------------------------- |
| `/api/residents`      | POST    | Créer un résident     | `json { "firstName": "Nuno", "lastName": "Moreira", "birthday": "2008-04-15", "email": "nuno@mail.com", "number": "0600000000", "address": "1 rue Jean", "arrivalDate": "2025-01-01", "notificationDate": "2025-12-01" } ` | JSON du résident créé avec son `id` |
| `/api/residents/{id}` | DELETE  | Supprimer un résident | id (path)                                                                                                                                                                                                                  | JSON vide                           |

---

### Gestion des attributions

| Endpoint                          | Méthode | Description                                           | Body / Paramètres                                                                                     | Réponse                           |
| --------------------------------- | ------- | ----------------------------------------------------- | ----------------------------------------------------------------------------------------------------- | --------------------------------- |
| `/api/attributions/resident/{id}` | POST    | Proposer un cadeau à un résident                      | id (path)                                                                                             | Message de confirmation           |
| `/api/attributions`               | PUT     | Mettre à jour une attribution (choix, email, adresse) | `json { "residentId": 123, "giftId": 5, "email": "nuno@mail.com", "deliveryAddress": "1 rue Jean" } ` | JSON de l’attribution mise à jour |
| `/api/attributions/{id}`          | GET     | Récupérer une attribution                             | id (path)                                                                                             | JSON complet de l’attribution     |

---

## Tutoriel

Vous trouverez à la racine de notre projet, à côté de ce fichier Markdown, un fichier `.exe` que vous pouvez exécuter. Celui-ci lancera automatiquement l’API et créera la base de données. Nous avons créé cet EXE avec un JRE intégré afin que vous n’ayez aucun problème lié à une version de Java différente.

Ensuite, vous pourrez aller dans le dossier `front` et exécuter les commandes suivantes pour lancer le serveur web et permettre les appels à l’API :

Attention Node.js doit être installé sur votre machine.
Voici un tutoriel d’installation que nous avons trouvé sur Internet, si besoin :

### Installer Node.js

1. Aller sur le site officiel : [https://nodejs.org](https://nodejs.org)
2. Télécharger la version **LTS (Long Term Support)** (exemple : 20.x LTS).
3. Lancer l’installeur téléchargé (`.msi`).
4. Suivre les étapes, en laissant les options par défaut.
   > ⚠️ Vérifier que l’option **Add to PATH** est cochée.

### Avec npm

```bash
cd HelloCitizen_Front
npx serve .
```

L'application est ensuite disponible sur https://localhost:3000
