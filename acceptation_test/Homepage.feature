Feature: Fonctionnalités de ma page d'accueil
	Scenario: Vérification du titre et de la description
		Given je suis sur la homepage
		Then le titre doit être "Partagez vos passions | Meetup"
		And la description contient "Partagez vos passions et faites bouger votre ville"


	Scenario: Vérification h1 et sous punchline
		Given je suis sur la homepage
		Then la punchline titre doit être "Le monde vous tend les bras"
		And la sous punchline doit être "Rejoignez un groupe local pour rencontrer du monde, tester une nouvelle activité ou partager vos passions."

	Scenario: Un bouton d'inscription rouge doit être présent en haut de la page, juste après la punchline
		Given je suis sur la homepage
		Then le bouton doit être "rgba(241, 58, 89, 1)"
		And le bouton doit contenir "Rejoindre Meetup"
		And le bouton mène au lien "https://www.meetup.com/fr-FR/register/"