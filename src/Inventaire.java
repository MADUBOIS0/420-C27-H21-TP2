/*
Objectif: Classe Inventaire, sert a créer des objets et ajouter des entretiens
  Auteur: Marc-Antoine Dubois
  Date: 2021-05-17 Session H2021
 */
import java.io.Serializable;
import java.time.LocalDate;
import java.util.LinkedHashMap;

public class Inventaire implements Serializable {
    private String nom; // Le nom de l'objet
    private String numSerie; // Numéro de série
    private String categorie; // Catégorie de l'objet
    private double prix; // prix de l'objet
    private LocalDate dateAchat; // Date que l'objet a été acheter
    private String description; // Description de l'objet
    private LinkedHashMap<LocalDate,String> entretiens = new LinkedHashMap<>(); // Liste des entretiens fait à l'objet

    /**
     * Obtenir le nom de l'objet
     * @return Nom objet en string
     */
    public String getNom(){
        return nom;
    }

    /**
     * Appliquer le nom reçu à l'objet
     * @param nom nom de l'objet
     */
    public void setNom(String nom){
        this.nom = nom;
    }

    /**
     * Obtenir numéro de serie
     * @return Numéro de serie
     */
    public String getNumSerie(){
        return this.numSerie;
    }

    /**
     * Appliquer numéro de serie
     * @param numSerie numéro de serie
     */
    public void setNumSerie(String numSerie){
        this.numSerie = numSerie;
    }

    /**
     * Obtenir la catégorie de l'objet
     * @return catégorie en string
     */
    public String getCategorie(){
        return this.categorie;
    }

    /**
     * Appliquer la catégorie
     * @param categorie nom de la catégorie
     */
    public void setCategorie(String categorie){
        this.categorie= categorie;
    }

    /**
     * Obtenir le prix
     * @return montant du prix en double
     */
    public double getPrix(){
        return this.prix;
    }

    /**
     * Appliquer le prix
     * @param prix montant du prix en double
     */
    public void setPrix(double prix){
        this.prix = prix;
    }

    /**
     * Obtenir la date d'achat
     * @return retourne un localDate de la date
     */
    public LocalDate getDateAchat(){
        return this.dateAchat;
    }

    /**
     * Appliquer la date d'achat
     * @param date LocalDate de la date
     */
    public void setDateAchat(LocalDate date){
        this.dateAchat = date;
    }

    /**
     * Obtenir la description
     * @return retourne la description
     */
    public String getDescription(){
        return this.description;
    }

    /**
     * Appliquer la description
     * @param description description en string
     */
    public void setDescription(String description){
        this.description = description;
    }

    /**
     * Obtenir un LinkedHashMap des entretiens
     * @return retourne la liste d'entretiens
     */
    public LinkedHashMap<LocalDate, String> getEntretien(){
        return this.entretiens;
    }

    /**
     * Ajoute un entretien à l'objet
     * @param dateEntretien La date de l'entretien en LocalDate
     * @param description La description de l'entretien en String
     */
    public void addEntretien(LocalDate dateEntretien, String description){
        this.entretiens.put(dateEntretien, description);
    }

    /**
     * Supprime l'entretien avec la date
     * @param dateEntretien date de l'entretien qui représente la key
     */
    public void removeEntretien(LocalDate dateEntretien){
        this.entretiens.remove(dateEntretien);
    }
}
