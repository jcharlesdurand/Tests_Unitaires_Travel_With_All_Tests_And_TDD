package fr.esgi.fx.travel;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.esgi.fx.travel.business.Aeroport;
import fr.esgi.fx.travel.business.Compagnie;
import fr.esgi.fx.travel.business.Vol;
import fr.esgi.fx.travel.dao.ConnexionBdd;
import fr.esgi.fx.travel.dao.impl.AeroportDaoImpl;
import fr.esgi.fx.travel.dao.impl.CompagnieDaoImpl;
import fr.esgi.fx.travel.dao.impl.VolDaoImpl;
import fr.esgi.fx.travel.service.AeroportService;
import fr.esgi.fx.travel.service.CompagnieService;
import fr.esgi.fx.travel.service.VolService;
import fr.esgi.fx.travel.service.impl.AeroportServiceImpl;
import fr.esgi.fx.travel.service.impl.CompagnieServiceImpl;
import fr.esgi.fx.travel.service.impl.VolServiceImpl;

public class App {
	
	private static final int AJOUTER_COMPAGNIE = 1;
	private static final int AFFICHER_COMPAGNIES = 2;
	private static final int AJOUTER_VOL = 3;
	private static final int AFFICHER_VOLS = 4;
	private static final int QUITTER = 5;
	
	private static AeroportService aeroportService = null;
	private static CompagnieService compagnieService = null;
	private static VolService volService = null;

	private static Scanner scanner = new Scanner(System.in);

	private static final String FORMAT_HEURE = "HH:mm";
	private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(FORMAT_HEURE);
				
	public static void main(String[] args) {
		aeroportService = new AeroportServiceImpl(new AeroportDaoImpl());
		compagnieService = new CompagnieServiceImpl(new CompagnieDaoImpl());
		volService = new VolServiceImpl(new VolDaoImpl(), aeroportService, compagnieService);

		ajouterAeroports();

		while (true) {
			afficherMenuPrincipal();
			int choix = demanderChoix("Entrez votre choix : ", 1, QUITTER);
			switch (choix) {
			case AJOUTER_COMPAGNIE:
				ajouterCompagnie();
				break;
			case AFFICHER_COMPAGNIES:
				afficherCompagnies();
				break;
			case AJOUTER_VOL:
				ajouterVol();
				break;
			case AFFICHER_VOLS:
				afficherVols();
				break;
			case QUITTER:
				System.out.println("Au revoir");
				ConnexionBdd.close();
				System.exit(0);
				break;
			default:
				break;
			}	
		}		
	}


	private static void afficherVols() {
		volService.recupererVols().forEach(System.out::println);
	}

	private static void afficherCompagnies() {
		System.out.println("Voici la liste exhaustive des compagnies : ");
		for (Compagnie compagnie : compagnieService.recupererCompagnies()) {
			//System.out.println(compagnie.getId() + " : " + compagnie.getNom());
			ObjectMapper objectMapper = new ObjectMapper();
			// On demande à Jackson de sérialiser l'objet vol en JSON)
			try {
				System.out.println(objectMapper.writeValueAsString(compagnie));
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
	}

	private static void afficherAeroports() {
		System.out.println("Voici la liste exhaustive des aéroports : ");
		for (Aeroport aeroport : aeroportService.recupererAeroports()) {
			System.out.println(aeroport.getId() + " : " + aeroport.getNom());
		}		
	}

	private static void ajouterCompagnie() {
		System.out.println("Entrez le nom de la nouvelle compagnie");
		compagnieService.ajouterCompagnie(scanner.nextLine());
	}

	private static void ajouterVol() {
		
		if (compagnieService.recupererCompagnies().isEmpty()) {
			System.out.println("Pour ajouter un vol, vous devez d'abord ajouter une compagnie");
			return;
		}
		
		afficherAeroports();
		Long idAeroportDepartChoisi = (long) demanderChoix("Entrez l'id de l'aeroport de départ : ", 1, aeroportService.recupererAeroports().size());
		Long idAeroportArriveeChoisi = (long) demanderChoix("Entrez l'id de l'aeroport d'arrivée : ", 1, aeroportService.recupererAeroports().size());
				
		afficherCompagnies();
		Long idCompagnieChoisi = (long) demanderChoix("Entrez l'id de la compagnie : ", 1, compagnieService.recupererCompagnies().size());
		
		LocalTime dateHeureDepart = demanderHeure("Entrez l'heure de départ au format " + FORMAT_HEURE + " : ");
		LocalTime dateHeureArrivee = demanderHeure("Entrez l'heure d'arrivée au format " + FORMAT_HEURE + " : ");

		System.out.print("Entrez le prix en euros : ");
		float prixEnEuros = Float.parseFloat(scanner.nextLine());
		
		Vol vol = volService.ajouterVol(idCompagnieChoisi, idAeroportDepartChoisi, idAeroportArriveeChoisi, dateHeureDepart, dateHeureArrivee, prixEnEuros);
		
		System.out.println("Le vol a bien été ajouté, il porte l'id " + vol.getId() + ". Aeroport de départ :" 
		+ aeroportService.recupererAeroport(idAeroportDepartChoisi)
		+ ", Aeroport d'arrivée : " + aeroportService.recupererAeroport(idAeroportArriveeChoisi) 
		+ ". compagnie : " + compagnieService.recupererCompagnie(idCompagnieChoisi) + ". Prix du vol : " + prixEnEuros
		+ ". Heure de départ : " + vol.getHeureDepart()
		+ ". Heure d'arrivée : " + vol.getHeureArrivee()
		);
	}

	private static void afficherMenuPrincipal() {
		System.out.println("Bienvenue sur ESGI Travel !");
		System.out.println("MENU PRINCIPAL");
		System.out.println(AJOUTER_COMPAGNIE + " : ajouter une compagnie");
		System.out.println(AFFICHER_COMPAGNIES + " : voir toutes les compagnies");
		System.out.println(AJOUTER_VOL + " : ajouter un vol");
		System.out.println(AFFICHER_VOLS + " : voir les vols triés sur le prix (du moins cher au plus cher)");
		System.out.println(QUITTER + " : quitter");	
	}


	private static void ajouterAeroports() {
		// On ajoute des aeroports si le service n'en contient pas
		if (aeroportService.recupererAeroports().isEmpty()) {
			aeroportService.ajouterAeroport("Clermont-Ferrand");
			aeroportService.ajouterAeroport("Lyon");
			aeroportService.ajouterAeroport("Grenoble");
		}
		
	}
	
    private static int demanderChoix(String message, int borneMin, int borneMax) {
        int valeur = borneMin-1;
        // Utilisation d'une boucle do while
        // Le code dans le do sera exécuté au moins une fois
        do {
                System.out.print(message);
                try {
                        String saisie = scanner.nextLine();
                        valeur = Integer.parseInt(saisie);
                        if (valeur<borneMin || valeur>borneMax)
                        {
                                System.out.println("Merci de saisir un nombre compris entre " + borneMin + " et " + borneMax );
                        }
                }
                catch (Exception e)
                {
                        System.out.println("Merci de saisir un nombre");
                }

        }
        while (!(valeur>=borneMin && valeur<=borneMax));
        return valeur;
    }

    private static LocalTime demanderHeure(String message) {
		LocalTime localTime = null;
		
		do {
			System.out.print(message);
			try {
				localTime = LocalTime.parse(scanner.nextLine(), dateTimeFormatter);
			} catch (Exception e) {
				System.out.println("Format incorrect");
			}				
		} while (localTime == null);
		
		return localTime;
	}
    
}