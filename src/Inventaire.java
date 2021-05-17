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

    public String getNom(){
        return nom;
    }

    public void setNom(String nom){
        this.nom = nom;
    }

    public String getNumSerie(){
        return this.numSerie;
    }

    public void setNumSerie(String numSerie){
        this.numSerie = numSerie;
    }

    public String getCategorie(){
        return this.categorie;
    }

    public void setCategorie(String categorie){
        this.categorie= categorie;
    }

    public double getPrix(){
        return this.prix;
    }

    public void setPrix(double prix){
        this.prix = prix;
    }

    public LocalDate getDateAchat(){
        return this.dateAchat;
    }

    public void setDateAchat(LocalDate date){
        this.dateAchat = date;
    }

    public String getDescription(){
        return this.description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public LinkedHashMap<LocalDate, String> getEntretien(){
        return this.entretiens;
    }

    public void addEntretien(LocalDate dateEntretien, String description){
        this.entretiens.put(dateEntretien, description);
    }

    public void removeEntretien(LocalDate dateEntretien){
        this.entretiens.remove(dateEntretien);
    }
}
