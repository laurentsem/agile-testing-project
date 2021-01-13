Feature: Fonctionnalités de ma page recherche

  Scenario: Vérification du titre
    Given je suis sur recherche
    Then le titre doit contenir  "Nature et aventure"
    And la balise titre dois contenir "Nature et aventure"

  Scenario: Verification bandau de recherche
    Given je suis sur recherche
    Then La page de recherche contient un bandeau de recherche
    And contient le champ de recherche
    And contient le rayon de recherche
    And contient la ville de recherche
    And contient un choix d'affichage de la liste entre Groupe et Calendrier

  Scenario Outline: Verification des tris
    Given je suis sur recherche
    Then le tri par default est le tri par "pertinence"
    And il y a quatre possibilités de "<tri>"
    Examples:
    |tri              |
    |pertinence       |
    |plus récents     |
    |nombre de membres|
    |proximité        |
