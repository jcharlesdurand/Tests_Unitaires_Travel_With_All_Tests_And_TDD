package fr.esgi.fx.travel.dao;

public class Requetes {

    public static final String AJOUT_AEROPORT = "INSERT INTO Aeroport(nom) VALUES(?)";
    public static final String AEROPORT_PAR_ID = "SELECT id, nom FROM Aeroport WHERE id=?";
    public static final String TOUS_LES_AEROPORTS = "SELECT id, nom FROM Aeroport";

    public static final String AJOUT_COMPAGNIE = "INSERT INTO Compagnie(nom) VALUES(?)";
    public static final String COMPAGNIE_PAR_ID = "SELECT id, nom FROM Compagnie WHERE id=?";
    public static final String TOUTES_LES_COMPAGNIES = "SELECT id, nom FROM Compagnie";
    public static final String SUPPRESSION_COMPAGNIE = "DELETE FROM Compagnie where id=?";

    public static final String AJOUT_VOL = "INSERT INTO Vol(heure_depart, heure_arrivee, prix_en_euros, compagnie_id, aeroport_depart_id, aeroport_arrivee_id ) VALUES (?,?,?,?,?,?)";
    public static final String VOL_PAR_ID = "SELECT heure_depart, heure_arrivee, prix_en_euros, compagnie_id, aeroport_depart_id, aeroport_arrivee_id FROM Vol WHERE id=?";
    public static final String TOUS_LES_VOLS = "SELECT id, heure_depart, heure_arrivee, prix_en_euros, compagnie_id, aeroport_depart_id, aeroport_arrivee_id FROM Vol ORDER BY prix_en_euros";
    public static final String SUPPRESSION_VOL = "DELETE FROM Vol where id=?";
    
}